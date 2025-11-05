package com.example.incidentviewer.service;

import org.springframework.data.jpa.domain.Specification;

import com.example.incidentviewer.dto.IncidentSearchDTO;
import com.example.incidentviewer.entity.Incident;
import com.example.incidentviewer.entity.Person;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class IncidentSpecification implements Specification<Incident> {

    private final String title;
    private final String description;
    private final String severity;
    private final String ownerName;

    public IncidentSpecification(IncidentSearchDTO searchDTO) {
        this.title = searchDTO.getTitle();
        this.description = searchDTO.getDescription();
        this.severity = searchDTO.getSeverity();
        this.ownerName = searchDTO.getOwnerName();
    }

    @Override
    public Predicate toPredicate(Root<Incident> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction(); // Start with a true predicate

        if (title != null) {
            predicate = criteriaBuilder.and(predicate,
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                 "%" + title.trim().toLowerCase() + "%", 
                 '\\'));
        }

        if (description != null) {
            predicate = criteriaBuilder.and(predicate,
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                 "%" + description.trim().toLowerCase() + "%", 
                 '\\'));
        }

        if (severity != null) {
            predicate = criteriaBuilder.and(predicate,
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("severity")), 
                    "%" + severity.trim().toLowerCase() + "%", 
                    '\\'));
        }

        if (ownerName != null ) {
            Join<Incident, Person> personJoin = root.join("owner");
            String ownerPattern = "%" + ownerName.trim().toLowerCase() + "%";
            predicate = criteriaBuilder.and(predicate,
                criteriaBuilder.or(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(personJoin.get("firstName")), 
                        ownerPattern, '\\'),
                    criteriaBuilder.like(
                        criteriaBuilder.lower(personJoin.get("lastName")), 
                        ownerPattern, '\\'),
                    criteriaBuilder.like(
                        criteriaBuilder.lower(personJoin.get("email")), 
                        ownerPattern, '\\')
                )
            );
        }

        return predicate;
    }
}