import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { CacheService } from '../services/cache.service';

@Injectable()
export class CacheInterceptor implements HttpInterceptor {
  constructor(private cacheService: CacheService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(`[Cache Interceptor] Intercepting request: ${req.method} ${req.urlWithParams}`);

    // Only cache GET requests
    if (req.method !== 'GET') {
      return next.handle(req);
    }

    const cacheKey = req.urlWithParams;

    if (this.cacheService.has(cacheKey) && this.cacheService.isValid(cacheKey)) {
      console.log(`[Cache HIT] ${cacheKey}`);
      const cached = this.cacheService.get(cacheKey);
      return of(cached!.clone());
    }

    console.log(`[Cache MISS] ${cacheKey}`);

    return next.handle(req).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          this.cacheService.set(cacheKey, event);
        }
      })
    );
  }
}
