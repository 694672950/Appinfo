package com.appinfo.service.impl;

import com.appinfo.dao.DataDictionaryMapper;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.service.DataDistionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dataDistionaryService")
public class DataDistionaryServiceImpl implements DataDistionaryService {

    @Resource(name="dataDictionaryMapper")
    private DataDictionaryMapper dataDictionaryMapper;


    @Override
    public List<DataDictionary> findStatusByAll(String typeCode) {
        return dataDictionaryMapper.getAppStatusAll(typeCode);
    }
}
