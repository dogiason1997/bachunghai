package com.example.demo.service;

import com.example.demo.entity.ProcessStage;
import com.example.demo.repository.ProcessStageRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProcessStageService {

    @Autowired
    private ProcessStageRepository processStageRepository;

    public Page<ProcessStage> getAllProcessStages(Pageable pageable) {
        return processStageRepository.findAll(pageable);
    }

    public List<ProcessStage> getAllProcessStages() {
        return processStageRepository.findAll();
    }

    public ProcessStage getProcessStageById(Integer id) {
        return processStageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" không tồn tại bước xử lý với id: " + id));
    }

    @Transactional
    public ProcessStage createProcessStage(ProcessStage processStage) {
        if (processStageRepository.existsByStageName(processStage.getStageName())) {
            throw new IllegalArgumentException("Tên giai đoạn '" + processStage.getStageName() + "' đã tồn tại!");
        }
        return processStageRepository.save(processStage);
    }

    @Transactional
    public ProcessStage updateProcessStage(Integer id, ProcessStage processStage) {
        ProcessStage existingStage = getProcessStageById(id);
        
        // Kiểm tra tên giai đoạn có bị trùng không (trừ chính nó)
        if (processStageRepository.existsByStageNameAndStageIdNot(
                processStage.getStageName(), id)) {
            throw new IllegalArgumentException("Tên giai đoạn '" + processStage.getStageName() + "' đã tồn tại!");
        }

        existingStage.setStageName(processStage.getStageName());
        existingStage.setStageOrder(processStage.getStageOrder());
        existingStage.setDescription(processStage.getDescription());
        
        return processStageRepository.save(existingStage);
    }

    @Transactional
    public void deleteProcessStage(Integer id) {
        ProcessStage processStage = getProcessStageById(id);
        processStageRepository.delete(processStage);
    }
}