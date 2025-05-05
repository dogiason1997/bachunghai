package com.example.demo.service;

import com.example.demo.dto.DocumentDTO;
// import com.example.demo.dto.DocumentResponseDTO;
import com.example.demo.dto.DocumentSearchDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.specification.DocumentSpecification;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
private EntityManager entityManager;

    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    private DocumentFieldRepository documentFieldRepository;
    
    @Autowired
    private IssuingAgencyRepository issuingAgencyRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private FilesSaveRepository filesSaveRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Document createDocument(DocumentDTO dto, Integer userId) throws IOException {
        Document document = new Document();

        document.setIdUser(userId);
        document.setDocumentNumber(dto.getDocumentNumber());
        document.setAbstractText(dto.getAbstractText());
        document.setContent(dto.getContent());
        document.setSigner(dto.getSigner());
        document.setDateOfIssue(dto.getDateOfIssue());
        document.setEffectiveDate(dto.getEffectiveDate());

        // Mapping name -> id với xử lý nhiều kết quả
        // DocumentType
        List<DocumentType> documentTypes = documentTypeRepository.findByNameDocumentType(dto.getNameDocumentType());
        if (!documentTypes.isEmpty()) {
            document.setDocumentType(documentTypes.get(0)); // Lấy kết quả đầu tiên
        } else {
            throw new RuntimeException("DocumentType not found: " + dto.getNameDocumentType());
        }

        // DocumentField
        Optional<DocumentField> documentFieldOpt = documentFieldRepository.findByNameDocumentField(dto.getNameDocumentField());
        if (documentFieldOpt.isPresent()) {
            document.setDocumentField(documentFieldOpt.get());
        } else {
            throw new RuntimeException("DocumentField not found: " + dto.getNameDocumentField());
        }

        // IssuingAgency
        List<IssuingAgency> issuingAgencies = issuingAgencyRepository.findByNameIssuingAgency(dto.getNameIssuingAgency());
        if (!issuingAgencies.isEmpty()) {
            document.setIssuingAgency(issuingAgencies.get(0)); // Lấy kết quả đầu tiên
        } else {
            throw new RuntimeException("IssuingAgency not found: " + dto.getNameIssuingAgency());
        }

        // Category
        Optional<Category> categoryOpt = categoryRepository.findByName(dto.getCategoryName());
        if (categoryOpt.isPresent()) {
            document.setCategory(categoryOpt.get());
        } else {
            throw new RuntimeException("Category not found: " + dto.getCategoryName());
        }

        // Lưu Document trước để lấy id
        Document savedDocument = documentRepository.save(document);

        // Xử lý lưu file
        List<FilesSave> filesSaveList = new ArrayList<>();
        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            for (MultipartFile file : dto.getFiles()) {
                FilesSave filesSave = new FilesSave();
                filesSave.setFileName(file.getOriginalFilename());
                filesSave.setData(file.getBytes());
                filesSave.setDocument(savedDocument);
                filesSaveList.add(filesSaveRepository.save(filesSave));
            }
        }
        
        savedDocument.setFilesSave(filesSaveList);
        return savedDocument;
    }

    public Document getDocument(Integer id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Transactional
    public Document updateDocument(Integer id, DocumentDTO dto, Integer userId) throws IOException {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));

        document.setIdUser(userId);
        document.setDocumentNumber(dto.getDocumentNumber());
        document.setAbstractText(dto.getAbstractText());
        document.setContent(dto.getContent());
        document.setSigner(dto.getSigner());
        document.setDateOfIssue(dto.getDateOfIssue());
        document.setEffectiveDate(dto.getEffectiveDate());

        // Mapping name -> id với xử lý nhiều kết quả
        // DocumentType
        List<DocumentType> documentTypes = documentTypeRepository.findByNameDocumentType(dto.getNameDocumentType());
        if (!documentTypes.isEmpty()) {
            document.setDocumentType(documentTypes.get(0)); // Lấy kết quả đầu tiên
        } else {
            throw new RuntimeException("DocumentType not found: " + dto.getNameDocumentType());
        }

        // DocumentField
        Optional<DocumentField> documentFieldOpt = documentFieldRepository.findByNameDocumentField(dto.getNameDocumentField());
        if (documentFieldOpt.isPresent()) {
            document.setDocumentField(documentFieldOpt.get());
        } else {
            throw new RuntimeException("DocumentField not found: " + dto.getNameDocumentField());
        }

        // IssuingAgency
        List<IssuingAgency> issuingAgencies = issuingAgencyRepository.findByNameIssuingAgency(dto.getNameIssuingAgency());
        if (!issuingAgencies.isEmpty()) {
            document.setIssuingAgency(issuingAgencies.get(0)); // Lấy kết quả đầu tiên
        } else {
            throw new RuntimeException("IssuingAgency not found: " + dto.getNameIssuingAgency());
        }

        // Category
        Optional<Category> categoryOpt = categoryRepository.findByName(dto.getCategoryName());
        if (categoryOpt.isPresent()) {
            document.setCategory(categoryOpt.get());
        } else {
            throw new RuntimeException("Category not found: " + dto.getCategoryName());
        }

        // Cập nhật file nếu có
        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            // Xóa file cũ
            List<FilesSave> oldFiles = document.getFilesSave();
            if (oldFiles != null) {
                for (FilesSave file : oldFiles) {
                    // filesSaveRepository.delete(file);
                    FilesSave managedFile = entityManager.getReference(FilesSave.class, file.getId());
                    entityManager.remove(managedFile);
                }
            }

            // Thêm file mới
            List<FilesSave> filesSaveList = new ArrayList<>();
            for (MultipartFile file : dto.getFiles()) {
                FilesSave filesSave = new FilesSave();
                filesSave.setFileName(file.getOriginalFilename());
                filesSave.setData(file.getBytes());
                filesSave.setDocument(document);
                filesSaveList.add(filesSaveRepository.save(filesSave));
            }
            document.setFilesSave(filesSaveList);
        }

        return documentRepository.save(document);
    }

    public void deleteDocument(Integer id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
        
        // Xóa các files liên quan
        List<FilesSave> files = document.getFilesSave();
        if (files != null) {
            for (FilesSave file : files) {
                filesSaveRepository.delete(file);
            }
        }
        
        documentRepository.delete(document);
    }
    
    public List<Document> searchDocuments(DocumentSearchDTO searchDTO) {
        Specification<Document> spec = DocumentSpecification.searchByCriteria(searchDTO);
        return documentRepository.findAll(spec);
    }
} 