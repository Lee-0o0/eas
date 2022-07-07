package com.lee.eas.domain.dto;

import com.lee.eas.domain.po.ExamPO;
import com.lee.eas.domain.po.GradePO;
import com.lee.eas.domain.po.StudentPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithGradeDTO{
    private String name;
    private String studentNumber;
    private String examName;
    private String subjectOne;
    private String subjectTwo;
    private String total;
    private int rank;

    public void set(StudentPO studentPO, ExamPO examPO, GradePO gradePO){
        this.name = studentPO.getName();
        this.studentNumber = studentPO.getStudentNumber();
        this.examName = examPO.getName();
        this.subjectOne = gradePO.getSubjectOne();
        this.subjectTwo = gradePO.getSubjectTwo();
        this.total = gradePO.getTotal();
        this.rank = gradePO.getRank();
    }

}
