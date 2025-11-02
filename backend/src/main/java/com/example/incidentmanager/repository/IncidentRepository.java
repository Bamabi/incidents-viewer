package com.example.incidentmanager.repository;

import com.example.incidentmanager.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Incident entity
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    /**
     * Search incidents by multiple criteria
     */
    List<Incident> findByTitleContainingIgnoreCaseOrSeverityContainingIgnoreCaseOrOwner_EmailContainingIgnoreCase(
            String title, String severity, String ownerName
    );

    /**
     * Find by title
     */
    List<Incident> findByTitleContainingIgnoreCase(String title);

    /**
     * Find by severity
     */
    List<Incident> findBySeverityContainingIgnoreCase(String severity);

    /**
     * Find by owner name
     */
    List<Incident> findByOwner_EmailContainingIgnoreCase(String ownerName);
}