package com.lee.eas.controller;

import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import com.lee.eas.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @ResponseBody
    @GetMapping("/student")
    public Response<Pagination<StudentDTO>> queryStudents(@RequestParam int page,
                                                          @RequestParam int pageSize){
        if(page <= 0){
            page = 1;
        }
        if(pageSize <= 0){
            pageSize = 10;
        }

        Response<Pagination<StudentDTO>> response = new Response<>();

        Pagination<StudentDTO> pagination;
        try {
            pagination = studentService.queryStudent(page, pageSize);
        }catch (Exception e){
            response.setCode(-1);
            response.setMsg(e.getMessage());
            return response;
        }
        response.setCode(0);
        response.setMsg("成功");
        response.setData(pagination);
        return response;
    }



    @ResponseBody
    @PostMapping("/student")
    public Response addStudent(StudentDTO studentDTO){
        boolean insertStudent = studentService.insertStudent(studentDTO);

        Response response = new Response();
        if(insertStudent){
            response.setCode(0);
            response.setMsg("成功");
        }else{
            response.setCode(-1);
            response.setMsg("失败");
        }

        return response;
    }

    @GetMapping("/updatepassword")
    public String toUpdatePassword(){
        return "/updatePassword";
    }

}
