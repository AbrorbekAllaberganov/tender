package com.example.tender.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tender.payload.Result;
import com.example.tender.payload.UserPayload;
import com.example.tender.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600L)
public class UserController {
    private final UserService userService;


    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editUser(@PathVariable UUID id,
                                           @RequestBody UserPayload userPayload) {
        Result result = userService.editUser(id,userPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

   @PutMapping("/change-location")
    public ResponseEntity<Result> locationChange(@RequestParam("userId") UUID userId,
                                                 @RequestParam("lon") long lon,
                                                 @RequestParam("lat")long lat){
        Result result=userService.changeLocation(userId,lon,lat);
       return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
   }

}
