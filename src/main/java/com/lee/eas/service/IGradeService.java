package com.lee.eas.service;

import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IGradeService {

    Response<List<StudentWithGradeDTO>> queryLatestExamGrade();

    Response<List<StudentWithGradeDTO>> queryGrade(int examId);

    Response importGrades(int examId, String filename, MultipartFile file);
}
