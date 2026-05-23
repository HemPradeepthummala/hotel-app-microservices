package org.example.hotelauth.controller;

import org.example.hotelauth.exception.UserNotFoundException;
import org.example.hotelauth.service.AppUserDetailsService;
import org.example.hotelauth.service.JwtService;
import org.example.hotelauth.view.LoginRequest;
import org.example.hotelauth.view.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthenticationController {

  private final AppUserDetailsService appUserDetailsService;
    private final JwtService jwtService;
    

    public AuthenticationController(AppUserDetailsService appUserDetailsService, JwtService jwtService) {
    this.appUserDetailsService = appUserDetailsService;
        this.jwtService = jwtService;
    }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody SignUpRequest signUpRequest) {
    String userId = appUserDetailsService.registerUser(signUpRequest);
    return ResponseEntity.ok(userId);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws UserNotFoundException {
    String username = appUserDetailsService.loginUser(loginRequest);
    String token = jwtService.generateToken(username);
    return ResponseEntity.ok(token);
  }

  @GetMapping("/internal/users/{name}")
  public String getUser(@PathVariable String name) {
    return appUserDetailsService.getUserId(name);
  }
}
