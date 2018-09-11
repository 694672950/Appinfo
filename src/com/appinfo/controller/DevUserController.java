package com.appinfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.appinfo.pojo.DevUser;
import com.appinfo.service.DevUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 开发者登录
 */
@Controller
@RequestMapping(value = "/devuser")
public class DevUserController {

    @Resource(name = "devUserService")
    private DevUserService devUserService;

    //跳到开发者登录页面
    @RequestMapping(value = "/devlogin.html")
    public String login(){
        return "devlogin";
    }


    //用例1.处理开发者登录
    @RequestMapping(value ="/devlogin.do",method = RequestMethod.POST)
    @ResponseBody
    public String loginDo(@RequestParam(value = "devCode") String devCode, @RequestParam(value = "devPassword")String devPassword, HttpSession session, Model model){
        DevUser userLogin = devUserService.findDevUserLogin(devCode, devPassword);
        if(userLogin!=null){
            session.setAttribute("devUserSession",userLogin);
            model.addAttribute("result","true" );
        }else {
            model.addAttribute("result","false" );
        }

        String json = JSON.toJSONStringWithDateFormat(model, "yyyy-MM-dd HH:mm:ss",
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty
        );
        return json;
    }

        @RequestMapping(value = "/devindex.html")
        public String devIndex(HttpSession session){
        if(session.getAttribute("devUserSession")!=null){
            return "developer/main";
        }else {
            return "403";
        }

    }


    //用例1.注销
    @RequestMapping(value = "/logout.html")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.jsp";

    }





}
