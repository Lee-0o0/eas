package com.lee.eas.service.impl;

import com.lee.eas.domain.dto.ExamDTO;
import com.lee.eas.domain.dto.Pagination;
import com.lee.eas.domain.po.ExamPO;
import com.lee.eas.mapper.ExamMapper;
import com.lee.eas.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements IExamService {

    @Autowired
    private ExamMapper examMapper;

    @Override
    public Pagination<ExamPO> queryExam(int page, int pageSize) {
        int total = examMapper.countExam();

        List<ExamPO> examPOS = examMapper.queryAllExam((page - 1) * pageSize, pageSize);

        Pagination<ExamPO> pagination = new Pagination<>();
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        pagination.setTotal(total);
        pagination.setData(examPOS);

        return pagination;
    }

    @Override
    public boolean addExam(ExamDTO examDTO) {
        ExamPO examPO = new ExamPO();
        examPO.setName(examDTO.getName());
        examPO.setExamAddress(examDTO.getExamAddress());
        examPO.setExamDatetime(examDTO.getExamDatetime());
        return examMapper.insertExam(examPO) == 1;
    }

    @Override
    public boolean updateExam(ExamDTO examDTO) {

        ExamPO examPO = examMapper.getExamById(examDTO.getId());
        if(examPO == null)
            return false;

        examPO.setName(examDTO.getName());
        examPO.setExamAddress(examDTO.getExamAddress());
        examPO.setExamDatetime(examDTO.getExamDatetime());

        return examMapper.updateExam(examPO) == 1;
    }

    @Override
    public boolean deleteExam(int id) {
        return examMapper.deleteExam(id) == 1;
    }
}
