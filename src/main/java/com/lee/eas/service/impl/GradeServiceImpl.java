package com.lee.eas.service.impl;

import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.dto.StudentWithGradeDTO;
import com.lee.eas.domain.po.ExamPO;
import com.lee.eas.domain.po.GradePO;
import com.lee.eas.domain.po.StudentPO;
import com.lee.eas.mapper.ExamMapper;
import com.lee.eas.mapper.GradeMapper;
import com.lee.eas.mapper.StudentMapper;
import com.lee.eas.service.IGradeService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Response<List<StudentWithGradeDTO>> queryLatestExamGrade() {
        Response<List<StudentWithGradeDTO>> response = new Response<>();
        response.setCode(-1);
        try {
            int examMaxId = examMapper.getExamMaxId();
            if (examMaxId < 0) {
                throw  new Exception("不存在考试");
            }

            return queryGrade(examMaxId);
        }catch (Exception e){
            response.setMsg(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<List<StudentWithGradeDTO>> queryGrade(int examId) {
        Response<List<StudentWithGradeDTO>> response = new Response<>();
        response.setCode(-1);
        try {
            if (examId < 0) {
                throw  new Exception("不存在考试");
            }
            ExamPO examById = examMapper.getExamById(examId);
            List<GradePO> gradePOS = gradeMapper.queryGradeByExamId(examId);
            List<StudentPO> studentPOS = studentMapper.queryAll();

            List<StudentWithGradeDTO> studentWithGradeDTOList = new ArrayList<>();
            for (StudentPO student:studentPOS) {
                StudentWithGradeDTO studentWithGradeDTO = new StudentWithGradeDTO();
                studentWithGradeDTO.setExamName(examById.getName());
                studentWithGradeDTO.setName(student.getName());
                studentWithGradeDTO.setStudentNumber(student.getStudentNumber());

                GradePO gradePO = gradePOS.stream().filter(x -> x.getStudentId() == student.getId()).findFirst().orElse(null);
                if(gradePO != null){
                    studentWithGradeDTO.setSubjectOne(gradePO.getSubjectOne());
                    studentWithGradeDTO.setSubjectTwo(gradePO.getSubjectTwo());
                    studentWithGradeDTO.setTotal(gradePO.getTotal());
                    studentWithGradeDTO.setRank(gradePO.getRank());
                }

                studentWithGradeDTOList.add(studentWithGradeDTO);
            }

            response.setCode(0);
            response.setMsg("成功");
            response.setData(studentWithGradeDTOList);
        }catch (Exception e){
            response.setMsg(e.getMessage());
        }
        return response;
    }

    @Override
    public Response importGrades(int examId, String fileName, MultipartFile file) {
        Response result = new Response();
        result.setCode(-1);
        try {
            InputStream inputStream = file.getInputStream();
            Workbook wb;
            if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                wb = new XSSFWorkbook(inputStream);
            } else {
                wb = new HSSFWorkbook(inputStream);
            }
            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) {
                result.setMsg("Excel数据为空！");
                return result;
            }

            List<StudentWithGradeDTO> excelData = new ArrayList<>();
            StudentWithGradeDTO temporary;

            // 循环Excel
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                temporary = new StudentWithGradeDTO();

                // 学生姓名
                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String userName = row.getCell(0).getStringCellValue();
                    temporary.setName(userName);
                }

                // 学号
                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String studentNumber = row.getCell(1).getStringCellValue();
                    if (studentNumber == null || studentNumber.isEmpty()) {
                        result.setMsg("Excel中学号为必填项，不能为空，请填写后再进行上传！");
                        return result;
                    }
                    temporary.setStudentNumber(studentNumber);
                }

                // 手机号
//                if (row.getCell(1) != null) {
//                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//                    String mobile = row.getCell(1).getStringCellValue();
//                    if (mobile == null || mobile.isEmpty()) {
//                        result.setMsg("Excel中用户手机号为必填项，不能为空，请填写后再进行上传！");
//                        return result;
//                    }
//                    temporary.setMobile(mobile);
//                }
//
//                // QQ
//                if (row.getCell(2) != null) {
//                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//                    String qq = row.getCell(2).getStringCellValue();
//                    if (qq == null || qq.isEmpty()) {
//                        result.setMsg("Excel中用户QQ为必填项，不能为空，请填写后再进行上传！");
//                        return result;
//                    }
//                    temporary.setQq(qq);
//                }
                //添加进list
                excelData.add(temporary);
            }

            // 此处省略其他操作处理
            // 此处省略其他操作处理

            // 做插入处理
//            if (excelData.size() > 0) {
//                // 将Excel数据插入数据库
//                int i = uploadEexcelMapper.insertExcelData(excelData);
//                if (i == excelData.size()) {
//                    // 数据全部插入成功
//                    result.setMessage("success");
//                }
//            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
