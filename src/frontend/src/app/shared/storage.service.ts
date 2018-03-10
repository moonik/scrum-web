import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

  public scope: any | boolean = false;

  constructor() {
  }

  public getScope(): any | boolean {
    return this.scope;
  }

  public setScope(scope: any): void {
    this.scope = scope;
  }
}
