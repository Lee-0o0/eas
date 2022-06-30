package com.lee.eas.service;

import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import org.springframework.stereotype.Service;


public interface IStudentService {
    StudentDTO getStudentById(int id);

    Pagination<StudentDTO> queryStudent(int page, int pageSize);

    boolean insertStudent(StudentDTO studentDTO);

    boolean updateStudent(StudentDTO studentDTO);

    boolean deleteStudent(int id);

    boolean updatePassword(int id, String oldPsw, String newPsw);

    Response<StudentWithGradeDTO> queryGrade(String studentNumber, String password);
}
