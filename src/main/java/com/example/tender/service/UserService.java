package com.example.tender.service;

import com.example.tender.entity.Interest;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.payload.UserPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.tender.entity.users.Parent;
import com.example.tender.entity.users.User;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.payload.Result;
import com.example.tender.repository.ParentRepository;
import com.example.tender.repository.RoleRepository;
import com.example.tender.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ParentRepository parentRepository;

    public Interest getInterest(String interest) {
        switch (interest) {
            case "Education":
                return Interest.Education;
            case "Language":
                return Interest.Language;
            case "Debates":
                return Interest.Debates;
            case "Shopping":
                return Interest.Shopping;
            case "Photography":
                return Interest.Photography;
            case "Cooking":
                return Interest.Cooking;
            case "Sports":
                return Interest.Sports;
            case "Swimming":
                return Interest.Swimming;
            case "Art":
                return Interest.Art;
            case "Traveling":
                return Interest.Traveling;
            case "Extreme":
                return Interest.Extreme;
            case "Music":
                return Interest.Music;
            case "Night_life":
                return Interest.Night_life;
            case "Video_games":
                return Interest.Video_games;
            case "Literature":
                return Interest.Literature;
            case "Cars":
                return Interest.Cars;
            default:
                throw new BadRequest("Interest not found");
        }

    }

    public Result saveUser(UserPayload userPayload) {
        try {
            User user = new User();

            Parent parent = new Parent();
            parent.setPassword(passwordEncoder.encode(userPayload.getPassword()));
            parent.setPhoneNumber(userPayload.getPhoneNumber());
            parent.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
            parentRepository.save(parent);

            user.setParent(parent);
            user.setFirstName(userPayload.getFirstName());
            user.setLastName(userPayload.getLastName());
            user.setAboutMe(userPayload.getAboutMe());
            user.setPhoneNumber(userPayload.getPhoneNumber());
            user.setGender(userPayload.isGender());
            user.setInterests(
                    userPayload.getInterests().stream()
                            .map(this::getInterest)
                            .collect(Collectors.toList())

            );
            user.setLat(userPayload.getLat());
            user.setLon(userPayload.getLon());

            userRepository.save(user);

            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result editUser(UUID userId, UserPayload userPayload) {
        try {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFound("user", "id", userId));

            Parent parent = user.getParent();
            parent.setPassword(userPayload.getPassword());
            parent.setPhoneNumber(userPayload.getPhoneNumber());
            parent.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
            parentRepository.save(parent);

            user.setParent(parent);
            user.setFirstName(userPayload.getFirstName());
            user.setLastName(userPayload.getLastName());
            user.setAboutMe(userPayload.getAboutMe());
            user.setPhoneNumber(userPayload.getPhoneNumber());
            user.setGender(userPayload.isGender());
            user.setInterests(
                    userPayload.getInterests().stream()
                            .map(this::getInterest)
                            .collect(Collectors.toList())

            );

            userRepository.save(user);

            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result deleteUser(UUID userId){
        try {
            userRepository.deleteById(userId);
            return Result.message("user deleted",true);
        }catch (Exception e){
            return Result.error(e);
        }
    }

    public Result getAll() {
        return Result.success(userRepository.findAll(Sort.by("createdAt")));
    }

    public Result delete(UUID userId) {
        try {
            userRepository.deleteById(userId);
            return Result.message("user deleted", true);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result changeLocation(UUID userId,long lon,long lat){
        try {
            User user=userRepository.findById(userId).orElseThrow(
                    ()->new ResourceNotFound("user","id",userId)
            );
            user.setLon(lon);
            user.setLat(lat);
            userRepository.save(user);
            return Result.success(user);
        }catch (Exception e){
            return Result.error(e);
        }
    }

}
