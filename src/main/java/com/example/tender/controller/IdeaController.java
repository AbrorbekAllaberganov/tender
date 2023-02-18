package com.example.tender.controller;

import com.example.tender.payload.request.IdeaRequestPayload;
import com.example.tender.payload.response.IdeaResponse;
import com.example.tender.payload.response.Result;
import com.example.tender.service.IdeaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 'Mukhtarov Sarvarbek' on 18.02.2023
 * @project tender
 * @contact @sarvargo
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/idea")
public class IdeaController {

    private final IdeaService ideaService;

    @PostMapping("")
    public ResponseEntity<Result> save(@RequestBody IdeaRequestPayload payload) {
        log.info("Save idea = {}", payload);
        return ResponseEntity.ok(ideaService.save(payload));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> save(@PathVariable("id") String id, @RequestBody IdeaRequestPayload payload) {
        log.info("Edit id = {} idea = {}", id, payload);
        return ResponseEntity.ok(ideaService.update(payload, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable("id") String id) {
        log.info("Delete id = {}", id);
        return ResponseEntity.ok(ideaService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<PageImpl<IdeaResponse>> delete(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "15") int size) {
        log.info("Find all page = {}, size = {}", page, size);
        return ResponseEntity.ok(ideaService.findByAll(page, size));
    }

}
