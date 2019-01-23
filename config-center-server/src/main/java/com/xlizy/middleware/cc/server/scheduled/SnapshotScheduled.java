package com.xlizy.middleware.cc.server.scheduled;

import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.entity.CcSnapshot;
import com.xlizy.middleware.cc.server.entity.CcSnapshotCriteria;
import com.xlizy.middleware.cc.server.entity.CcSnapshotDetails;
import com.xlizy.middleware.cc.server.enums.CreateType;
import com.xlizy.middleware.cc.server.mapper.CcSnapshotDetailsMapper;
import com.xlizy.middleware.cc.server.mapper.CcSnapshotMapper;
import com.xlizy.middleware.cc.server.service.AppService;
import com.xlizy.middleware.cc.server.service.EnvService;
import com.xlizy.middleware.cc.server.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xlizy
 * @date 2018/6/6
 */
@Component
@Slf4j
public class SnapshotScheduled {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CcSnapshotMapper snapshotMapper;

    @Autowired
    CcSnapshotDetailsMapper snapshotDetailsMapper;

    @Autowired
    PropertiesService propertiesService;

    @Autowired
    EnvService envService;

    @Autowired
    AppService appService;

    /**
     * 每天0点执行:
     * 为每个环境创建快照并删除30天之前自动创建的快照
     * 注意:只删除自动创建的快照
     * */
    @CacheEvict(value = {"snapshotDetails","snapshot","configCenterSnapshotTree"}, allEntries = true)
    @Transactional(rollbackFor = {Exception.class})
    @Scheduled(cron = "0 0 0 * * ?")
    public void snapshotManage() {
        String redisKey = "snapshotManageScheduled",redisValue = "1";
        //集群模式下只有一个节点能执行此业务方法
        if(!redisTemplate.opsForValue().setIfAbsent(redisKey,redisValue)){
            log.info("已经有其他节点在自动创建快照并删除30天之前自动创建的快照了");
            return;
        }
        redisTemplate.expire("snapshotManageScheduled",1,TimeUnit.HOURS);

        log.info("定时创建快照并删除30天之前自动创建的快照");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:MM:ss");
        Date now = new Date();
        String nowFormat = sdf.format(now);

        //创建快照
        //获取所有环境集合
        List<CcEnv> envList = envService.getEnvs();
        envList.forEach(e -> {
            //获取环境所属应用
            CcApp app = appService.getApp(e.getAppId());
            //生成快照名称
            StringBuilder snapshotName = new StringBuilder();
            snapshotName.append(app.getName())
                    .append("_")
                    .append(e.getName())
                    .append("_")
                    .append(nowFormat);

            //添加快照表数据
            CcSnapshot snapshot = new CcSnapshot();
            snapshot.setAppId(e.getAppId());
            snapshot.setEnvId(e.getId());
            snapshot.setName(snapshotName.toString());
            snapshot.setCreateType(CreateType.AUTOMATIC);
            snapshot.setCreateTime(new Date());
            snapshot.setCreateUser("定时任务");
            snapshotMapper.insertSelective(snapshot);

            //获取环境的最新配置集合
            List<CcProperties> propertiesList = propertiesService.getPropertiess(e.getId());
            //添加快照明细表数据
            propertiesList.forEach(p -> {
                CcSnapshotDetails snapshotDetails = new CcSnapshotDetails();
                snapshotDetails.setSnapshotId(snapshot.getId());
                snapshotDetails.setPropertiesKey(p.getKey());
                snapshotDetails.setPropertiesValue(p.getValue());
                snapshotDetails.setPropertiesEnable(p.getEnable());
                snapshotDetails.setPropertiesName(p.getName());
                snapshotDetails.setCreateTime(now);
                snapshotDetails.setCreateUser("定时任务");
                snapshotDetailsMapper.insertSelective(snapshotDetails);
            });
        });

        //获得30天之前的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE,-30);

        //删除30天之前自动创建的快照
        CcSnapshotCriteria snapshotCriteria = new CcSnapshotCriteria();
        snapshotCriteria.createCriteria()
                .andCreateTypeEqualTo(CreateType.AUTOMATIC)
                .andCreateTimeLessThan(calendar.getTime());
        snapshotMapper.deleteByExample(snapshotCriteria);

    }

}
