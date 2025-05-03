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

  

    // Lấy danh sách thông báo theo tên phòng ban
    // @GetMapping("/department/{nameDepartment}")
    // public List<Notification> getNotificationsByDepartment(@PathVariable String nameDepartment) {
    //     return notificationService.getNotificationsByDepartmentName(nameDepartment);
    // }

    // // Lấy danh sách thông báo theo tên chức vụ
    // @GetMapping("/position/{positionName}")
    // public List<Notification> getNotificationsByPosition(@PathVariable String positionName) {
    //     return notificationService.getNotificationsByPositionName(positionName);
    // }
    
    @GetMapping("/department/{departmentId}/position/{positionId}")
    public List<Notification> getByDepartmentAndPosition(@PathVariable Integer departmentId, @PathVariable Integer positionId) {
        return notificationService.getNotificationsByDepartmentAndPosition(departmentId, positionId);
    }

    @GetMapping("/department/{departmentId}")
    public List<Notification> getByDepartment(@PathVariable Integer departmentId) {
        return notificationService.getNotificationsByDepartment(departmentId);
    }

    @GetMapping("/position/{positionId}")
    public List<Notification> getByPosition(@PathVariable Integer positionId) {
        return notificationService.getNotificationsByPosition(positionId);
    }

}