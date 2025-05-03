package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Data
@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Notification")
    private Integer idNotification;

    @Column(name = "Title", nullable = false, length = 200, unique = true)
    private String title;

    @Column(name = "Id_User", nullable = false)
    private Integer idUser;
    
    @Column(name = "Content", length = 100)
    private String content;

    @Column(name = "Statuss", nullable = false)
    @Convert(converter = NotificationStatusConverter.class)
    private NotificationStatus statuss;

    @Column(name = "CreationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "Tags", length = 200)
    private String tags;

    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    @JsonIgnore
    private Users user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private java.util.List<FilesSave> files;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<NotificationDepartment> notificationDepartments = new ArrayList<>();
 
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<NotificationPosition> notificationPositions = new ArrayList<>();


    public enum NotificationStatus {
        NHAP("Nháp"),
        DA_XUAT_BAN("Đã xuất bản");
        // DA_LUU_TRU("Đã lưu trữ"), 
        // DA_HUY("Đã hủy");         
    
        private final String value;
    
        NotificationStatus(String value) {
            this.value = value;
        }
    
        public String getValue() {
            return value;
        }

        public static NotificationStatus fromValue(String value) {
            for (NotificationStatus status : NotificationStatus.values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown value: " + value);
        }
    }
    
    @Converter
    public static class NotificationStatusConverter implements AttributeConverter<NotificationStatus, String> {
        @Override
        public String convertToDatabaseColumn(NotificationStatus attribute) {
            return attribute == null ? null : attribute.getValue();
        }
    
        @Override
        public NotificationStatus convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null;
            }
            
            for (NotificationStatus status : NotificationStatus.values()) {
                if (status.getValue().equals(dbData)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown database value: " + dbData);
        }
    }
}     
