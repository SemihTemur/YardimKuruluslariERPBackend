package com.semih.controller;

import com.semih.dto.request.EmailRequest;
import com.semih.dto.request.LoginRequest;
import com.semih.dto.request.ProfileRequest;
import com.semih.dto.request.RegisterRequest;
import com.semih.dto.response.RegisterResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<RestResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = authService.register(registerRequest);
        return new ResponseEntity<>(RestResponse.of(registerResponse), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return new ResponseEntity<>(RestResponse.of(token), HttpStatus.OK);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<RestResponse<String>> updateProfile(@RequestBody ProfileRequest profileRequest) {
        String message = authService.updateProfile(profileRequest);
        return new ResponseEntity<>(RestResponse.of(message), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/getUserList")
    public ResponseEntity<RestResponse<List<RegisterResponse>>> getUserList() {
        List<RegisterResponse> registerResponseList = authService.getUserList();
        return new ResponseEntity<>(RestResponse.of(registerResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/updateUserById/{id}")
    public ResponseEntity<RestResponse<RegisterResponse>> updateUserById(@PathVariable Long id, @RequestBody RegisterRequest registerRequest
    ) {
        RegisterResponse updateRegisterResponse = authService.updateUserById(id, registerRequest);
        return new ResponseEntity<>(RestResponse.of(updateRegisterResponse), HttpStatus.OK);
    }

    @DeleteMapping("deleteUserById/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<RestResponse<RegisterResponse>> deleteUserById(@PathVariable Long id) {
        RegisterResponse deleteRegisterResponse = authService.deleteUserById(id);
        return new ResponseEntity<>(RestResponse.of(deleteRegisterResponse), HttpStatus.OK);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<RestResponse<String>> sendPasswordResetCode(@RequestBody EmailRequest email) {
        String message = authService.sendPasswordResetCode(email);
        return new ResponseEntity<>(RestResponse.of(message), HttpStatus.OK);
    }

    @GetMapping("/token")
    public Map<String, Object> getAuth(@RequestParam String token) {
        return authService.auth(token);
    }

}
