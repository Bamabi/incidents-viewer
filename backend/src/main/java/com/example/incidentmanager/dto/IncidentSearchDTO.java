package com.example.incidentmanager.dto;

import lombok.*;

/**
 * Data Transfer Object for Incident Search Criteria
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentSearchDTO {

    private String title;
    private String description;
    private String severity;
    private String ownerName;
    private Integer page = 0;
    private Integer size = 10;

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
        public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
}