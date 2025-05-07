package com.example.demo.service;

import com.example.demo.dto.LetterDTO;
import com.example.demo.dto.LetterSearchDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Letter;
import com.example.demo.entity.Letter.DispatchPriority;
import com.example.demo.entity.Letter.LetterSercurity;
import com.example.demo.entity.Letter.LetterType;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.LetterRepository;
import com.example.demo.specification.LetterSpecification;
import com.example.demo.repository.FilesSaveRepository;
import com.example.demo.entity.FilesSave;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LetterService {

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FilesSaveRepository filesSaveRepository;

    public Page<Letter> getAllLetters(Pageable pageable) {
        return letterRepository.findAll(pageable);
    }

    public Letter getLetterById(Integer id) {
        return letterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Letter not found with id: " + id));
    }

    public Letter createLetter(LetterDTO letterDTO, Integer userId) {
        Letter letter = convertToEntity(letterDTO);
        letter.setIdUser(userId);
        letter.setCreationDate(LocalDateTime.now());
        letter = letterRepository.save(letter);

        // Xử lý lưu file
        if (letterDTO.getFiles() != null) {
            List<FilesSave> files = new java.util.ArrayList<>();
            for (MultipartFile file : letterDTO.getFiles()) {
                if (file != null && !file.isEmpty()) {
                    try {
                        FilesSave filesSave = new FilesSave();
                        filesSave.setData(file.getBytes());
                        filesSave.setLetter(letter);
                        filesSave.setFileName(file.getOriginalFilename());
                        files.add(filesSaveRepository.save(filesSave));
                    } catch (Exception e) {
                        // Có thể log lỗi hoặc throw runtime exception
                        throw new RuntimeException("Lỗi khi lưu file: " + file.getOriginalFilename(), e);
                    }
                }
            }
            letter.setFiles(files);
            letter = letterRepository.save(letter);
        }
        return letter;
    }

    public Letter updateLetter(Integer id, LetterDTO letterDTO) {
        Letter existingLetter = getLetterById(id);
        
        // Update fields from DTO
        existingLetter.setLetterCode(letterDTO.getLetterCode());
        existingLetter.setLetterTitle(letterDTO.getLetterTitle());
        existingLetter.setPlaceSending(letterDTO.getPlaceSending());
        existingLetter.setSignerLetter(letterDTO.getSignerLetter());
        existingLetter.setInfomartionUserLetter(letterDTO.getInfomartionUserLetter());
        existingLetter.setDateIssue(letterDTO.getDateIssue());
        existingLetter.setDaySigner(letterDTO.getDaySigner());
        existingLetter.setDateEffective(letterDTO.getDateEffective());
        existingLetter.setAbstracts(letterDTO.getAbstracts());
        existingLetter.setDeadline(letterDTO.getDeadline());
        
        // Set enums
        if (letterDTO.getPrioritize() != null) {
            existingLetter.setPrioritize(DispatchPriority.fromValue(letterDTO.getPrioritize()));
        }
        if (letterDTO.getLetterType() != null) {
            existingLetter.setLetterType(LetterType.fromValue(letterDTO.getLetterType()));
        }
        if (letterDTO.getLetterSercurity() != null) {
            existingLetter.setLetterSercurity(LetterSercurity.fromValue(letterDTO.getLetterSercurity()));
        }
        
        // Set category
        if (letterDTO.getCategoryName() != null) {
            Category category = categoryRepository.findByName(letterDTO.getCategoryName())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + letterDTO.getCategoryName()));
            existingLetter.setCategory(category);
        }
        
        // Xóa file cũ nếu có file mới upload
        if (letterDTO.getFiles() != null && letterDTO.getFiles().length > 0) {
            if (existingLetter.getFiles() != null) {
                filesSaveRepository.deleteAll(existingLetter.getFiles());
            }
            List<FilesSave> files = new java.util.ArrayList<>();
            for (MultipartFile file : letterDTO.getFiles()) {
                if (file != null && !file.isEmpty()) {
                    try {
                        FilesSave filesSave = new FilesSave();
                        filesSave.setData(file.getBytes());
                        filesSave.setLetter(existingLetter);
                        filesSave.setFileName(file.getOriginalFilename());
                        files.add(filesSaveRepository.save(filesSave));
                    } catch (Exception e) {
                        throw new RuntimeException("Lỗi khi lưu file: " + file.getOriginalFilename(), e);
                    }
                }
            }
            existingLetter.setFiles(files);
        }
        return letterRepository.save(existingLetter);
    }

    public void deleteLetter(Integer id) {
        if (!letterRepository.existsById(id)) {
            throw new EntityNotFoundException("Letter not found with id: " + id);
        }
        letterRepository.deleteById(id);
    }

    private Letter convertToEntity(LetterDTO letterDTO) {
        Letter letter = new Letter();
        letter.setLetterCode(letterDTO.getLetterCode());
        letter.setLetterTitle(letterDTO.getLetterTitle());
        letter.setPlaceSending(letterDTO.getPlaceSending());
        letter.setSignerLetter(letterDTO.getSignerLetter());
        letter.setInfomartionUserLetter(letterDTO.getInfomartionUserLetter());
        letter.setDateIssue(letterDTO.getDateIssue());
        letter.setDaySigner(letterDTO.getDaySigner());
        letter.setDateEffective(letterDTO.getDateEffective());
        letter.setAbstracts(letterDTO.getAbstracts());
        letter.setDeadline(letterDTO.getDeadline());
        letter.setCreationDate(LocalDateTime.now());
        
        // Set enums
        if (letterDTO.getPrioritize() != null) {
            letter.setPrioritize(DispatchPriority.fromValue(letterDTO.getPrioritize()));
        }
        if (letterDTO.getLetterType() != null) {
            letter.setLetterType(LetterType.fromValue(letterDTO.getLetterType()));
        }
        if (letterDTO.getLetterSercurity() != null) {
            letter.setLetterSercurity(LetterSercurity.fromValue(letterDTO.getLetterSercurity()));
        }
        
        // Set category
        if (letterDTO.getCategoryName() != null) {
            Category category = categoryRepository.findByName(letterDTO.getCategoryName())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + letterDTO.getCategoryName()));
            letter.setCategory(category);
        }
        
        return letter;



    }
    
    
    public List<Letter> searchLetters(LetterSearchDTO dto) {
        Specification<Letter> spec = LetterSpecification.filterBy(dto);
        return letterRepository.findAll(spec);
    }


} 