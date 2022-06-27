package com.lee.eas.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorPO {
    // ID
    private int id;
    // 用户名
    private String username;
    // 密码
    private String password;
}
