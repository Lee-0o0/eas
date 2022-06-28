package com.lee.eas.controller;

import com.lee.eas.domain.dto.Response;
import com.lee.eas.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @ResponseBody
    @PostMapping("/admin/login")
    public Response login(String username,
                          String password){

        Response response = new Response();
        boolean login = adminService.login(username, password);
        if(login){
            response.setCode(0);
            response.setMsg("成功");
            return response;
        }else{
            response.setCode(-1);
            response.setMsg("用户名或密码错误");
            return response;
        }
    }

    @GetMapping("/admin/login")
    public String toLogin(){
        return "/admin/login";
    }

}
