package com.example.tender.service;

import uz.abror.myproject.entity.users.Parent;
import uz.abror.myproject.entity.users.User;
import uz.abror.myproject.exceptions.ResourceNotFound;
import uz.abror.myproject.payload.Result;
import uz.abror.myproject.payload.UserPayload;
import uz.abror.myproject.repository.ParentRepository;
import uz.abror.myproject.repository.RoleRepository;
import uz.abror.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ParentRepository parentRepository;

    public Result  saveUser(UserPayload userPayload){
        try {
            User user = new User();

            Parent parent = new Parent();
            parent.setUserName(userPayload.getUserName());
            parent.setPassword(userPayload.getPassword());
            parent.setPhoneNumber(userPayload.getPhoneNumber());
            parent.setFullName(userPayload.getFullName());
            parent.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
            parentRepository.save(parent);

            user.setParent(parent);
            userRepository.save(user);

            return Result.success(user);
        }catch (Exception e){
            return Result.error(e);
        }
    }

    public Result  editUser(UUID userId,UserPayload userPayload){
        try {
            User user = userRepository.findById(userId).orElseThrow(
                    ()->new ResourceNotFound("user","id",userId));

            Parent parent =user.getParent();
            parent.setUserName(userPayload.getUserName());
            parent.setPassword(userPayload.getPassword());
            parent.setPhoneNumber(userPayload.getPhoneNumber());
            parent.setFullName(userPayload.getFullName());
            parent.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
            parentRepository.save(parent);

            user.setParent(parent);
            userRepository.save(user);

            return Result.success(user);
        }catch (Exception e){
            return Result.error(e);
        }
    }

    public Result getAll(){
        return Result.success(userRepository.findAll(Sort.by("createdAt")));
    }

    public Result delete(UUID userId){
        try {
            userRepository.deleteById(userId);
            return Result.message("user deleted",true);
        }catch (Exception e){
            return Result.error(e);
        }
    }

}
