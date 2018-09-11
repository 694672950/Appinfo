package com.appinfo.service;

import com.appinfo.pojo.DevUser;

public interface DevUserService {

    DevUser findDevUserLogin(String devCode,String devPassword);
}
