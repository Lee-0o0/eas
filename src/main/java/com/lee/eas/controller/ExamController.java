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

import java.util.Random;

@Controller
public class ExamController {

    @Autowired
    private IExamService examService;

    @ResponseBody
    @GetMapping("/exam")
    public Response<Pagination<ExamDTO>> queryExam(int page,int pageSize){
        if(page <= 0){
            page = 1;
        }
        if(pageSize <= 0){
            pageSize = 10;
        }

        Pagination<ExamDTO> pagination = examService.queryExam(page, pageSize);
        Response<Pagination<ExamDTO>> response = new Response<>();
        response.setCode(0);
        response.setMsg("成功");
        response.setData(pagination);

        return response;
    }

    @ResponseBody
    @PostMapping("/exam")
    public Response addExam(ExamDTO examDTO){
        Response response = new Response();
        response.setCode(-1);
        if(examDTO == null){
            response.setMsg("考试信息为空");
            return response;
        }
        if(examDTO.getName() == null || "".equals(examDTO.getName())){
            response.setMsg("考试名称不能为空");
            return response;
        }
        if(examDTO.getExamAddress() == null || "".equals(examDTO.getExamAddress())){
            response.setMsg("考试地点不能为空");
            return response;
        }
        if(examDTO.getExamDatetime() == null){
            response.setMsg("考试时间不能为空");
            return response;
        }

        return examService.addExam(examDTO);
    }

    @ResponseBody
    @PostMapping("/updateexam")
    public Response updateExam(ExamDTO examDTO){
        Response response = new Response();
        response.setCode(-1);
        if(examDTO == null){
            response.setMsg("考试信息为空");
            return response;
        }
        if("".equals(examDTO.getName())){
            response.setMsg("考试名称不能为空");
            return response;
        }
        if("".equals(examDTO.getExamAddress())){
            response.setMsg("考试地点不能为空");
            return response;
        }
        if(examDTO.getExamDatetime() == null){
            response.setMsg("考试时间不能为空");
            return response;
        }

        return examService.updateExam(examDTO);
    }

    @ResponseBody
    @GetMapping("/deleteexam")
    public Response deleteExam(int id){
        Response response =new Response();
        response.setCode(-1);
        if(id < 0){
            response.setMsg("ID错误");
            return response;
        }

        return examService.deleteExam(id);
    }

    @GetMapping("/admin/exam")
    public String toExamPage(){
        return "/admin/exams";
    }

    @GetMapping("/exam/add")
    public String toExamAdd(){
        return "/admin/addExam";
    }

    @GetMapping("/exam/update")
    public String toExamUpdate(){
        return "/admin/updateExam";
    }

    @GetMapping("/exam/importgrade")
    public String toImportGradeUpload(){
        return "/admin/importgrade";
    }
}
