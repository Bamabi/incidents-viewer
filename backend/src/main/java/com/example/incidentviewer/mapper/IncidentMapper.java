package com.example.incidentviewer.mapper;

import com.example.incidentviewer.dto.IncidentDTO;
import com.example.incidentviewer.entity.Incident;

public class IncidentMapper {

    private IncidentMapper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Convert Incident entity to DTO
     */
    public static IncidentDTO toDTO(Incident incident) {
        IncidentDTO dto = new IncidentDTO();
        dto.setId(incident.getId());
        dto.setTitle(incident.getTitle());
        dto.setDescription(incident.getDescription());
        dto.setSeverity(incident.getStatus());
        dto.setOwnerLastName(incident.getOwner().getLastName());
        dto.setOwnerFirstName(incident.getOwner().getFirstName());
        dto.setOwnerEmail(incident.getOwner().getEmail());
        dto.setCreatedAt(incident.getCreatedAt());
        return dto;
    }

    /**
     * Convert Incident DTO to entity
     * Add toEntity if the app should support creating/updating Incidents
     */
    //public static Incident toEntity(IncidentDTO incidentDto) {
    //    Incident entity = new Incident();
    //    return entity;
    //}
}
