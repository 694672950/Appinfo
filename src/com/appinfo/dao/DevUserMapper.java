package com.appinfo.dao;

import com.appinfo.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

public interface DevUserMapper {
    DevUser getUserLogin(@Param("devCode") String dvCode, @Param("devPassword")String dvPassword);
}
