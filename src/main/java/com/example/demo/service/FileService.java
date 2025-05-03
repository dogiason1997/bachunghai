package com.example.demo.service;

import com.example.demo.entity.FilesSave;
import com.example.demo.repository.FilesSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FilesSaveRepository filesSaveRepository;

    // Tải lên tệp tin
    public FilesSave uploadFile(MultipartFile file) throws IOException {
        FilesSave filesSave = new FilesSave();
        filesSave.setFileName(file.getOriginalFilename());
        filesSave.setData(file.getBytes());
        return filesSaveRepository.save(filesSave);
    }

    // Tải xuống tệp tin theo ID
    public Optional<FilesSave> downloadFileById(Integer id) {
        return filesSaveRepository.findById(id);
    }

    // Tải xuống tệp tin theo tên
    public FilesSave downloadFileByName(String fileName) {
        return filesSaveRepository.findByFileName(fileName);
    }
}