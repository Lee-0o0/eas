package com.lee.eas.service;

import com.lee.eas.domain.dto.ExamDTO;
import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import com.lee.eas.domain.po.ExamPO;

import java.util.List;

public interface IExamService {
    Pagination<ExamDTO> queryExam(int page, int pageSize);

    Response addExam(ExamDTO examDTO);

    Response updateExam(ExamDTO examDTO);

    Response deleteExam(int id);
}
