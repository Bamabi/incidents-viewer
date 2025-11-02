import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TranslatePipe } from "@ngx-translate/core";
import { IncidentService } from '../../services/incident.service';
import { Incident } from '../../models/incident.model';
import { timeInterval } from 'rxjs';

/**
 * Component for searching and displaying incidents
 */
@Component({
  selector: 'app-incident-search',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './incident-search.component.html',
  styleUrls: ['./incident-search.component.scss']
})
export class IncidentSearchComponent implements OnInit {
  status = [
    {value: 'LOW', viewValue: 'Low'},
    {value: 'MEDIUM', viewValue: 'Medium'},
    {value: 'HIGH', viewValue: 'High'},
  ];
  
  // Search criteria
  searchTitle: string = '';
  searchDescription: string = '';
  searchStatus: string = '';
  searchOwner: string = '';

  // Results
  incidents: Incident[] = [];
  totalElements: number = 0;
  isLoading: boolean = false;
  requestTimer: number = 0;
  errorMessage: string = '';

  constructor(private incidentService: IncidentService) {}

  ngOnInit(): void {
    this.search();
  }

  /**
   * Perform search with current criteria
   */
  search(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.incidentService.searchIncidents(
      this.searchTitle || undefined,
      this.searchDescription || undefined,
      this.searchStatus || undefined,
      this.searchOwner || undefined
    ).pipe(timeInterval()).subscribe({ // timeInterval measure is ~5ms higher than actual request time in browser
      next: res => {
        this.incidents = res.value;
        this.totalElements = res.value.length;
        this.isLoading = false;
        this.requestTimer = res.interval / 1000; // convert interval in milliseconds to seconds
      },
      error: (error) => {
        this.errorMessage = 'Error loading incidents: ' + error.message;
        this.isLoading = false;
      }
    });
  }

  /**
   * Reset all search criteria and refresh
   */
  reset(): void {
    this.searchTitle = this.searchDescription = this.searchStatus = this.searchOwner = '';
    this.search();
  }

}
