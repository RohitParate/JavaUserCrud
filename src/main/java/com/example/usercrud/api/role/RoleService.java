package com.example.usercrud.api.role;

import com.example.usercrud.api.company.CompanyModel;
import com.example.usercrud.api.company.UpdateCompanyDto;
import com.example.usercrud.exceptions.ConflictException;
import com.example.usercrud.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository roleRepository;

    public RoleModel save(CreateRoleDto role){
        boolean roleExist = roleRepository.existsByRoleCode(role.getRoleCode());

        if(roleExist){
            throw new ConflictException("Role Already exists.");
        }

        var newRole = RoleModel.builder()
                .roleName(role.getRoleName())
                .roleCode(role.getRoleCode())
                .build();

        return roleRepository.save(newRole);
    }

    public Optional<RoleModel> findById(Long id){
        var role = roleRepository.findById(id);

        if(role.isEmpty()){
            throw new NotFoundException("Role not found");
        }
        return role;
    }

    public List<RoleModel> findAll(){
        try{
            return roleRepository.findAll();
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve roles.", ex);
        }
    }

    public void deleteById(Long id) {
        var role =  roleRepository.findById(id);
        if(role.isEmpty()){
            throw new NotFoundException( "Failed to retrieve role.");
        }
        RoleModel deleteRole = role.get();
        deleteRole.setDeletedAt(new Date());
        roleRepository.save(deleteRole);
    }

    public RoleModel updateRole(Long id, CreateRoleDto role){
        var checkRoleExist =  roleRepository.findById(id);
        if(checkRoleExist.isEmpty()){
            throw new NotFoundException("Failed to retrieve company.");
        }
        RoleModel existingRole = checkRoleExist.get();

        existingRole.setRoleCode(role.getRoleCode());
        existingRole.setRoleName(role.getRoleName());

        return roleRepository.save(existingRole);

    }
}
