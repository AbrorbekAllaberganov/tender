package com.example.tender.loader;

import uz.abror.myproject.entity.users.Admin;
import uz.abror.myproject.entity.users.Parent;
import uz.abror.myproject.entity.users.Role;
import uz.abror.myproject.repository.AdminRepository;
import uz.abror.myproject.repository.ParentRepository;
import uz.abror.myproject.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
        @Value("${spring.jpa.hibernate.ddl-auto}")
        private String init;

    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final ParentRepository parentRepository;


    @Override
    public void run(String... args) throws Exception {


        try {
            if (init.equalsIgnoreCase("create")) {
                Role roleUser = new Role();
                roleUser.setId(1L);
                roleUser.setName("ROLE_USER");

                Role roleAdmin = new Role();
                roleAdmin.setId(2L);
                roleAdmin.setName("ROLE_ADMIN");

                List<Role> roleList = new ArrayList<>(Arrays.asList(roleUser, roleAdmin));
                roleRepository.saveAll(roleList);

                Admin admin = new Admin();
                Parent parent = new Parent();
                parent.setRoles(new ArrayList<>(Collections.singletonList(roleRepository.findByName("ROLE_ADMIN"))));
                parent.setUserName("admin");
                parent.setPassword(passwordEncoder.encode("111"));
                parent.setFullName("Abror Allaberganov");
                parent.setPhoneNumber("+998977777777");
                parentRepository.save(parent);
                admin.setParent(parent);

                adminRepository.save(admin);
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
