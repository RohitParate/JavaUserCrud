package com.example.usercrud.api.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyRepository extends JpaRepository<CompanyModel, Long> {
    boolean existsByCompanyCode(String companyCode);
}
