package com.appinfo.service;

import com.appinfo.pojo.AppCategory;

import java.util.List;

public interface AppCategoryService {

    List<AppCategory> findCategoryByParentId(Integer parentId);
}
