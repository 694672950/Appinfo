package com.appinfo.service.impl;

import com.appinfo.dao.BackendUserMapper;
import com.appinfo.pojo.BackendUser;
import com.appinfo.service.BackendUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value ="backendUserService" )
public class BackendUserServiceImpl implements BackendUserService {

    @Resource(name = "backendUserMapper")
    private BackendUserMapper backendUserMapper;
    @Override
    public BackendUser findUserLogin(String userCode, String userPassword) {
        return backendUserMapper.getUserLogin(userCode, userPassword);
    }
}
