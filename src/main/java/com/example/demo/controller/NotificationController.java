package com.example.demo.controller;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.entity.Notification;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin("*")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createNotification(
            @ModelAttribute NotificationDTO notificationDTO,
            @RequestParam(name = "userId", required = true) Integer userId) throws Exception {
        return ResponseEntity.ok(notificationService.createNotification(notificationDTO, userId));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateNotification(
            @PathVariable Integer id,
            @ModelAttribute NotificationDTO notificationDTO,
            @RequestParam(name = "userId", required = true) Integer userId) throws Exception {
        return ResponseEntity.ok(notificationService.updateNotification(id, notificationDTO, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Xóa thông báo thành công");
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }
}