package com.lee.eas.controller;

import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import com.lee.eas.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GradeController {

    @Autowired
    private IGradeService gradeService;

    @ResponseBody
    @GetMapping("/grade")
    public Response<List<StudentWithGradeDTO>> queryLatestExamGrades(){
        return gradeService.queryLatestExamGrade();
    }

}
