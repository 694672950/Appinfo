package com.appinfo.service;

import com.appinfo.pojo.AppInfo;
import com.appinfo.util.PageBean;

public interface AppInfoService {

    PageBean<AppInfo> findAppByLikePage(AppInfo appInfo,Integer pageIndex,Integer pageSize);

    int addAppInfo(AppInfo appInfo);

    AppInfo findAppById(Integer id);

    int updateApp(AppInfo appInfo);
}
