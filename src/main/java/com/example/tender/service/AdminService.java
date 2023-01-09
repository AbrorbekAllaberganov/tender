package com.example.tender.service;

import uz.abror.myproject.entity.users.Admin;
import uz.abror.myproject.entity.users.Parent;
import uz.abror.myproject.exceptions.ResourceNotFound;
import uz.abror.myproject.payload.AdminRequest;
import uz.abror.myproject.payload.Result;
import uz.abror.myproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Result editAdmin(UUID adminId, AdminRequest adminRequest) {
        try {
            Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new ResourceNotFound("admin", "id", adminId));
            Parent parent = admin.getParent();
            parent.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
            parent.setFullName(adminRequest.getFullname());
            parent.setUserName(adminRequest.getUsername());
            parent.setPhoneNumber(adminRequest.getPhoneNumber());

            admin.setParent(parent);
            adminRepository.save(admin);
            return Result.success(admin);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

}
