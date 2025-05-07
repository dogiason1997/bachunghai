package com.example.demo.specification;

import com.example.demo.dto.LetterSearchDTO;
import com.example.demo.entity.Letter;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class LetterSpecification {
    public static Specification<Letter> filterBy(LetterSearchDTO dto) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (dto.getLetterCode() != null && !dto.getLetterCode().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("LetterCode"), "%" + dto.getLetterCode() + "%"));
            }
            if (dto.getLetterTitle() != null && !dto.getLetterTitle().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("LetterTitle"), "%" + dto.getLetterTitle() + "%"));
            }
            if (dto.getPlaceSending() != null && !dto.getPlaceSending().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("placeSending"), "%" + dto.getPlaceSending() + "%"));
            }
            if (dto.getSignerLetter() != null && !dto.getSignerLetter().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("signerLetter"), "%" + dto.getSignerLetter() + "%"));
            }
            if (dto.getInfomartionUserLetter() != null && !dto.getInfomartionUserLetter().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("infomartionUserLetter"), "%" + dto.getInfomartionUserLetter() + "%"));
            }
            
            // Xử lý tìm kiếm theo khoảng thời gian cho dateIssue
            if (dto.getDateIssueFrom() != null && dto.getDateIssueTo() != null) {
                predicate = cb.and(predicate, 
                    cb.between(root.get("dateIssue"), dto.getDateIssueFrom(), dto.getDateIssueTo())
                );
            } else if (dto.getDateIssueFrom() != null) {
                predicate = cb.and(predicate, 
                    cb.greaterThanOrEqualTo(root.get("dateIssue"), dto.getDateIssueFrom())
                );
            } else if (dto.getDateIssueTo() != null) {
                predicate = cb.and(predicate, 
                    cb.lessThanOrEqualTo(root.get("dateIssue"), dto.getDateIssueTo())
                );
            }

            // Xử lý tìm kiếm theo khoảng thời gian cho dateEffective
            if (dto.getDateEffectiveFrom() != null && dto.getDateEffectiveTo() != null) {
                predicate = cb.and(predicate, 
                    cb.between(root.get("dateEffective"), dto.getDateEffectiveFrom(), dto.getDateEffectiveTo())
                );
            } else if (dto.getDateEffectiveFrom() != null) {
                predicate = cb.and(predicate, 
                    cb.greaterThanOrEqualTo(root.get("dateEffective"), dto.getDateEffectiveFrom())
                );
            } else if (dto.getDateEffectiveTo() != null) {
                predicate = cb.and(predicate, 
                    cb.lessThanOrEqualTo(root.get("dateEffective"), dto.getDateEffectiveTo())
                );
            }

            if (dto.getDaySigner() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("daySigner"), dto.getDaySigner()));
            }
            if (dto.getAbstracts() != null && !dto.getAbstracts().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("abstracts"), "%" + dto.getAbstracts() + "%"));
            }
            if (dto.getDeadline() != null && !dto.getDeadline().isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("deadline"), "%" + dto.getDeadline() + "%"));
            }
            if (dto.getPrioritize() != null && !dto.getPrioritize().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("prioritize"), dto.getPrioritize()));
            }
            if (dto.getLetterType() != null && !dto.getLetterType().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("letterType"), dto.getLetterType()));
            }
            if (dto.getLetterSercurity() != null && !dto.getLetterSercurity().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("letterSercurity"), dto.getLetterSercurity()));
            }
            if (dto.getCategoryName() != null && !dto.getCategoryName().isEmpty()) {
                Join<Object, Object> categoryJoin = root.join("category", JoinType.INNER);
                predicate = cb.and(predicate, cb.like(categoryJoin.get("name"), "%" + dto.getCategoryName() + "%"));
            }

            return predicate;
        };
    }
}