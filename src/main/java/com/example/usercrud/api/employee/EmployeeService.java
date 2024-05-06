package com.example.usercrud.api.employee;

import com.example.usercrud.api.company.CompanyModel;
import com.example.usercrud.api.company.ICompanyRepository;
import com.example.usercrud.api.employee.CreateEmployeeDTO;
import com.example.usercrud.api.employee.EmployeeModel;
import com.example.usercrud.api.user.UpdateUserDTO;
import com.example.usercrud.api.user.UserModel;
import com.example.usercrud.api.employee.IEmployeeRepository;
import com.example.usercrud.api.user.IUserRepository;
import com.example.usercrud.api.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final IUserRepository userRepository;
    private final ICompanyRepository companyRepository;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    public EmployeeModel save(CreateEmployeeDTO employee) throws Exception {
        //check first user exist or not
        boolean userNameExists = userRepository.existsByName(employee.getName());

        if (userNameExists) {
            throw new Exception( "User with this name already exists.");
        }

        //check company exist or not
        var companyExist = companyRepository.findById(employee.getCompanyId());
        if(companyExist.isEmpty()){
            throw new Exception( "Unable to find company");
        }

        //check if employee exist or not
        boolean employeeExist = employeeRepository.existsByEmployeeCode(employee.getEmployeeCode());

        if(employeeExist){
            throw new Exception( "Employee with this Code already exists.");
        }

        //create user and save.

        var newUser = UserModel.builder()
                .name(employee.getName())
                .userName(employee.getUserName())
                .password(passwordEncoder.encode(employee.getPassword()))
                .build();

        userRepository.save(newUser);

        //create employee and save

        var newEmployee = EmployeeModel.builder()
                .employeeCode(employee.getEmployeeCode())
                .salary(employee.getSalary())
                .jobTitle(employee.getJobTitle())
                .company(companyExist.get())
                .user(newUser)
                .build();


        return employeeRepository.save(newEmployee);
    }

    public EmployeeModel updateEmployee (Long id, UpdateEmployeeDto updateEmployeeData) throws Exception{

        var employee =  employeeRepository.findById(id);
        if(employee.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to retrieve Employee.");
        }

        //check first user exist or not
       var existingUser = userRepository.findById(updateEmployeeData.getUserId());

        if(existingUser.isEmpty()){
            throw new Exception( "Unable to find user");
        }

        //check company exist or not
        var companyExist = companyRepository.findById(updateEmployeeData.getCompanyId());
        if(companyExist.isEmpty()){
            throw new Exception( "Unable to find company");
        }

        //update user and save.

        UpdateUserDTO updateUserData = new UpdateUserDTO();
        updateUserData.setName(updateEmployeeData.getName());

        var updatedUser = userService.updateUser(updateEmployeeData.getUserId(), updateUserData);

        //create employee and save

        var newEmployee = EmployeeModel.builder()
//                .employeeCode(updateEmployeeData.getEmployeeCode())
                .salary(updateEmployeeData.getSalary())
                .jobTitle(updateEmployeeData.getJobTitle())
                .company(companyExist.get())
//                .user(updatedUser)
                .build();


        return employeeRepository.save(newEmployee);


    }

    public Optional<EmployeeModel> findById(Long id) {
        var employee =  employeeRepository.findById(id);

        if(employee.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to retrieve user.");
        }
        return employee;
    }

    public List<EmployeeModel> findAll() {
        try{
            return employeeRepository.findAll();
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve Employees.", ex);
        }
    }

    public void deleteById(Long id) {
        var employee =  employeeRepository.findById(id);
        if(employee.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to retrieve user.");
        }
        EmployeeModel deleteEmployee = employee.get();
        deleteEmployee.setDeletedAt(new Date());
        employeeRepository.save(deleteEmployee);
    }

}
