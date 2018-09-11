package com.appinfo.dao;

import com.appinfo.pojo.DataDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataDictionaryMapper {

    //查询所有APP状态
    List<DataDictionary> getAppStatusAll(@Param("typeCode") String typeCode);

}
