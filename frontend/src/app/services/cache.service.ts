import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';

/**
 * Service pour gérer le cache manuellement
 */
@Injectable({
  providedIn: 'root'
})
export class CacheService {
  private cache = new Map<string, { response: HttpResponse<any>; timestamp: number }>();

  // Cache duration (5 minutes)
  readonly CACHE_DURATION = 5 * 60 * 1000;

  has(key: string): boolean {
    return this.cache.has(key);
  }

  get(key: string): HttpResponse<any> | undefined {
    const entry = this.cache.get(key);
    return entry ? entry.response : undefined;
  }

  set(key: string, response: HttpResponse<any>): void {
    this.cache.set(key, { response: response.clone(), timestamp: Date.now() });
  }

  isValid(key: string): boolean {
    const entry = this.cache.get(key);
    if (!entry) {
        return false;
    }
    return (Date.now() - entry.timestamp) < this.CACHE_DURATION;
  }

  /**
   * Empty all cache entries
   */
  clearAll(): void {
    console.log('[CacheService] Clearing all cache');
    this.cache.clear();
  }
}
