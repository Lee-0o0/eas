package com.lee.eas.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradePO {
    // id
    private int id;
    // 考试序号
    private int examNumber;
    // 学生ID
    private int studentId;
    // 科目一成绩
    private String subjectOne;
    // 科目二成绩
    private String subjectTwo;
    // 总成绩
    private String total;
    // 排名
    private int rank;

    public void calculateTotal(){
        BigDecimal bigDecimal = new BigDecimal(0);
        try{
            bigDecimal = new BigDecimal(subjectOne);
        }catch (Exception e){}

        BigDecimal bigDecimal1 = new BigDecimal(0);
        try{
            bigDecimal1 = new BigDecimal(subjectTwo);
        }catch (Exception e){}

        total = bigDecimal.add(bigDecimal1).toPlainString();
    }
}
