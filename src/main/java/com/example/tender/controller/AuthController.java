package com.example.tender.controller;

import com.example.tender.payload.Result;
import com.example.tender.payload.UserPayload;
import com.example.tender.service.SmsService;
import com.example.tender.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.example.tender.entity.users.Parent;
import com.example.tender.payload.LoginPayload;
import com.example.tender.repository.ParentRepository;
import com.example.tender.security.JwtTokenProvider;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600L)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ParentRepository parentRepository;
    private final SmsService smsService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload payload) {
        Parent parent = parentRepository.findByPhoneNumber(payload.getPhoneNumber());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getPhoneNumber(), payload.getPassword()));
        String token = jwtTokenProvider.createToken(parent.getPhoneNumber(), parent.getRoles());

        if (token == null) {
            return new ResponseEntity<>("Xatolik", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String, Object> login = new HashMap<>();
        login.put("token", token);
        login.put("username", parent.getPhoneNumber());
        login.put("success", true);
        return ResponseEntity.ok(login);
    }

    @GetMapping("/send-sms/{phoneNumber}")
    public ResponseEntity<?> sendSms(@PathVariable("phoneNumber") String phoneNumber) {
        Result result = smsService.sendSms(phoneNumber);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check-sms")
    public ResponseEntity<?> checkSmsCode(@RequestParam("phoneNumber") String phoneNumber,
                                          @RequestParam("code") String code) {
        return smsService.checkCode(phoneNumber, code);
    }

    @PostMapping("/register")
    public ResponseEntity<Result> saveUser(@RequestBody @Valid UserPayload userPayload) {
        Result result = userService.saveUser(userPayload);
        return ResponseEntity
                .status(result.isStatus() ? 200 : 400)
                .body(result);
    }
}
