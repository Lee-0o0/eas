package com.lee.eas.service;

import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import org.springframework.stereotype.Service;


public interface IStudentService {
    StudentDTO getStudentById(int id);

    Pagination<StudentDTO> queryStudent(int page, int pageSize);

    Response insertStudent(StudentDTO studentDTO);

    Response updateStudent(StudentDTO studentDTO);

    Response deleteStudent(int id);

    Response updatePassword(String studentNumber, String oldPsw, String newPsw);

    Response<StudentWithGradeDTO> queryGrade(String studentNumber, String password);
}
