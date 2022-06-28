package com.lee.eas.mapper;

import com.lee.eas.domain.po.ExamPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamMapper {

    ExamPO getExamById(int id);

    List<ExamPO> queryAllExam();

    int insertExam(ExamPO examPO);

    int updateExam(ExamPO examPO);

    int deleteExam(int id);

}
