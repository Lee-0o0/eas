package com.lee.eas.controller;

import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class StudentController {

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


        response.setCode(200);
        response.setMsg("");
        Pagination<StudentDTO> pagination = new Pagination<>();
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        pagination.setTotal(new Random().nextInt() + 100);

        List<StudentDTO> studentDTOList = new ArrayList<>() ;
        for(int i = 0; i < pageSize; i++){
            studentDTOList.add(new StudentDTO(i,"姓名" + i,"学号"+i));
        }
        pagination.setData(studentDTOList);

        response.setData(pagination);
        return response;
    }

    @GetMapping("/admin/index")
    public String toIndex(){
        return "/admin/index";
    }

}
