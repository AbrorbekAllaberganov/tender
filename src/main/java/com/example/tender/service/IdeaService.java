package com.example.tender.service;

import com.example.tender.entity.IdeaEntity;
import com.example.tender.entity.enums.IdeaStatus;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.payload.request.IdeaRequestPayload;
import com.example.tender.payload.request.user.UserPayload;
import com.example.tender.payload.response.IdeaResponse;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 'Mukhtarov Sarvarbek' on 18.02.2023
 * @project tender
 * @contact @sarvargo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IdeaService {

    private final IdeaRepository ideaRepository;

    public Result save(IdeaRequestPayload ideaPayload) {
        try {
            IdeaEntity idea = new IdeaEntity();
            idea.setTitle(ideaPayload.getTitle());
            idea.setStatus(ideaPayload.getStatus());
            idea.setVisible(Boolean.TRUE);

            ideaRepository.save(idea);

            return Result.success(toResponse(idea));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public IdeaEntity findById(String id) {
        return ideaRepository.findById(id).orElseThrow(() -> new BadRequest("Idea not found!"));
    }

    public Result update(IdeaRequestPayload ideaPayload, String id) {
        try {
            IdeaEntity idea = findById(id);
            idea.setTitle(ideaPayload.getTitle());
            idea.setStatus(ideaPayload.getStatus());
            idea.setVisible(Boolean.TRUE);

            ideaRepository.save(idea);

            return Result.success(toResponse(idea));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public Result delete(String id) {
        try {
            ideaRepository.updateVisible(false, id);
            return Result.success(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public PageImpl<IdeaResponse> findByAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "created_at"));

        Page<IdeaEntity> entityPage = ideaRepository
                .findAllByVisibleIsTrue(pageable);

        List<IdeaResponse> list = entityPage.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, entityPage.getTotalPages());
    }

    public IdeaResponse toResponse(IdeaEntity entity) {
        return new IdeaResponse(entity.getId(), entity.getTitle(), entity.getStatus());
    }

}
