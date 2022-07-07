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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
                }else{
                    continue;
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

    @Transactional
    @Override
    public Response importGrades(int examId, String fileName, MultipartFile file) {
        Response result = new Response();
        result.setCode(-1);
        try {
            // 查询考试是否存在
            ExamPO examById = examMapper.getExamById(examId);
            if(examById == null){
                result.setMsg("考试不存在");
                return result;
            }

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

            // 循环Excel，获取数据
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

                // 科目一
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String subjectOne = row.getCell(2).getStringCellValue();
                    if (subjectOne == null || subjectOne.isEmpty()) {
                        subjectOne = "0";
                    }
                    temporary.setSubjectOne(subjectOne);
                }
                // 科目二
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String subjectTwo = row.getCell(3).getStringCellValue();
                    if (subjectTwo == null || subjectTwo.isEmpty()) {
                        subjectTwo = "0";
                    }
                    temporary.setSubjectTwo(subjectTwo);
                }

                //添加进list
                excelData.add(temporary);
            }

            if(excelData.size() == 0){
                result.setMsg("Excel数据为空！");
                return result;
            }
            // 获取所有的学生数据
            List<StudentPO> studentPOList = studentMapper.queryAll();

            List<GradePO> gradePOList = new ArrayList<>();
            for (StudentWithGradeDTO s : excelData) {
                StudentPO studentPO = studentPOList.stream().filter(x -> x.getStudentNumber().equals(s.getStudentNumber())).findFirst().orElse(null);

                GradePO gradePO = new GradePO();
                gradePO.setExamNumber(examId);
                gradePO.setStudentId(studentPO.getId());

                gradePO.setSubjectOne(s.getSubjectOne());
                gradePO.setSubjectTwo(s.getSubjectTwo());
                gradePO.calculateTotal();

                gradePOList.add(gradePO);
            }
            // 计算排名
            Collections.sort(gradePOList, new Comparator<GradePO>() {
                @Override
                public int compare(GradePO o1, GradePO o2) {
                    try {
                        BigDecimal b1 = new BigDecimal(o1.getTotal());
                        BigDecimal b2 = new BigDecimal(o2.getTotal());
                        return b2.compareTo(b1);
                    }catch (Exception e){}
                    return 0;
                }
            });

            gradePOList.get(0).setRank(1);
            for(int i = 1; i < gradePOList.size(); i++){
                if(gradePOList.get(i-1).getTotal().equals(gradePOList.get(i))){
                    gradePOList.get(i).setRank(gradePOList.get(i-1).getRank());
                }else{
                    gradePOList.get(i).setRank(i+1);
                }
            }

            // 做插入处理
            int i = gradeMapper.insertBatchGrade(gradePOList);

            if(i == gradePOList.size()) {
                result.setCode(0);
                result.setMsg("成功");
            }else{
                result.setMsg("未成功插入");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(-1);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
