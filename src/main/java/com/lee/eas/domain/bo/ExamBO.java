package com.lee.eas.domain.bo;

import com.lee.eas.domain.po.GradePO;
import com.lee.eas.domain.po.StudentPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamBO {
    // 考试id
    private int id;
    // 参加考试的学生列表
    private List<StudentPO> studentList;
    // 单次考试的成绩列表
    private List<GradePO> gradeList;
}
