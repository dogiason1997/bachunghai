package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.NotificationDepartment;

@Repository
public interface NotificationDepartmentRepository extends JpaRepository<NotificationDepartment, Integer> {
    List<NotificationDepartment> findByDepartment_IdPhongBan(Integer departmentId);
    List<NotificationDepartment> findByNotification_IdNotification(Integer notificationId);
}
