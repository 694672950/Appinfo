package com.appinfo.dao;
import com.appinfo.pojo.BackendUser;
import org.apache.ibatis.annotations.Param;
public interface BackendUserMapper {
    BackendUser getUserLogin(@Param("userCode") String userCode, @Param("userCode") String userPassword);
}
