package com.lee.eas.mapper;

import com.lee.eas.domain.po.GradePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GradeMapper {

    List<GradePO> queryGradeByExamId(int examId);

    GradePO getGrade(int examId,int studentId);

    int insertGrade(GradePO gradePO);

    int insertBatchGrade(List<GradePO> gradePOList);
}
