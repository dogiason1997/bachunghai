package com.example.demo.controller;

import com.example.demo.entity.FilesSave;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    // API tải lên tệp tin
    @PostMapping("/upload")
    public ResponseEntity<FilesSave> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FilesSave savedFile = fileService.uploadFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // API tải xuống tệp tin theo ID
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFileById(@PathVariable Integer id) {
        Optional<FilesSave> fileOptional = fileService.downloadFileById(id);
        if (fileOptional.isPresent()) {
            FilesSave file = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // API tải xuống tệp tin theo tên
    @GetMapping("/download/name/{fileName}")
    public ResponseEntity<byte[]> downloadFileByName(@PathVariable String fileName) {
        FilesSave file = fileService.downloadFileByName(fileName);
        if (file != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}