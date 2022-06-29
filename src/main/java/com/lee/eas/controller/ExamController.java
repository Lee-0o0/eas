package com.lee.eas.controller;

import com.lee.eas.domain.dto.ExamDTO;
import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.po.ExamPO;
import com.lee.eas.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExamController {

    @Autowired
    private IExamService examService;

    @GetMapping("/exam")
    public Response<Pagination<ExamPO>> queryExam(int page,int pageSize){
        if(page <= 0){
            page = 1;
        }
        if(pageSize <= 0){
            pageSize = 10;
        }

        Pagination<ExamPO> pagination = examService.queryExam(page, pageSize);
        Response<Pagination<ExamPO>> response = new Response<>();
        response.setCode(0);
        response.setMsg("成功");
        response.setData(pagination);

        return response;
    }

    @ResponseBody
    @PostMapping("/exam")
    public Response addExam(ExamDTO examDTO){
        Response response = new Response();

        boolean addExam = examService.addExam(examDTO);
        if(addExam){
            response.setCode(-1);
            response.setMsg("新增考试失败");
        }else {
            response.setCode(0);
            response.setMsg("成功");
        }

        return response;
    }

    @GetMapping("/admin/exam")
    public String toExamPage(){
        return "/admin/exams";
    }
}
