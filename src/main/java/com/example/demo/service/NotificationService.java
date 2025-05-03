package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.entity.Notification;
import com.example.demo.entity.NotificationDepartment;
import com.example.demo.entity.NotificationPosition;
import com.example.demo.entity.Position;
import com.example.demo.entity.Category;
import com.example.demo.entity.Department;
import com.example.demo.entity.FilesSave;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.FilesSaveRepository;
import com.example.demo.repository.NotificationDepartmentRepository;
import com.example.demo.repository.NotificationPositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FilesSaveRepository filesSaveRepository;

    @Autowired
    private NotificationDepartmentRepository notificationDepartmentRepository;
    @Autowired
    private NotificationPositionRepository notificationPositionRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;

    public Notification createNotification(NotificationDTO notificationDTO, Integer userId) throws IOException {
        Notification notification = new Notification();
        notification.setTitle(notificationDTO.getTitle());
        notification.setContent(notificationDTO.getContent());
        notification.setStatuss(Notification.NotificationStatus.fromValue(notificationDTO.getStatuss()));
        notification.setIdUser(userId);
        Category category = categoryRepository.findByName(notificationDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Không có danh mục"));
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
        notification = notificationRepository.save(notification);
        if (notificationDTO.getDepartmentIds() != null) {
            for (Integer deptId : notificationDTO.getDepartmentIds()) {
                Department dept = departmentRepository.findById(deptId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
                NotificationDepartment nd = new NotificationDepartment();
                nd.setNotification(notification);
                nd.setDepartment(dept);
                notificationDepartmentRepository.save(nd);
            }
        }
    
        if (notificationDTO.getPositionIds() != null) {
            for (Integer posId : notificationDTO.getPositionIds()) {
                Position pos = positionRepository.findById(posId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vị trí"));
                NotificationPosition np = new NotificationPosition();
                np.setNotification(notification);
                np.setPosition(pos);
                notificationPositionRepository.save(np);
            }
        }
        return notification;


    }

    //  Lấy thông báo theo phòng ban/vị trí
    public List<Notification> getNotificationsByDepartment(Integer departmentId) {
        List<NotificationDepartment> nds = notificationDepartmentRepository.findByDepartment_IdPhongBan(departmentId);
        return nds.stream().map(NotificationDepartment::getNotification).collect(Collectors.toList());
    }

 

    public List<Notification> getNotificationsByPosition(Integer positionId) {
        List<NotificationPosition> nps = notificationPositionRepository.findByPosition_Id(positionId);
        return nps.stream().map(NotificationPosition::getNotification).collect(Collectors.toList());
    }


    public List<Notification> getNotificationsByDepartmentAndPosition(Integer departmentId, Integer positionId) {
        List<Notification> byDept = getNotificationsByDepartment(departmentId);
        List<Notification> byPos = getNotificationsByPosition(positionId);
        return byDept.stream().filter(byPos::contains).collect(Collectors.toList());
    }

    //

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


    public List<Notification> getNotificationsByDepartmentName(String nameDepartment) {
        return notificationRepository.findByDepartmentName(nameDepartment);
    }

    public List<Notification> getNotificationsByPositionName(String positionName) {
        return notificationRepository.findByPositionName(positionName);
    }

    public Notification updateNotificationStatus(Integer id, String newStatus) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Kiểm tra trạng thái mới có hợp lệ không
        Notification.NotificationStatus status;
        try {
            status = Notification.NotificationStatus.fromValue(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + newStatus);
        }

        // Cập nhật trạng thái
        notification.setStatuss(status);
        return notificationRepository.save(notification);
    }
}
