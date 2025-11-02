import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Incident } from '../models/incident.model';
import { Page } from '../models/page.model';

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
    page: number = 0,
    size: number = 10
  ): Observable<Page<Incident>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

      // Add optional parameters only if they exist
      if (title) params = params.set('title', title);
      if (description) params = params.set('description', description);
      if (severity) params = params.set('severity', severity);
      if (ownerName) params = params.set('ownerName', ownerName);
      
    return this.http.get<Page<Incident>>(`${this.apiUrl}`, { params });
  }
}
