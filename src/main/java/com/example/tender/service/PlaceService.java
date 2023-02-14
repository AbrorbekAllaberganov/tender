package com.example.tender.service;

import com.example.tender.entity.PlaceEntity;
import com.example.tender.entity.enums.Language;
import com.example.tender.entity.enums.UserStatus;
import com.example.tender.entity.users.Parent;
import com.example.tender.entity.users.User;
import com.example.tender.payload.request.PlaceRequestPayload;
import com.example.tender.payload.response.PlacesResponse;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 'Mukhtarov Sarvarbek' on 06.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Result save(PlaceRequestPayload payload) {
        try {
            PlaceEntity entity = new PlaceEntity();
            entity.setType(payload.getType());
            entity.setVisible(true);
            entity.setNameRu(payload.getNameRu());
            entity.setType(payload.getType());
            entity.setNameEn(payload.getNameEn());
            entity.setNameUz(payload.getNameUz());
            placeRepository.save(entity);
            return Result.success("");
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result delete(final String id) {
        try {
            placeRepository.updateVisible(id, false);
            return Result.message("Success!", true);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result edit(String placeId, PlaceRequestPayload payload) {
        try {
            Optional<PlaceEntity> place = placeRepository.findById(placeId);
            if (!place.isPresent()) {
                return Result.message("Place not found!", false);
            }
            PlaceEntity entity = place.get();
            entity.setType(payload.getType());
            entity.setNameRu(payload.getNameRu());
            entity.setType(payload.getType());
            entity.setNameEn(payload.getNameEn());
            entity.setNameUz(payload.getNameUz());
            entity.setVisible(true);
            placeRepository.save(entity);
            return Result.success("");
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    public Result findAll(Language language) {
        return Result.success(placeRepository
                .findAll()
                .stream()
                .map(e -> toDTO(e, language))
                .collect(Collectors.toList()));
    }

    public PlacesResponse toDTO(PlaceEntity entity, Language lang) {
        String name = null;
        switch (lang) {
            case uz:
                name = entity.getNameUz();
            case en:
                name = entity.getNameEn();
            case ru:
                name = entity.getNameRu();
        }
        return new PlacesResponse(entity.getId(), name);
    }
}
