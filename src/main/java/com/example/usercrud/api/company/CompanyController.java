package com.example.usercrud.api.company;

import com.example.usercrud.exceptions.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<CustomResponse> getCompanies(){
        var companies = companyService.findAll();

        CustomResponse response = new CustomResponse(true, "companies fetched successfully", HttpStatus.OK.value(), companies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCompanyById(@PathVariable Long id){
        var company = companyService.findById(id);
        CustomResponse response = new CustomResponse(true, "company fetched successfully", HttpStatus.OK.value(), company);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createCompany(@RequestBody CreateCompanyDto companyDto) throws Exception{
        var newCompany= companyService.save(companyDto);
        CustomResponse response = new CustomResponse(true, "company created successfully", HttpStatus.CREATED.value(), newCompany);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public  ResponseEntity<CustomResponse> updateCompany(@PathVariable Long id, @RequestBody UpdateCompanyDto updateCompany) {
        var updatedCompany = companyService.updateCompany(id, updateCompany);
        CustomResponse response = new CustomResponse(true, "company updated successfully", HttpStatus.OK.value(), updatedCompany);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteCompany(@PathVariable Long id){

        companyService.deleteById(id);

        CustomResponse response = new CustomResponse(true, "company deleted successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
