package com.lee.eas.service;

import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentWithGradeDTO;

import java.util.List;

public interface IGradeService {

    Response<List<StudentWithGradeDTO>> queryLatestExamGrade();

    Response<List<StudentWithGradeDTO>> queryGrade(int examId);
}
