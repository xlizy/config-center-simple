package com.xlizy.middleware.cc.server.service.impl;

import com.xlizy.middleware.cc.server.annotations.SendConfig;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import com.xlizy.middleware.cc.server.common.utils.StringUtils;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.entity.CcPropertiesCriteria;
import com.xlizy.middleware.cc.server.enums.Enable;
import com.xlizy.middleware.cc.server.mapper.CcPropertiesMapper;
import com.xlizy.middleware.cc.server.service.PropertiesService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 配置服务接口实现
 * @author xlizy
 * @date 2018/5/19
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    CcPropertiesMapper propertiesMapper;

    @CacheEvict(value = {"properties"}, allEntries = true)
    @Override
    public Results addProperties(CcProperties properties) {
        properties.setCreateTime(new Date());

        CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
        CcPropertiesCriteria.Criteria criteria = propertiesCriteria.createCriteria();
        criteria.andEnvIdEqualTo(properties.getEnvId()).andKeyEqualTo(properties.getKey());
        if(propertiesMapper.selectByExample(propertiesCriteria).size() > 0){
            return Results.failure().msg("已存在该配置");
        }

        propertiesMapper.insertSelective(properties);
        return Results.success().msg("添加成功");
    }

    @SendConfig(envId = "#properties.envId")
    @CacheEvict(value = {"properties"}, allEntries = true)
    @Override
    public Results editProperties(CcProperties properties) {
        properties.setLastModifyTime(new Date());

        CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
        CcPropertiesCriteria.Criteria criteria = propertiesCriteria.createCriteria();
        criteria.andEnvIdEqualTo(properties.getEnvId()).andKeyEqualTo(properties.getKey()).andIdNotEqualTo(properties.getId());
        if(propertiesMapper.selectByExample(propertiesCriteria).size() > 0){
            return Results.failure().msg("已存在该配置");
        }

        propertiesMapper.updateByPrimaryKeySelective(properties);
        return Results.success().msg("修改成功");
    }

    @SendConfig(envId = "#envId")
    @CacheEvict(value = {"properties"}, allEntries = true)
    @Override
    public Results deleteProperties(Integer envId, List<Integer> ids) {
        CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
        if(!ids.isEmpty()) {
            propertiesCriteria.createCriteria().andIdIn(ids);
        }
        propertiesMapper.deleteByExample(propertiesCriteria);
        return Results.success().msg("删除成功");
    }

    @SendConfig(envId = "#envId")
    @CacheEvict(value = {"properties"}, allEntries = true)
    @Override
    public Results setEnable(Integer envId, List<Integer> ids, Enable enable) {
        CcProperties properties = new CcProperties();
        CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
        if(!ids.isEmpty()) {
            propertiesCriteria.createCriteria().andIdIn(ids);
        }
        properties.setEnable(enable);
        propertiesMapper.updateByExampleSelective(properties,propertiesCriteria);
        return Results.success().msg("设置成功");
    }

    @Cacheable(value = "properties", key = "T(com.xlizy.middleware.cc.server.common.utils.CoreUtil).cp(#properties.toString(),#start,#limit)")
    @Override
    public String getPropertiess(CcProperties properties, Integer start, Integer limit) {
        CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
        CcPropertiesCriteria.Criteria criteria = propertiesCriteria.createCriteria();
        if(properties != null) {
            if(StringUtils.isNotEmpty(properties.getName())) {
                criteria.andNameLike("%" + properties.getName() + "%");
            }
            if(properties.getKey() != null) {
                criteria.andKeyLike("%" + properties.getKey() + "%");
            }
            if(properties.getEnable() != null) {
                criteria.andEnableEqualTo(properties.getEnable());
            }
            if(properties.getEnvId() != null) {
                criteria.andEnvIdEqualTo(properties.getEnvId());
            }
        }
        propertiesCriteria.setOrderByClause(" `key` ");
        List<CcProperties> list = propertiesMapper.selectByExampleWithRowbounds(propertiesCriteria, new RowBounds(start, limit));
        long count = propertiesMapper.countByExample(propertiesCriteria);
        return Results.gridResult(count, list);
    }

    @Cacheable(value = "properties", key = "#id")
    @Override
    public CcProperties getProperties(Integer id) {
        return propertiesMapper.selectByPrimaryKey(id);
    }

    @Override
    public String downloadProperties(String idsStr, Integer envId) {
        List<Integer> ids = CoreUtil.strArray2List(idsStr);
        CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
        CcPropertiesCriteria.Criteria criteria = propertiesCriteria.createCriteria();
        if(!ids.isEmpty()){
            criteria.andIdIn(ids);
        }
        if(envId != null){
            criteria.andEnvIdEqualTo(envId);
        }
        propertiesCriteria.setOrderByClause(" `key` ");
        StringBuilder sb = new StringBuilder();
        List<CcProperties> list = propertiesMapper.selectByExample(propertiesCriteria);
        list.forEach(p -> {
            sb.append("#").append(p.getName()).append("\n");
            if(Enable.NO.equals(p.getEnable())){
                sb.append("#");
            }
            sb.append(p.getKey()).append("=").append(p.getValue()).append("\n");
        });
        return sb.toString();
    }

    /**
     * 上传配置
     * 因为配置的key肯定是不能重复的，所以现在是只要有重复的key，全部回滚，以后会考虑跳过错误并给以提示
     * */
    //@SendConfig(envId = "#envId")
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = {"properties"}, allEntries = true)
    @Override
    public Results uploadProperties(Integer envId,InputStream is) {
        StringBuilder sb = new StringBuilder();

        BufferedReader reader=null;
        try {
            reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            return Results.failure().msg("读取文件失败");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                return Results.failure().msg("关闭流失败");
            }
        }

        String[] row = sb.toString().split("\n");

        //这里有些特殊的处理,主要是要获取配置的注释,目前的策略是key=value往上找,最近的一行以'#'开头的内容为本条配置的注释
        boolean init = true;
        List<CcProperties> list = new ArrayList<>();
        CcProperties properties = new CcProperties();
        for (int i = 0; i < row.length; i++) {
            if(init){
                properties = new CcProperties();
                properties.setEnvId(envId);
                properties.setEnable(Enable.NO);
                init = false;
            }
            try {
                if(row[i].startsWith("#") && (!row[i + 1].startsWith("#")) && row[i + 1].indexOf("=") > -1){
                    properties.setName(row[i].substring(1));
                }
            } catch (Exception e) {

            }
            if((!row[i].startsWith("#")) && row[i].indexOf("=") > -1){
                properties.setKey(row[i].substring(0,row[i].indexOf("=")));
                properties.setValue(row[i].substring(row[i].indexOf("=")+1));
                list.add(properties);
                init = true;
            }
        }
        list.forEach(p -> {
            p.setCreateTime(new Date());
            p.setRemark("通过上传文件添加");

            CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
            CcPropertiesCriteria.Criteria criteria = propertiesCriteria.createCriteria();
            criteria.andEnvIdEqualTo(p.getEnvId()).andKeyEqualTo(p.getKey());
            List<CcProperties> ps = propertiesMapper.selectByExample(propertiesCriteria);
            if(ps.size() < 1){
                propertiesMapper.insertSelective(p);
            }else {
                p.setId(ps.get(0).getId());
                propertiesMapper.updateByPrimaryKeySelective(p);
            }
        });
        return Results.success();
    }

    @Cacheable(value = "properties", key = "'env'+#envId")
    @Override
    public List<CcProperties> getPropertiess(Integer envId) {
        CcPropertiesCriteria propertiesCriteria = new CcPropertiesCriteria();
        propertiesCriteria.createCriteria().andEnvIdEqualTo(envId);
        return propertiesMapper.selectByExample(propertiesCriteria);
    }

}
