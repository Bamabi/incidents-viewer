package com.example.incidentviewer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.incidentviewer.dto.IncidentDTO;
import com.example.incidentviewer.dto.IncidentSearchDTO;
import com.example.incidentviewer.entity.Incident;
import com.example.incidentviewer.mapper.IncidentMapper;
import com.example.incidentviewer.repository.IncidentRepository;

/**
 * Service layer for Incident operations
 */
@Service
@Transactional(readOnly = true)
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Search incidents based on provided criteria
     */
    public Page<IncidentDTO> searchIncidents(IncidentSearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    

        Page<Incident> incidents;

        // Check which search criteria are provided
        boolean hasTitle = searchDTO.getTitle() != null && !searchDTO.getTitle().isEmpty();
        boolean hasDescription = searchDTO.getDescription() != null && !searchDTO.getDescription().isEmpty();
        boolean hasStatus = searchDTO.getSeverity() != null && !searchDTO.getSeverity().isEmpty();
        boolean hasOwnerName = searchDTO.getOwnerName() != null && !searchDTO.getOwnerName().isEmpty();

        // Execute appropriate query based on criteria
        if (hasTitle && hasDescription && hasStatus && hasOwnerName) {
            // All criteria provided - search in all fields
            incidents = incidentRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrSeverityContainingIgnoreCaseOrOwner_EmailContainingIgnoreCase(
                    searchDTO.getTitle(), searchDTO.getDescription(), searchDTO.getSeverity(), searchDTO.getOwnerName(), pageable
            );
        }  else if (hasTitle) {
            incidents = incidentRepository.findByTitleContainingIgnoreCase(searchDTO.getTitle(), pageable);
        } else if (hasDescription) {
            incidents = incidentRepository.findByDescriptionContainingIgnoreCase(searchDTO.getDescription(), pageable);
        } else if (hasStatus) {
            incidents = incidentRepository.findBySeverityContainingIgnoreCase(searchDTO.getSeverity(), pageable);
        } else if (hasOwnerName) {
            incidents = incidentRepository.findByOwner_EmailContainingIgnoreCase(searchDTO.getOwnerName(), pageable);
        } else {
            // No criteria - return all
            incidents = incidentRepository.findAll(pageable);
        }

        // Convert to DTOs
        return incidents.map(IncidentMapper::toDTO);
    }
}
