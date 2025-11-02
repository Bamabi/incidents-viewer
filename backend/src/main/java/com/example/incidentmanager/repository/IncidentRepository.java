package com.example.incidentmanager.repository;

import com.example.incidentmanager.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Incident entity
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    /**
     * Search incidents by multiple criteria
     */
    Page<Incident> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrSeverityContainingIgnoreCaseOrOwner_EmailContainingIgnoreCase(
            String title, String description,  String severity, String ownerName, Pageable pageable
    );

    /**
     * Find by title
     */
    Page<Incident> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * Find by description
     */
    Page<Incident> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    /**
     * Find by severity
     */
    Page<Incident> findBySeverityContainingIgnoreCase(String severity, Pageable pageable);

    /**
     * Find by owner name
     */
    Page<Incident> findByOwner_EmailContainingIgnoreCase(String ownerName, Pageable pageable);
}