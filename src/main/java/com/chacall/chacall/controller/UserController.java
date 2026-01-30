package com.chacall.chacall.controller;

import com.chacall.chacall.dto.request.UserLoginRequest;
import com.chacall.chacall.dto.request.UserSignupRequest;
import com.chacall.chacall.dto.response.UserLoginResponse;
import com.chacall.chacall.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request, HttpServletRequest req) {

        System.out.println("req.getScheme() = " + req.getScheme());
        System.out.println("req.getServerName() = " + req.getServerName());
        System.out.println("req.getServerPort() = " + req.getServerPort());
        System.out.println("req.getHeader(\"Host\") = " + req.getHeader("Host"));
        System.out.println("req.getHeader(\"Origin\") = " + req.getHeader("Origin"));
        System.out.println("req.getHeader(\"X-Forwarded-Proto\") = " + req.getHeader("X-Forwarded-Proto"));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().build();
    }

    /* 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignupRequest request) {
        userService.join(request.getPhoneNumber(), request.getPassword(), request.getPasswordConfirm());

        URI targetLocation = URI.create("/");
        return ResponseEntity.created(targetLocation).build();
    }
}
