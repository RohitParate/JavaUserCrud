package com.example.usercrud.api.company;

import com.example.usercrud.api.employee.EmployeeModel;
import com.example.usercrud.exceptions.ConflictException;
import com.example.usercrud.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final ICompanyRepository companyRepository;

    public CompanyModel save(CreateCompanyDto company){
        boolean companyExist = companyRepository.existsByCompanyCode(company.getCompanyCode());

        if(companyExist){
            throw new ConflictException("Company Already exists.");
        }

        //create

        var newCompany = CompanyModel.builder()
                .companyName(company.getCompanyName())
                .companyCode(company.getCompanyCode())
                .build();

        return companyRepository.save(newCompany);
    }

    public Optional<CompanyModel> findById(Long id){
        var company = companyRepository.findById(id);

        if(company.isEmpty()){
            throw new NotFoundException("Failed to retrieve company.");
        }
        return company;
    }

    public List<CompanyModel> findAll() {
        try{
            return companyRepository.findAll();
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve Employees.", ex);
        }
    }

    public void deleteById(Long id) {
        var company =  companyRepository.findById(id);
        if(company.isEmpty()){
            throw new NotFoundException( "Failed to retrieve company.");
        }
        CompanyModel deleteEmployee = company.get();
        deleteEmployee.setDeletedAt(new Date());
        companyRepository.save(deleteEmployee);
    }

    public CompanyModel updateCompany(Long id, UpdateCompanyDto company){
        var checkCompanyExist =  companyRepository.findById(id);
        if(checkCompanyExist.isEmpty()){
            throw new NotFoundException("Failed to retrieve company.");
        }
        CompanyModel existingCompany = checkCompanyExist.get();

        existingCompany.setCompanyCode(company.getCompanyCode());
        existingCompany.setCompanyName(company.getCompanyName());

        return companyRepository.save(existingCompany);

    }
}
