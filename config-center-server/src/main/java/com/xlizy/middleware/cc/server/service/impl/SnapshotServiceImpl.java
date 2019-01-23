package com.xlizy.middleware.cc.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.annotations.SendConfig;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.common.utils.StringUtils;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.entity.CcSnapshot;
import com.xlizy.middleware.cc.server.entity.CcSnapshotCriteria;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetails;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetailsCriteria;
import com.xlizy.middleware.cc.server.enums.CreateType;
import com.xlizy.middleware.cc.server.mapper.CcPropertiesMapper;
import com.xlizy.middleware.cc.server.mapper.CcSnapshotDetailsMapper;
import com.xlizy.middleware.cc.server.mapper.CcSnapshotMapper;
import com.xlizy.middleware.cc.server.service.AppService;
import com.xlizy.middleware.cc.server.service.EnvService;
import com.xlizy.middleware.cc.server.service.PropertiesService;
import com.xlizy.middleware.cc.server.service.SnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置快照服务实现
 * @author xlizy
 * @date 2018/6/4
 */
@Service
@Slf4j
public class SnapshotServiceImpl implements SnapshotService {

    @Autowired
    CcSnapshotMapper snapshotMapper;

    @Autowired
    CcSnapshotDetailsMapper snapshotDetailsMapper;

    @Autowired
    PropertiesService propertiesService;

    @Autowired
    CcPropertiesMapper propertiesMapper;

    @Autowired
    EnvService envService;

    @Autowired
    AppService appService;

    @CacheEvict(value = {"configCenterSnapshotTree","snapshot","snapshotDetails"}, allEntries = true)
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Results addSnapshot(Integer envId, String name, CreateType createType) {
        log.info("创建快照envId:{},name:{}",envId,name);

        //添加快照表数据
        CcEnv env = envService.getEnv(envId);
        List<CcProperties> propertiesList = propertiesService.getPropertiess(envId);
        CcSnapshot snapshot = new CcSnapshot();
        snapshot.setAppId(env.getAppId());
        snapshot.setEnvId(envId);
        snapshot.setName(name);
        snapshot.setCreateType(createType);
        snapshot.setCreateTime(new Date());
        snapshotMapper.insertSelective(snapshot);

        //添加快照明细表数据
        propertiesList.forEach(p -> {
            CcSnapshotDetails snapshotDetails = new CcSnapshotDetails();
            snapshotDetails.setSnapshotId(snapshot.getId());
            snapshotDetails.setPropertiesKey(p.getKey());
            snapshotDetails.setPropertiesValue(p.getValue());
            snapshotDetails.setPropertiesEnable(p.getEnable());
            snapshotDetails.setPropertiesName(p.getName());
            snapshotDetails.setCreateTime(new Date());
            snapshotDetailsMapper.insertSelective(snapshotDetails);
        });

        return Results.success().msg("创建成功");
    }

    @CacheEvict(value = {"snapshotDetails","snapshot","configCenterSnapshotTree"}, allEntries = true)
    @Override
    public Results deleteSnapshot(Integer id) {
        int count = snapshotMapper.deleteByPrimaryKey(id);
        if(count > 0){
            return Results.success().msg("删除成功");
        }else{
            return Results.failure().msg("删除失败");
        }
    }

