package com.example.demo.service;

import com.example.demo.entity.sms.ConfirmCode;
import com.example.demo.entity.superEntity.Parent;
import com.example.demo.entity.users.User;
import com.example.demo.json.login.LoginResult;
import com.example.demo.payload.Result;
import com.example.demo.repository.ConfirmCodeRepository;
import com.example.demo.repository.userRepository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.SmsConstant;
import lombok.RequiredArgsConstructor;
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
public class SmsService {
    private final ConfirmCodeRepository confirmCodeRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Logger logger= LoggerFactory.getLogger(SmsService.class);

    public ResponseEntity<?> checkCode(String phoneNumber, String code) {
        ConfirmCode confirmCode = confirmCodeRepository.findByPhoneNumber(phoneNumber);
        Date now = new Date();
        if (now.getTime() - confirmCode.getCreatAt().getTime() <= 120000) {
            if (code.equals(confirmCode.getCode())) {
                confirmCodeRepository.delete(confirmCode);
                User user=userService.savePhone(phoneNumber);

                Parent parent=user.getParent();

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(parent.getUserName(), parent.getPhoneNumber()));
                String token = jwtTokenProvider.createToken(parent.getUserName(), parent.getRoles());

                if (token == null) {
                    return new ResponseEntity("Xatolik", HttpStatus.INTERNAL_SERVER_ERROR);
                }

                Map<String, Object> login = new HashMap<>();
                login.put("token", token);
                login.put("username", parent.getUserName());
                login.put("success", true);
                login.put("data",parent);

                return ResponseEntity.ok(login);

            } else {
                confirmCodeRepository.delete(confirmCode);
                return ResponseEntity.ok(new Result("Code error",false));
            }
        }

        return ResponseEntity.ok(new Result("Time out", false));
    }
//,initialDelay = 1000L
    @Scheduled(fixedDelay = 36000000L,initialDelay = 1000L)
    public void getToken(){
        RestTemplate restTemplate=new RestTemplate();
        String url="https://notify.eskiz.uz/api/auth/login";
        Map<String,String>params=new HashMap<>();
        params.put("email","test@eskiz.uz");
        params.put("password","j6DWtQjjpLDNjWEk74Sx");
        ResponseEntity<LoginResult>res=restTemplate.postForEntity(url,params,LoginResult.class);

        SmsConstant.setToken(res.getBody().getData().getToken());
    }

    public Result sendSms(String phoneNumber){
        String url="https://notify.eskiz.uz/api/message/sms/send";
        String code=GetPassword();
        RestTemplate restTemplate=new RestTemplate();
        Map<String,String> params=new HashMap<>();
        params.put("mobile_phone",phoneNumber);
        params.put("message","Kod : "+code);
        params.put("from","4546");
        params.put("callback_url","http://0000.uz/test.php");

        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(SmsConstant.getToken());

        HttpEntity<?>req=new HttpEntity<>(params,headers);

        ResponseEntity<String>res=restTemplate.postForEntity(url,req,String.class);
        try {
            if(confirmCodeRepository.findByPhoneNumber(phoneNumber)!=null)
                confirmCodeRepository.delete(confirmCodeRepository.findByPhoneNumber(phoneNumber));

            ConfirmCode confirmCode = new ConfirmCode();
            confirmCode.setPhoneNumber(phoneNumber);
            confirmCode.setCode(code);

            confirmCodeRepository.save(confirmCode);

            return new Result("success",true,confirmCode);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return new Result("error",false);
    }

    public static String GetPassword() {
        String password = "";
        Random random = new Random();
        password += String.valueOf(random.nextInt(899999) + 100000);

        return password;
    }
}
