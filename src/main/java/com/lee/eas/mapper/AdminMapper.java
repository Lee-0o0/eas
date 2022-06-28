package com.lee.eas.mapper;

import com.lee.eas.domain.po.AdminPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    AdminPO getAdminByUsername(String username);
}
