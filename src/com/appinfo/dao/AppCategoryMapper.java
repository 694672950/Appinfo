package com.appinfo.dao;

import com.appinfo.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppCategoryMapper {

    List<AppCategory> getCategoryByParentId(@Param(value = "parentId") Integer parentId);
}
