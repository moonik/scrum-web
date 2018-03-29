import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

  scope: any | boolean = false;

  constructor() {
  }

  getScope(): any | boolean {
    return this.scope;
  }

  setScope(scope: any): void {
    this.scope = scope;
  }
}
