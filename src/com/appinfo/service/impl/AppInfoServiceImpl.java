package com.appinfo.service.impl;

import com.appinfo.dao.AppInfoMapper;
import com.appinfo.pojo.AppInfo;
import com.appinfo.service.AppInfoService;
import com.appinfo.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

    @Resource(name = "appInfoMapper")
    private AppInfoMapper appInfoMapper;

    @Override
    public PageBean<AppInfo> findAppByLikePage(AppInfo appInfo, Integer pageIndex, Integer pageSize) {
        PageBean<AppInfo> pageBean=new PageBean<>();
        pageBean.setPageSize(pageSize);
        int appLikeNameCount = appInfoMapper.getAppLikeNameCount(appInfo);
        pageBean.setTotalCount(appLikeNameCount);
        pageBean.setPageIndex(pageIndex);
        List<AppInfo> appByLikeName = appInfoMapper.getAppByLikeName(appInfo, (pageIndex - 1) * pageSize, pageSize);
        pageBean.setPageIndexList(appByLikeName);
        return pageBean;
    }

    @Override
    public int addAppInfo(AppInfo appInfo) {
        return appInfoMapper.addApp(appInfo);
    }

    @Override
    public AppInfo findAppById(Integer id) {
        return appInfoMapper.getAppById(id);
    }

    @Override
    public int updateApp(AppInfo appInfo) {
        return appInfoMapper.updateApp(appInfo);
    }
}
