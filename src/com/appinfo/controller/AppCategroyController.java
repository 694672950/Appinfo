package com.appinfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.appinfo.pojo.AppCategory;
import com.appinfo.service.AppCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/category")
public class AppCategroyController {


    @Resource(name = "appCategoryService")
    private AppCategoryService appCategoryService;


    //查询条件分类异步请求
    @RequestMapping("/categorylist/{parentId}")
    @ResponseBody
    public String category(@PathVariable Integer parentId){

        List<AppCategory> categoryByParentId = appCategoryService.findCategoryByParentId(parentId);
        String json = JSON.toJSONStringWithDateFormat(categoryByParentId, "yyyy-MM-dd HH:mm:ss",
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty
        );
        return json;
    }
}
