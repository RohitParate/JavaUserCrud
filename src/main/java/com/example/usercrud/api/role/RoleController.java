package com.example.usercrud.api.role;

import com.example.usercrud.exceptions.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@CrossOrigin
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<CustomResponse> getRoles(){
        var roles = roleService.findAll();

        CustomResponse response = new CustomResponse(true, "Roles fetched successfully", HttpStatus.OK.value(), roles);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getRoleById(@PathVariable Long id){
        var role = roleService.findById(id);
        CustomResponse response = new CustomResponse(true, "role fetched successfully", HttpStatus.OK.value(), role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createRole(@RequestBody CreateRoleDto roleDto) throws Exception{
        var newRole= roleService.save(roleDto);
        CustomResponse response = new CustomResponse(true, "Role created successfully", HttpStatus.CREATED.value(), newRole);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public  ResponseEntity<CustomResponse> updateRole(@PathVariable Long id, @RequestBody CreateRoleDto updateRole) {
        var updatedRole = roleService.updateRole(id, updateRole);
        CustomResponse response = new CustomResponse(true, "Role updated successfully", HttpStatus.OK.value(), updatedRole);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteRole(@PathVariable Long id){

        roleService.deleteById(id);

        CustomResponse response = new CustomResponse(true, "Role deleted successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
