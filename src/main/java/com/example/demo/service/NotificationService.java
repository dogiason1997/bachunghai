package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.entity.Notification;
import com.example.demo.entity.Category;
import com.example.demo.entity.FilesSave;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FilesSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FilesSaveRepository filesSaveRepository;

    public Notification createNotification(NotificationDTO notificationDTO, Integer userId) throws IOException {
        Notification notification = new Notification();
        notification.setTitle(notificationDTO.getTitle());
        notification.setContent(notificationDTO.getContent());
        notification.setStatuss(Notification.NotificationStatus.valueOf(notificationDTO.getStatuss()));
        notification.setIdUser(userId);
        Category category = categoryRepository.findByName(notificationDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        notification.setCategory(category);
        notification.setCreationDate(java.time.LocalDateTime.now());
        notification.setTags("");
        notification = notificationRepository.save(notification);

        List<FilesSave> files = new ArrayList<>();
        if (notificationDTO.getFiles() != null) {
            for (MultipartFile file : notificationDTO.getFiles()) {
                if (!file.isEmpty()) {
                    FilesSave filesSave = new FilesSave();
                    filesSave.setData(file.getBytes());
                    filesSave.setNotification(notification);
                    filesSave.setFileName(file.getOriginalFilename());
                    files.add(filesSaveRepository.save(filesSave));
                }
            }
        }
        notification.setFiles(files);
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Integer id, NotificationDTO notificationDTO, Integer userId) throws IOException {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setTitle(notificationDTO.getTitle());
        notification.setContent(notificationDTO.getContent());
        notification.setStatuss(Notification.NotificationStatus.valueOf(notificationDTO.getStatuss()));
        notification.setIdUser(userId);
        Category category = categoryRepository.findByName(notificationDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        notification.setCategory(category);

        // Xóa file cũ nếu muốn, hoặc giữ lại nếu cần
        filesSaveRepository.deleteAll(notification.getFiles());
        List<FilesSave> files = new ArrayList<>();
        if (notificationDTO.getFiles() != null) {
            for (MultipartFile file : notificationDTO.getFiles()) {
                if (!file.isEmpty()) {
                    FilesSave filesSave = new FilesSave();
                    filesSave.setData(file.getBytes());
                    filesSave.setNotification(notification);
                    filesSave.setFileName(file.getOriginalFilename());
                    files.add(filesSaveRepository.save(filesSave));
                }
            }
        }
        notification.setFiles(files);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Integer id) {
        notificationRepository.deleteById(id);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Integer id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
}
