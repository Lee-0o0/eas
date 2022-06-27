package com.lee.eas.controller;

import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @GetMapping("/student")
    public Response<Pagination<StudentDTO>> queryStudents(@RequestParam int page,
                                                          @RequestParam int pageSize){
        if(page <= 0){
            page = 1;
        }

        return null;
    }

}