    @Cacheable(value = "configCenterSnapshotTree")
    @Override
    public JSONObject getTree() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        JSONArray appArray = new JSONArray();
        List<CcApp> appList = appService.getApps();
        List<CcEnv> envList = envService.getEnvs();
        List<CcSnapshot> snapshotList = getSnapshots();
        appList.forEach(a ->{
            //获取当前应用下的环境列表
            List<CcEnv> envs = envList.stream().filter(e -> e.getAppId().compareTo(a.getId()) == 0).collect(Collectors.toList());
            //应用下面有环境信息
            if(!envs.isEmpty()){
                JSONObject app = new JSONObject();
                app.put("dataId", a.getId());
                app.put("type", "app");
                app.put("text", a.getName());
                app.put("qtip", "<strong>name</strong>:" + a.getName() + "<br/><strong>remark</strong>:" + a.getRemark());
                app.put("expanded",Boolean.FALSE);
                JSONArray envArray = new JSONArray();
                envs.forEach(e -> {
                    //获取当前应用下的环境列表
                    List<CcSnapshot> snapshots = snapshotList.stream().filter(s -> (s.getAppId().compareTo(a.getId()) == 0 && s.getEnvId().compareTo(e.getId()) == 0)).collect(Collectors.toList());
                    //环境下面有快照信息
                    if(!snapshots.isEmpty()){
                        JSONObject env = new JSONObject();
                        env.put("dataId", e.getId());
                        env.put("type", "env");
                        env.put("text", e.getName());
                        env.put("qtip", "<strong>name</strong>:" + e.getName() + "<br/><strong>env</strong>:" + e.getEnv() + "<br/><strong>version</strong>:" + e.getVersion() + "<br/><strong>cluster</strong>:" + e.getCluster() + "<br/><strong>enable</strong>:" + e.getEnable().getDes());
                        env.put("expanded",Boolean.FALSE);
                        JSONArray snapArray = new JSONArray();
                        snapshots.forEach(s -> {
                            JSONObject snapshot = new JSONObject();
                            snapshot.put("dataId", s.getId());
                            snapshot.put("type", "snap");
                            snapshot.put("text", s.getName());
                            snapshot.put("qtip", "<strong>name</strong>:" + s.getName() + "<br/><strong>createType</strong>:" + s.getCreateType().getDes()+"<br/><strong>createTime</strong>:"+sdf.format(s.getCreateTime()));
                            snapshot.put("leaf", Boolean.TRUE);
                            snapArray.add(snapshot);
                        });
                        env.put("children", snapArray);
                        envArray.add(env);
                    }else{//环境下面没有快照信息
                        JSONObject env = new JSONObject();
                        env.put("dataId", e.getId());
                        env.put("type", "env");
                        env.put("text", e.getName());
                        env.put("qtip", "<strong>name</strong>:" + e.getName() + "<br/><strong>env</strong>:" + e.getEnv() + "<br/><strong>version</strong>:" + e.getVersion() + "<br/><strong>cluster</strong>:" + e.getCluster() + "<br/><strong>enable</strong>:" + e.getEnable().getDes());
                        env.put("leaf", Boolean.TRUE);
                        envArray.add(env);
                    }
                });
                app.put("children", envArray);
                appArray.add(app);
            }else{//应用下面没有环境信息
                JSONObject app = new JSONObject();
                app.put("dataId", a.getId());
                app.put("type", "app");
                app.put("text", a.getName());
                app.put("qtip", "<strong>name</strong>:" + a.getName() + "<br/><strong>remark</strong>:" + a.getRemark());
                app.put("leaf", Boolean.TRUE);
                appArray.add(app);
            }
        });

        JSONObject object = new JSONObject();
        object.put("children",appArray);
        return object;
    }

    @SendConfig(envId = "#envId")
    @CacheEvict(value = {"properties"}, allEntries = true)
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Results restoreSnapshot(Integer id, Integer envId) {
        CcSnapshot snapshot = snapshotMapper.selectByPrimaryKey(id);

        CcSnapshotDetailsCriteria detailsCriteria = new CcSnapshotDetailsCriteria();
        detailsCriteria.createCriteria().andSnapshotIdEqualTo(snapshot.getId());
        List<CcSnapshotDetails> details = snapshotDetailsMapper.selectByExample(detailsCriteria);
        //先删除当前最新的配置
        propertiesService.deleteProperties(snapshot.getEnvId(),new ArrayList<>());

        //将快照信息添加到properties表
        details.forEach(d -> {
            CcProperties p = new CcProperties();
            p.setEnvId(snapshot.getEnvId());
            p.setKey(d.getPropertiesKey());
            p.setValue(d.getPropertiesValue());
            p.setName(d.getPropertiesName());
            p.setEnable(d.getPropertiesEnable());
            p.setCreateTime(new Date());
            p.setRemark("通过[" + snapshot.getName() +"]快照恢复");
            propertiesMapper.insertSelective(p);
        });

        return Results.success().msg("恢复成功");
    }

    @Cacheable(value = "snapshot")
    @Override
    public List<CcSnapshot> getSnapshots() {
        CcSnapshotCriteria snapshotCriteria = new CcSnapshotCriteria();
        snapshotCriteria.createCriteria();
        snapshotCriteria.setOrderByClause(" create_time ");
        return snapshotMapper.selectByExample(snapshotCriteria);
    }

    @Cacheable(value = "snapshotDetails", key = "T(com.xlizy.middleware.cc.server.common.utils.CoreUtil).cp(#details.toString(),#start,#limit)")
    @Override
    public String getSnapshotDetails(CcSnapshotDetails details, Integer start, Integer limit) {
        CcSnapshotDetailsCriteria detailsCriteria = new CcSnapshotDetailsCriteria();
        CcSnapshotDetailsCriteria.Criteria criteria = detailsCriteria.createCriteria();

        if(details != null){
            if(StringUtils.isNotEmpty(details.getPropertiesKey())){
                criteria.andPropertiesKeyLike("%" + details.getPropertiesKey() + "%");
            }
            if(StringUtils.isNotEmpty(details.getPropertiesName())){
                criteria.andPropertiesNameLike("%" + details.getPropertiesName() + "%");
            }
            if(details.getPropertiesEnable() != null) {
                criteria.andPropertiesEnableEqualTo(details.getPropertiesEnable());
            }
            if(details.getSnapshotId() != null){
                criteria.andSnapshotIdEqualTo(details.getSnapshotId());
            }
        }
        detailsCriteria.setOrderByClause(" properties_key ");
        List<CcSnapshotDetails> list = snapshotDetailsMapper.selectByExampleWithRowbounds(detailsCriteria,new RowBounds(start,limit));
        long count = snapshotDetailsMapper.countByExample(detailsCriteria);
        return Results.gridResult(count, list);
    }
}
