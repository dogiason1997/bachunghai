package com.example.demo.service;

import com.example.demo.entity.IssuingAgency;
import com.example.demo.repository.IssuingAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssuingAgencyService {

    @Autowired
    private IssuingAgencyRepository issuingAgencyRepository;

    public IssuingAgency createIssuingAgency(IssuingAgency issuingAgency) {
        return issuingAgencyRepository.save(issuingAgency);
    }

    public List<IssuingAgency> getAllIssuingAgencies() {
        return issuingAgencyRepository.findAll();
    }

    public Optional<IssuingAgency> getIssuingAgencyById(Integer id) {
        return issuingAgencyRepository.findById(id);
    }

    public IssuingAgency updateIssuingAgency(Integer id, IssuingAgency issuingAgency) {
        issuingAgency.setId_issuingAgency(id);
        return issuingAgencyRepository.save(issuingAgency);
    }

    public void deleteIssuingAgency(Integer id) {
        issuingAgencyRepository.deleteById(id);
    }
}