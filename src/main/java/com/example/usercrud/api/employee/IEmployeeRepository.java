package com.example.usercrud.api.employee;

import com.example.usercrud.api.employee.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository  extends JpaRepository<EmployeeModel, Long> {
    boolean existsByEmployeeCode(String employeeCode);
}
