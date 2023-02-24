package com.example.tender.controller;

import com.example.tender.payload.request.FeaturePayload;
import com.example.tender.payload.response.Result;
import com.example.tender.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureService featureService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveFature(@RequestBody FeaturePayload featurePayload){
        Result result=featureService.saveFeature(featurePayload);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editFature(@PathVariable("id") String id,
                                             @RequestBody FeaturePayload featurePayload){
        Result result=featureService.editFeature(id,featurePayload);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteFature(@PathVariable("id") String id){
        Result result=featureService.deleteFeature(id);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Result> getAll(){
        Result result=featureService.getAll();
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }



}
