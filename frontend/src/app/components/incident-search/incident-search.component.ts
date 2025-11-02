import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TranslatePipe } from "@ngx-translate/core";
import { IncidentService } from '../../services/incident.service';
import { Incident } from '../../models/incident.model';
import { Page } from '../../models/page.model';
import { timeInterval } from 'rxjs';
import { TimeInterval } from 'rxjs/internal/operators/timeInterval';

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
  totalPages: number = 0;
  currentPage: number = 0;
  pageSize: number = 10;
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
      this.searchOwner || undefined,
      this.currentPage,
      this.pageSize
    ).pipe(timeInterval()).subscribe({ // timeInterval measure is ~5ms higher than actual request time in browser
      next: (res: TimeInterval<Page<Incident>>) => {
        let page = res.value;
        this.incidents = page.content;
        this.totalElements = page.numberOfElements;
        this.totalPages = page.totalPages;
        this.currentPage = page.number;
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
    this.currentPage = 0;
    this.search();
  }

  /**
   * Go to specific page
   */
  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.search();
    }
  }

  /**
   * Go to previous page
   */
  previousPage(): void {
    if (this.currentPage > 0) {
      this.goToPage(this.currentPage - 1);
    }
  }

  /**
   * Go to next page
   */
  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.goToPage(this.currentPage + 1);
    }
  }

  /**
   * Get array of page numbers for pagination
   */
  getPageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

}
