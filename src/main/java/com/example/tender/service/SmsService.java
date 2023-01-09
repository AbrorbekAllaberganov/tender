package com.example.tender.service;

import com.example.tender.entity.ConfirmCode;
import com.example.tender.entity.users.Parent;
import com.example.tender.entity.users.User;
import com.example.tender.json.login.LoginResult;
import com.example.tender.payload.Result;
import com.example.tender.repository.ConfirmCodeRepository;
import com.example.tender.repository.UserRepository;
import com.example.tender.security.JwtTokenProvider;
import com.example.tender.security.SmsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
@Service
@Slf4j
public class SmsService {
    private final ConfirmCodeRepository confirmCodeRepository;

    public ResponseEntity<?> checkCode(String phoneNumber, String code) {
        ConfirmCode confirmCode = confirmCodeRepository.findByPhoneNumber(phoneNumber);
        Date now = new Date();
        if (now.getTime() - confirmCode.getCreatAt().getTime() <= 120000) {
            confirmCodeRepository.delete(confirmCode);
            if (code.equals(confirmCode.getCode())) {
                return ResponseEntity.ok(new Result("success", true));
            } else {
                return ResponseEntity.ok(new Result("Code isn't correct", false));
            }
        }
        return ResponseEntity.ok(new Result("code expired", false));
    }

    //,initialDelay = 1000L
    @Scheduled(fixedDelay = 36000000L, initialDelay = 1000L)
    public void getToken() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://notify.eskiz.uz/api/auth/login";
        Map<String, String> params = new HashMap<>();
        params.put("email", "test@eskiz.uz");
        params.put("password", "j6DWtQjjpLDNjWEk74Sx");
        ResponseEntity<LoginResult> res = restTemplate.postForEntity(url, params, LoginResult.class);

        SmsConstant.setToken(res.getBody().getData().getToken());
    }

    public Result sendSms(String phoneNumber) {
        String url = "https://notify.eskiz.uz/api/message/sms/send";
        String code = GetPassword();
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("mobile_phone", phoneNumber);
        params.put("message", "Kod : " + code);
        params.put("from", "4546");
        params.put("callback_url", "http://0000.uz/test.php");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(SmsConstant.getToken());

        HttpEntity<?> req = new HttpEntity<>(params, headers);
        System.out.println(code);

//        ResponseEntity<String> res = restTemplate.postForEntity(url, req, String.class);
        try {
            if (confirmCodeRepository.findByPhoneNumber(phoneNumber) != null)
                confirmCodeRepository.delete(confirmCodeRepository.findByPhoneNumber(phoneNumber));

            ConfirmCode confirmCode = new ConfirmCode();
            confirmCode.setPhoneNumber(phoneNumber);
            confirmCode.setCode(code);

            confirmCodeRepository.save(confirmCode);

            return new Result("success", true, confirmCode);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new Result("error", false);
    }

    public static String GetPassword() {
        String password = "";
        Random random = new Random();
        password += String.valueOf(random.nextInt(899999) + 100000);

        return password;
    }
}
