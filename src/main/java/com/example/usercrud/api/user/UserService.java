package com.example.usercrud.api.user;

import com.example.usercrud.exceptions.BadRequestException;
import com.example.usercrud.exceptions.ConflictException;
import com.example.usercrud.exceptions.CustomResponse;
import com.example.usercrud.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final IUserRepository userRepository;

    public List<UserModel> findAll() {
        try {
            return userRepository.findByDeletedAtIsNull();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve users.", ex);
        }
    }

    public UserModel findById(Long id) {
        //           return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
//            var user = userRepository.findById(id);
//            if (user.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to retrieve user.");
//            }
//            return user;
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user with id :" +id+ " Not found"));
    }

    public UserModel save(CreateUserDto user) {
      var existingUser = userRepository.findByUserName(user.getUserName());
      if(existingUser != null){
          throw new ConflictException("User already exist");
      }
            var newUser = UserModel.builder()
                    .name(user.getName())
                    .userName(user.getUserName())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();

            return userRepository.save(newUser);

    }

    public UserModel updateUser(Long id, UpdateUserDTO updatedEmployee) {
//        try {
//            Optional<UserModel> optionalUser = userRepository.findById(id);
//
//            if (optionalUser.isPresent()) {
//                UserModel existingUser = optionalUser.get();
//
//                existingUser.setName(updatedEmployee.getName());
////                existingUser.setUserName(updatedEmployee.getUserName());
//
//                return userRepository.save(existingUser);
//            } else {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
//            }
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update user.", ex);
//        }

        //check for user against id
        Optional<UserModel> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }
        UserModel existingUser = optionalUser.get();

        existingUser.setName(updatedEmployee.getName());

        return userRepository.save(existingUser);

    }



    public void deleteById(Long id) {
        Optional<UserModel> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        UserModel user = optionalUser.get();
        // Set the 'deleted' flag to true instead of physically deleting the user
        user.setDeletedAt(new Date());
        userRepository.save(user);
    }

    }
