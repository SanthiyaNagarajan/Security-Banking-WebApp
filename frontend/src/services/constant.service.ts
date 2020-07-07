import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
/**
 * Helper class to store persistent information across url pages.
 */
export class ConstantService {
  readonly baseAppUrl: string = 'http://localhost:3000/';
  readonly password:string = 'shsh';
  accountId:number;
  firstName:string;
  constructor() { }
}
