package com.lee.eas.service.impl;

import com.lee.eas.domain.po.StudentPO;
import com.lee.eas.mapper.StudentMapper;
import com.lee.eas.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

}
