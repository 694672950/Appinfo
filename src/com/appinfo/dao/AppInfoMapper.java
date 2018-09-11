package com.appinfo.dao;

import com.appinfo.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppInfoMapper {
    //查询app记录
    List<AppInfo> getAppByLikeName(@Param("appInfo") AppInfo appInfo, @Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    //查询app记录数
    int getAppLikeNameCount(AppInfo appInfo);

    //增加app基础信息
    int addApp(AppInfo appInfo);

    //按ID查找APP
    AppInfo getAppById(@Param("id") Integer id);

    int updateApp(AppInfo appInfo);
}
