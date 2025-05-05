package com.example.demo.specification;

import com.example.demo.dto.DocumentSearchDTO;
import com.example.demo.entity.Document;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class DocumentSpecification {

    public static Specification<Document> searchByCriteria(DocumentSearchDTO searchDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchDTO.getDocumentNumber() != null && !searchDTO.getDocumentNumber().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("documentNumber")), 
                    "%" + searchDTO.getDocumentNumber().toLowerCase() + "%"
                ));
            }

            if (searchDTO.getAbstractText() != null && !searchDTO.getAbstractText().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("abstractText")), 
                    "%" + searchDTO.getAbstractText().toLowerCase() + "%"
                ));
            }

            if (searchDTO.getContent() != null && !searchDTO.getContent().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("content")), 
                    "%" + searchDTO.getContent().toLowerCase() + "%"
                ));
            }

            if (searchDTO.getSigner() != null && !searchDTO.getSigner().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("signer")), 
                    "%" + searchDTO.getSigner().toLowerCase() + "%"
                ));
            }

            if (searchDTO.getDateOfIssueFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("dateOfIssue"), searchDTO.getDateOfIssueFrom()
                ));
            }

            if (searchDTO.getDateOfIssueTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("dateOfIssue"), searchDTO.getDateOfIssueTo()
                ));
            }

            if (searchDTO.getEffectiveDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("effectiveDate"), searchDTO.getEffectiveDateFrom()
                ));
            }

            if (searchDTO.getEffectiveDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("effectiveDate"), searchDTO.getEffectiveDateTo()
                ));
            }

            if (searchDTO.getCategoryName() != null && !searchDTO.getCategoryName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("category").get("name")), 
                    searchDTO.getCategoryName().toLowerCase()
                ));
            }

            if (searchDTO.getNameDocumentType() != null && !searchDTO.getNameDocumentType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("documentType").get("nameDocumentType")), 
                    searchDTO.getNameDocumentType().toLowerCase()
                ));
            }

            if (searchDTO.getNameDocumentField() != null && !searchDTO.getNameDocumentField().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("documentField").get("nameDocumentField")), 
                    searchDTO.getNameDocumentField().toLowerCase()
                ));
            }

            if (searchDTO.getNameIssuingAgency() != null && !searchDTO.getNameIssuingAgency().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("issuingAgency").get("nameIssuingAgency")), 
                    searchDTO.getNameIssuingAgency().toLowerCase()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
} 