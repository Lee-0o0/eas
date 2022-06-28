package com.lee.eas.service.impl;

import com.lee.eas.domain.po.AdminPO;
import com.lee.eas.mapper.AdminMapper;
import com.lee.eas.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean login(String username, String password) {

        AdminPO adminByUsername = adminMapper.getAdminByUsername(username);
        if(adminByUsername.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
