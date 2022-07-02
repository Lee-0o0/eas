package com.lee.eas.controller;

import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import com.lee.eas.domain.po.StudentPO;
import com.lee.eas.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
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


    /**
     * 查询成绩
     * @param studentNumber
     * @param password
     * @return
     */
    @ResponseBody
    @PostMapping("/student/grade")
    public Response<StudentWithGradeDTO> getStudentGrade(String studentNumber, String password){
        System.out.println(studentNumber+"--"+password);

        Response<StudentWithGradeDTO> response = new Response<>();
        if(studentNumber == null || "".equals(studentNumber)){
            response.setCode(-1);
            response.setMsg("学号为空");
        }else if(password == null || "".equals(password)){
            response.setCode(-1);
            response.setMsg("密码为空");
        }else{
            response = studentService.queryGrade(studentNumber,password);
        }

        return response;
    }


    @ResponseBody
    @PostMapping("/student")
    public Response addStudent(String name , String studentNumber){
        Response response = new Response();
        response.setCode(-1);
        if(name == null || "".equals(name)){
            response.setMsg("学生姓名不能为空");
            return response;
        }
        if(studentNumber == null || "".equals(studentNumber)){
            response.setMsg("学号不能为空");
            return response;
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(name);
        studentDTO.setStudentNumber(studentNumber);
        return studentService.insertStudent(studentDTO);
    }

    @GetMapping("/student/updatepassword")
    public String toUpdatePassword(){
        return "/updatePassword";
    }

    @GetMapping("/student/add")
    public  String toAddStudent(){
        return "/admin/addStudent";
    }

    @GetMapping("/student/update")
    public String toUpdateStudent(){
        return "/admin/updateStudent";
    }

    @ResponseBody
    @PostMapping("/student/updatepassword")
    public Response updatePassword(String studentNumber,String oldPassword,String newPassword){
        System.out.println(studentNumber);
        System.out.println(oldPassword);
        System.out.println(newPassword);

        Response response = new Response();
        response.setCode(-1);
        if(studentNumber == null || "".equals(studentNumber)){
            response.setMsg("学号不能为空");
            return response;
        }
        if(oldPassword == null || "".equals(oldPassword)){
            response.setMsg("原密码不能为空");
            return response;
        }
        if(newPassword == null || "".equals(newPassword)){
            response.setMsg("新密码不能为空");
            return response;
        }

        return studentService.updatePassword(studentNumber, oldPassword, newPassword);
    }

    @ResponseBody
    @PostMapping("/updatestudent")
    public Response updateStudent(StudentDTO studentDTO){
        System.out.println(studentDTO);
        Response response = new Response();
        response.setCode(-1);
        if( studentDTO==null || studentDTO.getId() <= 0){
            response.setMsg("未查询到该学生信息");
            return response;
        }

        return studentService.updateStudent(studentDTO);
    }

    @ResponseBody
    @GetMapping("/student/delete")
    public Response deleteStudent(int id){
        Response response = new Response();
        response.setCode(-1);

        if(id < 0){
            response.setMsg("ID违法");
            return response;
        }

        return studentService.deleteStudent(id);
    }

}
