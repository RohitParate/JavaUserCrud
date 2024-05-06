package com.example.usercrud.api.employee;

import com.example.usercrud.api.user.CreateUserDto;
import lombok.Getter;

@Getter
public class CreateEmployeeDTO extends CreateUserDto {
    private String jobTitle;

    private double salary;

    private String employeeCode;

    private Long companyId;
}
