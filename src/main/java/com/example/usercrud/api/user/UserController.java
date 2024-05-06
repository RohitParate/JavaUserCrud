package com.example.usercrud.api.user;

import com.example.usercrud.exceptions.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class)) }),
//            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
//                    content = @Content),
            @ApiResponse(responseCode = "404", description = "users not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<CustomResponse> getAllEmployees() {
        var users = userService.findAll();

        CustomResponse response = new CustomResponse(true, "Created Successfully", HttpStatus.OK.value(), users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getUserById  (@PathVariable Long id) {

        UserModel user = userService.findById(id);

        CustomResponse response = new CustomResponse(true, "Find successfully", HttpStatus.OK.value(), user);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CustomResponse> createUser(@Valid @RequestBody CreateUserDto userDto) {

        UserModel newUser = userService.save(userDto);

        CustomResponse response = new CustomResponse(true, "Created Successfully", HttpStatus.CREATED.value(), newUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updatedEmployee) {
        UserModel updateResponse =  userService.updateUser(id, updatedEmployee);

        CustomResponse response = new CustomResponse(true, "Updated Successfully", HttpStatus.OK.value(), updateResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteEmployee(@PathVariable Long id) {

        userService.deleteById(id);

        CustomResponse response = new CustomResponse(true, "deleted successfully", HttpStatus.NO_CONTENT.value());

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
