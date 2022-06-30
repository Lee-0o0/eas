package com.lee.eas.mapper;

import com.lee.eas.domain.po.GradePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GradeMapper {

    GradePO getGrade(int examId,int studentId);

}
