package com.example.incidentviewer.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.incidentviewer.dto.IncidentDTO;
import com.example.incidentviewer.dto.IncidentSearchDTO;
import com.example.incidentviewer.service.IncidentService;

/**
 * REST Controller for Incident operations
 * Baseline Version
 */
@RestController
@RequestMapping("/api/incidents")
@CrossOrigin(origins = "*") // For development - should be restricted in production
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Search incidents endpoint
     * GET /api/incidents
     * <p>
     * Query parameters:
     * - title: Search by title (partial match)
     * - description: Search by title (partial match)
     * - severity: Search by status (partial match)
     * - ownerName: Search by owner name (partial match)
     */
    @GetMapping()
    @Cacheable("incidentSearchCache")
    public ResponseEntity<Page<IncidentDTO>> searchIncidents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String ownerName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        IncidentSearchDTO searchDTO = new IncidentSearchDTO();
        searchDTO.setTitle(title);
        searchDTO.setDescription(description);
        searchDTO.setSeverity(severity);
        searchDTO.setOwnerName(ownerName);
        searchDTO.setPage(page);
        searchDTO.setSize(size);

        Page<IncidentDTO> results = incidentService.searchIncidents(searchDTO);
        return ResponseEntity.ok(results);
    }
}