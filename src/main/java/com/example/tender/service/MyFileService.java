package com.example.tender.service;

import com.example.tender.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.tender.entity.MyFile;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.MyFileRepository;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyFileService {

    private final MyFileRepository repository;

    @Value("${upload}")
    private String downloadPath;

    @Value("${server.name}")
    private String domainName;


    public Result save(MultipartFile multipartFile) {

        MyFile myFile = new MyFile();

        myFile.setContentType(multipartFile.getContentType());
        myFile.setFileSize(multipartFile.getSize());
        myFile.setName(multipartFile.getOriginalFilename());
        myFile.setExtension(getExtension(myFile.getName()).toLowerCase());
        myFile.setHashId(UUID.randomUUID().toString());


        LocalDate date = LocalDate.now();

        // change value downloadPath
        String localPath = downloadPath + String.format(
                "/%d/%d/%d/%s",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                myFile.getExtension().toLowerCase());

        myFile.setUploadPath(localPath);


        // downloadPath / year / month / day / extension
        File file = new File(localPath);

        // " downloadPath / year / month / day / extension "   crate directory
        file.mkdirs();

        // save MyFile into base
        myFile.setLink(file.getAbsolutePath() + "/" + String.format("%s.%s", myFile.getHashId(), myFile.getExtension()));

        repository.save(myFile);

        try {
            // copy bytes into new file or saving into storage
            multipartFile.transferTo(new File(file.getAbsolutePath() + "/" + String.format("%s.%s", myFile.getHashId(), myFile.getExtension())));
            Map<Object, Object> data = new HashMap<>();
            data.put("id", myFile.getId());
            return new Result("File successfully saved!", true, data);

        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequest("File not saved!");
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public MyFile findByHashId(String hashId) {
        return repository.findByHashId(hashId)
                .orElseThrow(() -> new ResourceNotFound("File", "hashId", hashId));
    }

    public MyFile findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("File", "id", id));
    }

    public Result delete(String hashId) {
        MyFile myFile = findByHashId(hashId);

        File file = new File(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension()));

        if (file.delete() && repository.deleteByHashId(hashId)) {
            return new Result("success", true);

        } else {
            return new Result("error", false);
        }

    }

    public Page<String> getHashId(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.getHashId(pageable);
    }

    public String toOpenUrl(String id) {
        return domainName.concat("auth/file/preview/").concat(findById(id).getHashId());
    }

    public String toOpenUrl(@NotNull MyFile file) {
        return domainName.concat("auth/file/preview/").concat(file.getHashId());
    }
}
