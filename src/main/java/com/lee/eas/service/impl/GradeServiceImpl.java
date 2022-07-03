package com.lee.eas.service.impl;

import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import com.lee.eas.domain.po.ExamPO;
import com.lee.eas.domain.po.GradePO;
import com.lee.eas.domain.po.StudentPO;
import com.lee.eas.mapper.ExamMapper;
import com.lee.eas.mapper.GradeMapper;
import com.lee.eas.mapper.StudentMapper;
import com.lee.eas.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Response<List<StudentWithGradeDTO>> queryLatestExamGrade() {
        Response<List<StudentWithGradeDTO>> response = new Response<>();
        response.setCode(-1);
        try {
            int examMaxId = examMapper.getExamMaxId();
            if (examMaxId < 0) {
                throw  new Exception("不存在考试");
            }

            return queryGrade(examMaxId);
        }catch (Exception e){
            response.setMsg(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<List<StudentWithGradeDTO>> queryGrade(int examId) {
        Response<List<StudentWithGradeDTO>> response = new Response<>();
        response.setCode(-1);
        try {
            if (examId < 0) {
                throw  new Exception("不存在考试");
            }
            ExamPO examById = examMapper.getExamById(examId);
            List<GradePO> gradePOS = gradeMapper.queryGradeByExamId(examId);
            List<StudentPO> studentPOS = studentMapper.queryAll();

            List<StudentWithGradeDTO> studentWithGradeDTOList = new ArrayList<>();
            for (StudentPO student:studentPOS) {
                StudentWithGradeDTO studentWithGradeDTO = new StudentWithGradeDTO();
                studentWithGradeDTO.setExamName(examById.getName());
                studentWithGradeDTO.setName(student.getName());
                studentWithGradeDTO.setStudentNumber(student.getStudentNumber());

                GradePO gradePO = gradePOS.stream().filter(x -> x.getStudentId() == student.getId()).findFirst().orElse(null);
                if(gradePO != null){
                    studentWithGradeDTO.setSubjectOne(gradePO.getSubjectOne());
                    studentWithGradeDTO.setSubjectTwo(gradePO.getSubjectTwo());
                    studentWithGradeDTO.setTotal(gradePO.getTotal());
                    studentWithGradeDTO.setRank(gradePO.getRank());
                }

                studentWithGradeDTOList.add(studentWithGradeDTO);
            }

            response.setCode(0);
            response.setMsg("成功");
            response.setData(studentWithGradeDTOList);
        }catch (Exception e){
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
