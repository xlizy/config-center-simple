package com.xlizy.middleware.cc.server.service.impl;

import com.xlizy.middleware.cc.server.annotations.SendConfig;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.common.utils.StringUtils;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcAppCriteria;
import com.xlizy.middleware.cc.server.mapper.CcAppMapper;
import com.xlizy.middleware.cc.server.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 应用服务接口实现
 * @author xlizy
 * @date 2018/5/19
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    CcAppMapper appMapper;

    @CacheEvict(value = {"app","configCenterTree","configCenterSnapshotTree"}, allEntries = true)
    @Override
    public Results addApp(CcApp app) {
        app.setCreateTime(new Date());
        appMapper.insertSelective(app);
        return Results.success().msg("添加成功");
    }

    @SendConfig(appId = "#app.id")
    @CacheEvict(value = {"app","configCenterTree","configCenterSnapshotTree"}, allEntries = true)
    @Override
    public Results editApp(CcApp app) {
        app.setLastModifyTime(new Date());
        appMapper.updateByPrimaryKeySelective(app);
        return Results.success().msg("修改成功");
    }

    @CacheEvict(value = {"app","env","properties","configCenterTree","configCenterSnapshotTree"}, allEntries = true)
    @Override
    public Results deleteApp(List<Integer> ids) {
        CcAppCriteria appCriteria = new CcAppCriteria();
        if(!ids.isEmpty()) {
            appCriteria.createCriteria().andIdIn(ids);
        }
        appMapper.deleteByExample(appCriteria);
        return Results.success().msg("删除成功");
    }

    @Cacheable(value = "app", key = "T(com.xlizy.middleware.cc.server.common.utils.CoreUtil).cp('all')")
    @Override
    public List<CcApp> getApps() {
        CcAppCriteria appCriteria = new CcAppCriteria();
        appCriteria.createCriteria();
        appCriteria.setOrderByClause(" name ");
        return appMapper.selectByExample(appCriteria);
    }

    @Cacheable(value = "app", key = "#id")
    @Override
    public CcApp getApp(Integer id) {
        return appMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "app", key = "'name'+#name")
    @Override
    public CcApp getApp(String name) {
        if(StringUtils.isBlank(name)){
            return null;
        }
        CcAppCriteria appCriteria = new CcAppCriteria();
        appCriteria.createCriteria().andNameEqualTo(name);
        List<CcApp> list = appMapper.selectByExample(appCriteria);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
