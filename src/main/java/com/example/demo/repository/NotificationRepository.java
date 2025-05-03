package com.example.demo.repository;

import com.example.demo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n JOIN n.user u JOIN u.department d WHERE d.nameDepartment = :nameDepartment")
    List<Notification> findByDepartmentName(@Param("nameDepartment") String nameDepartment);

    @Query("SELECT n FROM Notification n JOIN n.user u JOIN u.position p WHERE p.positionName = :positionName")
    List<Notification> findByPositionName(@Param("positionName") String positionName);
}