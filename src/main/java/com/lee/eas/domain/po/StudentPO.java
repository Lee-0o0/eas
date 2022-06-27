package com.lee.eas.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentPO {
    // ID
    private int id;
    // 姓名
    private String name;
    // 密码
    private String password;
    // 学号
    private String studentNumber;
}
