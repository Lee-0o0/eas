package com.lee.eas.mapper;

import com.lee.eas.domain.po.StudentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface StudentMapper {
    StudentPO getStudentById(int id);

    List<StudentPO> queryAllStudents();
}
