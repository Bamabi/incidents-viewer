import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Incident } from '../models/incident.model';

/**
 * Service for incident-related HTTP operations
 */
@Injectable({
  providedIn: 'root'
})
export class IncidentService {
  private apiUrl = 'http://localhost:8080/api/incidents';

  constructor(private http: HttpClient) {}

  /**
   * Search incidents with optional filters
   */
  searchIncidents(
    title?: string,
    description?: string,
    severity?: string,
    ownerName?: string,
  ): Observable<Incident[]> {
    let params = {
      ...(title && { title }),
      ...(description && { description }),
      ...(severity && { severity }),
      ...(ownerName && { ownerName })
    } as HttpParams;

    return this.http.get<Incident[]>(`${this.apiUrl}`, { params });
  }
}
