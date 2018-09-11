package com.appinfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.appinfo.pojo.BackendUser;
import com.appinfo.service.BackendUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 后台管理员页面控制器
 */
@Controller
@RequestMapping(value = "/backenuser")
public class BackenUserController {

    @Resource(name="backendUserService")
    private BackendUserService backendUserService;

    //跳到后台管理员登录页面
    @RequestMapping(value = "/backenlogin.html")
    public String login(){
        return "backend/backenlogin";
    }


    //用例1.登录处理
    @RequestMapping(value ="/backenlogin.do",method = RequestMethod.POST)
    @ResponseBody
    public String loginDo(@RequestParam(value = "userCode") String userCode, @RequestParam(value = "userPassword")String userPassword, HttpSession session, Model model){
        BackendUser userLogin = backendUserService.findUserLogin(userCode, userPassword);
        if(userLogin!=null){
            session.setAttribute("backenUserSession",userLogin);
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


    //用例1.注销
    @RequestMapping(value = "/logout.html")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.jsp";

    }


}
