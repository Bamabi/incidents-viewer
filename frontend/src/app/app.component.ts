import { Component, inject } from '@angular/core';
import {
  TranslatePipe,
  TranslateService
} from "@ngx-translate/core";
import { IncidentSearchComponent } from './components/incident-search/incident-search.component';

/**
 * Root component of the application
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [IncidentSearchComponent, TranslatePipe],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  private translate = inject(TranslateService);
  currentLanguage = 'en'; // Track the current language

  constructor() {
      this.translate.addLangs(['fr', 'en']);
      this.translate.setFallbackLang('en');
      this.translate.use('en');
  }

  setLanguage(lang: string) {
    this.translate.use(lang);
    this.currentLanguage = lang;
  }
}
