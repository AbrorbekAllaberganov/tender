package com.example.tender.service;

import com.example.tender.entity.enums.Interest;
import com.example.tender.entity.enums.UserStatus;
import com.example.tender.entity.users.Parent;
import com.example.tender.entity.users.User;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.mapper.user.UserMapper;
import com.example.tender.payload.detail.FileForResponse;
import com.example.tender.payload.request.user.UserInterestFilterPayload;
import com.example.tender.payload.request.user.UserPayload;
import com.example.tender.payload.response.Result;
import com.example.tender.payload.response.UserFilterResponse;
import com.example.tender.repository.ParentRepository;
import com.example.tender.repository.RoleRepository;
import com.example.tender.repository.UserRepository;
import com.example.tender.repository.filter.UserFilterRepository;
import com.example.tender.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFilterRepository userFilterRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ParentRepository parentRepository;
    private final MyFileService myFileService;

    private final SecurityUtils securityUtils;

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
            myFileService.findById(userPayload.getPhotoId());

            User user = new User();

            Parent parent = new Parent();
            parent.setPassword(passwordEncoder.encode(userPayload.getPassword()));
            parent.setPhoneNumber(userPayload.getPhoneNumber());
            parent.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
            parentRepository.save(parent);
            user.setLang(userPayload.getLanguage());
            user.setPhotoId(userPayload.getPhotoId());
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
            user.setStatus(UserStatus.ONLINE);
            user.setLat(userPayload.getLat());
            user.setLon(userPayload.getLon());
            user.setBirthDay(userPayload.getBirthDay());
            userRepository.save(user);

            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result editUser(String userId, UserPayload userPayload) {
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

    public Result getAll() {
        return Result.success(userRepository.findAll(Sort.by("createdAt")));
    }

    public Result delete(String userId) {
        try {
            userRepository.deleteById(userId);
            return Result.message("user deleted", true);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result changeLocation(String userId, long lon, long lat) {
        try {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFound("user", "id", userId)
            );
            user.setLon(lon);
            user.setLat(lat);
            userRepository.save(user);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result changeStatus(UserStatus status) {
        try {
            Optional<String> currentUser = securityUtils.getCurrentUser();

            if (!currentUser.isPresent())
                throw new BadRequest("User not found!");

            Parent user = parentRepository.findByPhoneNumber(currentUser.get());

            switch (status) {
                case OFFLINE:
                    userRepository.updateStatus(user.getId(), status, LocalDateTime.now());
                case ONLINE:
                    userRepository.updateStatus(user.getId(), status);
            }
            return Result.success(status);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public User findByPhone(String phone) {
        return userRepository.findByParent_PhoneNumber(phone).orElseThrow(
                () -> new BadRequest("User not found")
        );
    }

    public PageImpl<UserFilterResponse> findInterests(UserInterestFilterPayload payload, int size, int page) {

        Optional<String> currentUser = securityUtils.getCurrentUser();

        if (!currentUser.isPresent())
            throw new BadRequest("User not found!");

        User user = findByPhone(currentUser.get());

        List<String> interests = user.getInterests()
                .stream()
                .map(Interest::name)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);

        UserMapper filter = userFilterRepository
                .filter(payload, page, size,
                        user.getLat(), user.getLon(), interests);

        List<UserFilterResponse> resList = filter.getList()
                .stream()
                .map(this::toResponseFilter)
                .collect(Collectors.toList());

        return new PageImpl<>(resList, pageable, filter.getCount());
    }

    private UserFilterResponse toResponseFilter(User user) {
        UserFilterResponse response = new UserFilterResponse();
        response.setAge(new Date().getYear() - user.getBirthDay().getYear());
        response.setId(user.getId());
        response.setPhoto(new FileForResponse(user.getPhotoId(), myFileService.toOpenUrl(user.getPhoto())));
        response.setLastName(user.getLastName());
        response.setFirstName(user.getFirstName());
        return response;
    }
}
