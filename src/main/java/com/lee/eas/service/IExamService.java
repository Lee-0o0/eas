package com.lee.eas.service;

import com.lee.eas.domain.dto.ExamDTO;
import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.StudentDTO;
import com.lee.eas.domain.po.ExamPO;

import java.util.List;

public interface IExamService {
    Pagination<ExamPO> queryExam(int page, int pageSize);

    boolean addExam(ExamDTO examDTO);

    boolean updateExam(ExamDTO examDTO);

    boolean deleteExam(int id);
}
