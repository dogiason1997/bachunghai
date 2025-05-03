package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.NotificationPosition;

@Repository
public interface NotificationPositionRepository extends JpaRepository<NotificationPosition, Integer> {
    List<NotificationPosition> findByPosition_Id(Integer positionId);
    List<NotificationPosition> findByNotification_IdNotification(Integer notificationId);
}
