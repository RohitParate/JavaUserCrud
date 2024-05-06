package com.example.usercrud.api.user;

import com.example.usercrud.api.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    // JpaRepository belongs to spring JPA library
    boolean existsByName(String name);

    UserModel findByUserName(String userName);

    List<UserModel> findByDeletedAtIsNull();

}
