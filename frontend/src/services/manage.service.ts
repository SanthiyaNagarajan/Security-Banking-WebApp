import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Account} from "../models/account";
import {Transaction} from "../models/transaction";

@Injectable({
  providedIn: 'root'
})

/**
 * Service to send post requests to server from manage page, deals with bank functionalities.
 */
export class ManageService {
  private baseUrl = "http://localhost:8080/";
  private url:string;
  constructor(private http:HttpClient) {
    this.url=this.baseUrl;
  }

  public getBalance(transaction:Transaction) {
    this.url= this.baseUrl +"getBalance";

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'my-auth-token'
      })
    };
    return this.http.post<Account>(this.url,JSON.stringify(transaction), httpOptions);
  }

  public deposit(transaction:Transaction) {
    this.url= this.baseUrl +"deposit";

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'my-auth-token'
      })
    };
    return this.http.post<Account>(this.url,JSON.stringify(transaction), httpOptions);
  }

  public withdraw(transaction:Transaction) {
    this.url= this.baseUrl +"withdraw";

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'my-auth-token'
      })
    };
    return this.http.post<Account>(this.url,JSON.stringify(transaction), httpOptions);
  }
}
