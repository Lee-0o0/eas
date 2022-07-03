package com.lee.eas.service.impl;

import com.lee.eas.domain.dto.ExamDTO;
import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.dto.Response;
import com.lee.eas.domain.po.ExamPO;
import com.lee.eas.mapper.ExamMapper;
import com.lee.eas.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceImpl implements IExamService {

    @Autowired
    private ExamMapper examMapper;

    @Override
    public Pagination<ExamDTO> queryExam(int page, int pageSize) {
        int total = examMapper.countExam();

        List<ExamPO> examPOS = examMapper.queryAllExam((page - 1) * pageSize, pageSize);

        Pagination<ExamDTO> pagination = new Pagination<>();
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        pagination.setTotal(total);
        List<ExamDTO> examDTOS = new ArrayList<>();
        for (ExamPO e : examPOS) {
            ExamDTO examDTO = new ExamDTO();
            examDTO.setId(e.getId());
            examDTO.setName(e.getName());
            examDTO.setExamAddress(e.getExamAddress());
            examDTO.setExamDatetime(e.getExamDatetime());
            examDTOS.add(examDTO);
        }
        pagination.setData(examDTOS);

        return pagination;
    }

    @Override
    public Response addExam(ExamDTO examDTO) {
        Response response = new Response();

        ExamPO examPO = new ExamPO();
        examPO.setName(examDTO.getName());
        examPO.setExamAddress(examDTO.getExamAddress());
        examPO.setExamDatetime(examDTO.getExamDatetime());

        try{
            boolean b = examMapper.insertExam(examPO) == 1;
            if(!b){
                response.setCode(-1);
                response.setMsg("新增考试失败");
            }else{
                response.setCode(0);
                response.setMsg("成功");
            }
        }catch (Exception e){
            response.setCode(-1);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateExam(ExamDTO examDTO) {

        Response response = new Response();
        response.setCode(-1);
        ExamPO examPO = examMapper.getExamById(examDTO.getId());
        if(examPO == null){
            response.setMsg("未查询到考试信息");
            return response;
        }

        examPO.setName(examDTO.getName());
        examPO.setExamAddress(examDTO.getExamAddress());
        examPO.setExamDatetime(examDTO.getExamDatetime());

        try{
            boolean b = examMapper.updateExam(examPO) == 1;
            if(!b){
                response.setMsg("更新失败");
            }else{
                response.setCode(0);
                response.setMsg("成功");
            }
        }catch (Exception e){
            response.setMsg(e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteExam(int id) {
        Response response = new Response();
        response.setCode(-1);

        try{
            boolean b = examMapper.deleteExam(id) == 1;
            if(!b){
                response.setMsg("删除失败");
            }else{
                response.setCode(0);
                response.setMsg("成功");
            }
        }catch (Exception e){
            response.setMsg(e.getMessage());
        }

        return response;
    }
}
