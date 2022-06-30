package com.lee.eas.service.impl;

import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import com.lee.eas.domain.po.ExamPO;
import com.lee.eas.domain.po.GradePO;
import com.lee.eas.domain.po.StudentPO;
import com.lee.eas.mapper.ExamMapper;
import com.lee.eas.mapper.GradeMapper;
import com.lee.eas.mapper.StudentMapper;
import com.lee.eas.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public StudentDTO getStudentById(int id) {
        StudentPO studentById = studentMapper.getStudentById(id);
        return new StudentDTO(studentById.getId(),studentById.getName(),studentById.getStudentNumber());
    }

    @Override
    public Pagination<StudentDTO> queryStudent(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<StudentPO> studentPOList = studentMapper.queryAllStudents(offset, pageSize);
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (StudentPO s : studentPOList) {
            studentDTOList.add(new StudentDTO(s.getId(),s.getName(),s.getStudentNumber()));
        }

        int count = studentMapper.countStudent();

        Pagination<StudentDTO> pagination = new Pagination<>();
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        pagination.setTotal(count);
        pagination.setData(studentDTOList);

        return pagination;
    }

    @Override
    public boolean insertStudent(StudentDTO studentDTO) {
        StudentPO studentPO = new StudentPO(studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getStudentNumber(),
                studentDTO.getStudentNumber());
        return studentMapper.insertStudent(studentPO) == 1;
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) {

        StudentPO studentPO = studentMapper.getStudentById(studentDTO.getId());

        studentPO.setName(studentDTO.getName());
        studentPO.setStudentNumber(studentDTO.getStudentNumber());

        return studentMapper.updateStudent(studentPO) == 1;
    }

    @Override
    public boolean deleteStudent(int id) {
        return studentMapper.deleteStudent(id) == 1;
    }

    @Override
    public boolean updatePassword(int id, String oldPsw, String newPsw) {
        StudentPO studentPO = studentMapper.getStudentById(id);

        if(!studentPO.getPassword().equals(oldPsw)){
            return false;
        }

        studentPO.setPassword(newPsw);
        return studentMapper.updateStudent(studentPO) == 1;
    }

    @Override
    public Response<StudentWithGradeDTO> queryGrade(String studentNumber, String password) {

        Response<StudentWithGradeDTO> response = new Response<>();
        // 查询学生信息
        StudentPO studentByStudentNumber = studentMapper.getStudentByStudentNumber(studentNumber);
        if(studentByStudentNumber == null){
            response.setCode(-1);
            response.setMsg("学号有误，未查询到对应的学生");
            return response;
        }
        if(!password.equals(studentByStudentNumber.getPassword())){
            response.setCode(-1);
            response.setMsg("学号或密码有误");
            return response;
        }

        // 查询最近一次考试信息
        int examMaxId = examMapper.getExamMaxId();
        ExamPO examById = examMapper.getExamById(examMaxId);
        // 查询该学生的成绩
        GradePO grade = gradeMapper.getGrade(examMaxId, studentByStudentNumber.getId());
        if(grade == null){
            response.setCode(-1);
            response.setMsg("未查询到该学生最近考试结果");
            return response;
        }

        StudentWithGradeDTO studentWithGradeDTO = new StudentWithGradeDTO();
        studentWithGradeDTO.set(studentByStudentNumber,examById,grade);
        response.setCode(0);
        response.setMsg("成功");
        response.setData(studentWithGradeDTO);

        return response;
    }
}
