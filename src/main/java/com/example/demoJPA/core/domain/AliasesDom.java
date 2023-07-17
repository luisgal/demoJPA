package com.example.demoJPA.core.domain;

import com.example.demoJPA.core.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AliasesDom {
    private String id;
    private String firstname;
    private String lastname;
    private LocalDate dob;
    private Gender gender;
}
