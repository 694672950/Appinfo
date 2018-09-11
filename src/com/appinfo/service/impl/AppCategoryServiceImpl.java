package com.appinfo.service.impl;

import com.appinfo.dao.AppCategoryMapper;
import com.appinfo.pojo.AppCategory;
import com.appinfo.service.AppCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService {

    @Resource(name="appCategoryMapper")
    private AppCategoryMapper appCategoryMapper;

    @Override
    public List<AppCategory> findCategoryByParentId(Integer parentId) {
        return appCategoryMapper.getCategoryByParentId(parentId);
    }
}
