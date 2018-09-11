package com.appinfo.service.impl;

import com.appinfo.dao.DevUserMapper;
import com.appinfo.pojo.DevUser;
import com.appinfo.service.DevUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 开发者页面控制器
 */
@Service(value = "devUserService")
public class DevUserServiceImpl implements DevUserService {

    @Resource(name = "devUserMapper")
    private DevUserMapper devUserMapper;


    /**
     * 开发者登录
     * @param devCode
     * @param devPassword
     * @return
     */
    @Override
    public DevUser findDevUserLogin(String devCode, String devPassword) {
        return devUserMapper.getUserLogin(devCode,devPassword);
    }
}
