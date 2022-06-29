package com.lee.eas.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private int id;
    private String name;
    private String examAddress;
    private LocalDateTime examDatetime;
}
