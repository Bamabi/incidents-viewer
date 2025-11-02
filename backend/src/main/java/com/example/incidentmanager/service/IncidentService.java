package com.example.incidentmanager.service;

import com.example.incidentmanager.dto.IncidentDTO;
import com.example.incidentmanager.dto.IncidentSearchDTO;
import com.example.incidentmanager.entity.Incident;
import com.example.incidentmanager.mapper.IncidentMapper;
import com.example.incidentmanager.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<IncidentDTO> searchIncidents(IncidentSearchDTO searchDTO) {
        List<Incident> incidents;

        // Check which search criteria are provided
        boolean hasTitle = searchDTO.getTitle() != null && !searchDTO.getTitle().isEmpty();
        boolean hasStatus = searchDTO.getSeverity() != null && !searchDTO.getSeverity().isEmpty();
        boolean hasOwnerName = searchDTO.getOwnerName() != null && !searchDTO.getOwnerName().isEmpty();

        // Execute appropriate query based on criteria
        if (hasTitle && hasStatus && hasOwnerName) {
            // All criteria provided - search in all fields
            incidents = incidentRepository.findByTitleContainingIgnoreCaseOrSeverityContainingIgnoreCaseOrOwner_EmailContainingIgnoreCase(
                    searchDTO.getTitle(), searchDTO.getSeverity(), searchDTO.getOwnerName()
            );
        }  else if (hasTitle) {
            incidents = incidentRepository.findByTitleContainingIgnoreCase(searchDTO.getTitle());
        } else if (hasStatus) {
            incidents = incidentRepository.findBySeverityContainingIgnoreCase(searchDTO.getSeverity());
        } else if (hasOwnerName) {
            incidents = incidentRepository.findByOwner_EmailContainingIgnoreCase(searchDTO.getOwnerName());
        } else {
            // No criteria - return all
            incidents = incidentRepository.findAll();
        }

        // Convert to DTOs
        return incidents.stream().map(IncidentMapper::toDTO).toList();
    }
}
