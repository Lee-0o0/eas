package com.lee.eas.controller;

import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import com.lee.eas.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @ResponseBody
    @GetMapping("/grade/{id}")
    public Response<List<StudentWithGradeDTO>> queryExamGradesByExamId(@PathVariable int id){
        return gradeService.queryGrade(id);
    }


    @ResponseBody
    @PostMapping("/upload")
    public Response upload(@RequestParam String examId, @RequestParam("file") MultipartFile file) throws IOException {
        Response result = new Response();
        try {
            if (file == null) {
                // 文件不能为空
                result.setCode(-1);
                result.setMsg("文件不能为空");
                return result;
            }

            String fileName = file.getOriginalFilename();
            if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                // 文件格式不正确
                result.setCode(-1);
                result.setMsg("文件格式不正确");
                return result;
            }

            long size = file.getSize();
            if (fileName == null || "".equals(fileName) || size == 0) {
                // 文件不能为空
                result.setCode(-1);
                result.setMsg("文件不能为空");
                return result;
            }

            int exam = Integer.parseInt(examId);

            return gradeService.importGrades(exam, fileName, file);

        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(-1);
            result.setMsg(e.getMessage());
        }

        return result;
    }

}
