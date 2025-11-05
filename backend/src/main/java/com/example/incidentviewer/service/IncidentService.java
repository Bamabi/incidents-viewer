package com.example.incidentviewer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

        Specification<Incident> spec = new IncidentSpecification(searchDTO);

        Page<Incident> incidents = incidentRepository.findAll(spec, pageable);

        // Convert to DTOs
        return incidents.map(IncidentMapper::toDTO);
    }
}
