package com.example.usercrud.api.employee;

import com.example.usercrud.api.user.UpdateUserDTO;
import lombok.Getter;

@Getter
public class UpdateEmployeeDto extends UpdateUserDTO {
    private String jobTitle;

    private double salary;

    private Long companyId;

    private Long userId;

}
