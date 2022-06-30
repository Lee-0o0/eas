package com.lee.eas.controller;

import com.alibaba.excel.EasyExcel;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;


@Controller
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @ResponseBody
    @PostMapping("/admin/login")
    public Response login(String username,
                          String password,
                          HttpServletRequest request){
        System.out.println(username +" --- "+password);

        Response response = new Response();
        boolean login = adminService.login(username, password);
        if(login){
            request.getSession(true);
            response.setCode(0);
            response.setMsg("成功");
            return response;
        }else{
            response.setCode(-1);
            response.setMsg("用户名或密码错误");
            return response;
        }
    }

    @ResponseBody
    @PostMapping("/admin/unlogin")
    public Response unLogin(HttpServletRequest request){
        Response response = new Response();

        HttpSession session = request.getSession(false);
        if(session == null){
            response.setCode(-1);
            response.setMsg("请先登陆");
            return response;
        }
        session.invalidate();
        response.setCode(0);
        response.setMsg("成功");
        return response;
    }

    @GetMapping("/admin/login")
    public String toLogin(){
        return "/admin/login";
    }

    @GetMapping("/admin/students")
    public String toStudents(){
        return "/admin/students";
    }

    @GetMapping("/admin/exams")
    public String toExams(){
        return "/admin/exams";
    }

    @GetMapping("/admin/grades")
    public String toGrades(){
        return "/admin/grades";
    }

    @PostMapping("/upload")
    public String upload(@RequestPart MultipartFile file) throws IOException {
        // 判断文件是否上传成功，如果是，则保存在磁盘中
        //EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        return "success";

    }

}
