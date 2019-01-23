package com.xlizy.middleware.cc.server.service.impl;

import com.xlizy.middleware.cc.server.annotations.SendConfig;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcEnvCriteria;
import com.xlizy.middleware.cc.server.enums.Enable;
import com.xlizy.middleware.cc.server.mapper.CcEnvMapper;
import com.xlizy.middleware.cc.server.service.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 环境服务接口实现
 * @author xlizy
 * @date 2018/5/19
 */
@Service
public class EnvServiceImpl implements EnvService {

    @Autowired
    CcEnvMapper envMapper;

    @CacheEvict(value = {"env","configCenterTree","configCenterSnapshotTree"}, allEntries = true)
    @Override
    public Results addEnv(CcEnv env) {
        env.setEnable(Enable.YES);
        env.setCreateTime(new Date());
        envMapper.insertSelective(env);
        return Results.success().msg("添加成功");
    }

    @SendConfig(envId = "#env.id")
    @CacheEvict(value = {"env","configCenterTree","configCenterSnapshotTree"}, allEntries = true)
    @Override
    public Results editEnv(CcEnv env) {
        env.setLastModifyTime(new Date());
        envMapper.updateByPrimaryKeySelective(env);
        return Results.success().msg("修改成功");
    }

    @CacheEvict(value = {"env","properties","configCenterTree","configCenterSnapshotTree"}, allEntries = true)
    @Override
    public Results deleteEnv(List<Integer> ids) {
        CcEnvCriteria envCriteria = new CcEnvCriteria();
        if(!ids.isEmpty()) {
            envCriteria.createCriteria().andIdIn(ids);
        }
        envMapper.deleteByExample(envCriteria);
        return Results.success().msg("删除成功");
    }

    @Cacheable(value = "env", key = "T(com.xlizy.middleware.cc.server.common.utils.CoreUtil).cp('all')")
    @Override
    public List<CcEnv> getEnvs() {
        CcEnvCriteria envCriteria = new CcEnvCriteria();
        envCriteria.createCriteria();
        envCriteria.setOrderByClause(" env,version,cluster ");
        return envMapper.selectByExample(envCriteria);
    }

    @Cacheable(value = "env", key = "'appId'+#id")
    @Override
    public List<CcEnv> getEnvsByAppId(Integer id) {
        CcEnvCriteria envCriteria = new CcEnvCriteria();
        envCriteria.createCriteria().andAppIdEqualTo(id);
        return envMapper.selectByExample(envCriteria);
    }

    @Cacheable(value = "env", key = "#id")
    @Override
    public CcEnv getEnv(Integer id) {
        return envMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "env", key = "T(com.xlizy.middleware.cc.server.common.utils.CoreUtil).cp(#appId,#env,#version,#cluster)")
    @Override
    public CcEnv getEnv(Integer appId, String env,String version,String cluster) {
        CcEnvCriteria envCriteria = new CcEnvCriteria();
        envCriteria.createCriteria()
                .andAppIdEqualTo(appId)
                .andEnvEqualTo(env)
                .andVersionEqualTo(version)
                .andClusterEqualTo(cluster);
        List<CcEnv> list = envMapper.selectByExample(envCriteria);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
