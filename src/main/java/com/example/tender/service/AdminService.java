package com.example.tender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.tender.entity.users.Admin;
import com.example.tender.entity.users.Parent;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.payload.request.AdminRequest;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.AdminRepository;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Result editAdmin(String adminId, AdminRequest adminRequest) {
        try {
            Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new ResourceNotFound("admin", "id", adminId));
            Parent parent = admin.getParent();
            parent.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
            parent.setPhoneNumber(adminRequest.getPhoneNumber());

            admin.setParent(parent);
            adminRepository.save(admin);
            return Result.success(admin);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

}
