package com.example.tender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.tender.entity.MyFile;
import com.example.tender.payload.Result;
import com.example.tender.service.MyFileService;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600L)
public class MyFileController {
    private final MyFileService myFileService;

    @PostMapping("/auth/file/save")
    public ResponseEntity<?> saveFile(@RequestParam(name = "file") MultipartFile multipartFile) {
        Result result = myFileService.save(multipartFile);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @GetMapping("/admin/file/download/{hashId}")
    public ResponseEntity<?> download(@PathVariable String hashId) throws MalformedURLException {
        MyFile myFile = myFileService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + URLEncoder.encode(myFile.getName()))
                .contentType(MediaType.parseMediaType(myFile.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension())));
    }

    @DeleteMapping("/admin/file/delete/{hashId}")
    public ResponseEntity<?> delete(@PathVariable String hashId) throws MalformedURLException {
        Result result = myFileService.delete(hashId);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @GetMapping("/admin/file/get-all")
    public ResponseEntity<?> getAllHashId(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(myFileService.getHashId(page, size));
    }

    @GetMapping("/auth/file/preview/{hashId}")
    public ResponseEntity<?> preview(@PathVariable String hashId) throws MalformedURLException {
        MyFile myFile = myFileService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.EXPIRES, "inline; fileName=" + URLEncoder.encode(myFile.getName()))
                .contentType(MediaType.parseMediaType(myFile.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s.%s",
                        myFile.getUploadPath(),
                        myFile.getHashId(),
                        myFile.getExtension())));
    }

}
