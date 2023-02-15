package com.example.budgetappv2.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.budgetappv2.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserSecurityController {

    private final AuthenticationManager authorizationManager;

    public UserSecurityController(AuthenticationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login (@RequestBody UserDto userDto){
        Authentication authentication = authorizationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.username(),
                        userDto.password()
                ));
        User user = (User) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("auth0")
                .sign(algorithm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
