package com.appinfo.service;

import com.appinfo.pojo.BackendUser;

public interface BackendUserService {

    BackendUser findUserLogin(String userCode,String userPassword);
}
