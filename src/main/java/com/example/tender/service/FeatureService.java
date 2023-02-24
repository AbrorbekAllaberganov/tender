package com.example.tender.service;

import com.example.tender.entity.Feature;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.payload.request.FeaturePayload;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureService {
    private final FeatureRepository featureRepository;

    public Result saveFeature(FeaturePayload featurePayload){
        try {
            Feature feature=new Feature();
            feature.setText(featurePayload.getText());
            feature.setVersion(featurePayload.getVersion());

            featureRepository.save(feature);
            return Result.success(feature);
        }catch (Exception e){
            return Result.error(e);
        }
    }


    public Result editFeature(String id,FeaturePayload featurePayload){
        try {
            Feature feature=featureRepository.findById(id).orElseThrow(
                    ()->new ResourceNotFound("feature","id",id)
            );
            feature.setText(featurePayload.getText());
            feature.setVersion(featurePayload.getVersion());

            featureRepository.save(feature);
            return Result.success(feature);
        }catch (Exception e){
            return Result.error(e);
        }
    }

    public Result getAll(){
        return Result.success(featureRepository.findAll(Sort.by("createdAt")));
    }

    public Result deleteFeature(String id){
        try {
            featureRepository.deleteById(id);
            return Result.message("feature deleted",true);
        }catch (Exception e){
            return Result.error(e);
        }
    }

}
