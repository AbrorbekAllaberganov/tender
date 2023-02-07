package com.example.tender.controller;

import com.example.tender.entity.enums.Language;
import com.example.tender.payload.request.PlaceRequestPayload;
import com.example.tender.payload.response.Result;
import com.example.tender.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 'Mukhtarov Sarvarbek' on 06.02.2023
 * @project tender
 * @contact @sarvargo
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/place")
public class PlacesController {

    private final PlaceService placeService;

    @PostMapping("")
    public ResponseEntity<Result> save(@RequestBody PlaceRequestPayload payload) {
        log.info("Save place body = {}", payload);
        return ResponseEntity.ok(placeService.save(payload));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> edit(@PathVariable() String id,
                                       @RequestBody PlaceRequestPayload payload) {
        log.info("Save place body = {}", payload);
        return ResponseEntity.ok(placeService.edit(id, payload));
    }

    @GetMapping("")
    public ResponseEntity<Result> findAll(@RequestParam("lang") Language language) {
        log.info("Get all place ");
        return ResponseEntity.ok(placeService.findAll(language));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable String id) {
        log.info("Delete place id = {}", id);
        return ResponseEntity.ok(placeService.delete(id));
    }
}
