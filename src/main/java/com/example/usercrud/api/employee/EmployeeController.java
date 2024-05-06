package com.example.usercrud.api.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeModel> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<EmployeeModel> getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public EmployeeModel createEmployee(@RequestBody CreateEmployeeDTO employee) throws  Exception{
        return employeeService.save(employee);
    }


    @PutMapping("/{id}")
    public EmployeeModel updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeDto updatedEmployee) throws Exception {
      return employeeService.updateEmployee(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
    }
}
