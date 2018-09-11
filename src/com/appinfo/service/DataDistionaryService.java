package com.appinfo.service;

import com.appinfo.pojo.DataDictionary;

import java.util.List;

public interface DataDistionaryService {

    List<DataDictionary> findStatusByAll(String typeCode);
}
