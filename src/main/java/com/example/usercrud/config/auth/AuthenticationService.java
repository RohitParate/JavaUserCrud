package com.example.usercrud.config.auth;

import com.example.usercrud.config.JwtService;
import com.example.usercrud.api.user.UserModel;
import com.example.usercrud.api.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserModel.builder()
                .name(request.getName())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
                .build();

        repository.save(user);

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        }catch (Exception e){
            System.out.println(e);
        }
        var user = repository.findByUserName(request.getUserName());
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
    }
}
