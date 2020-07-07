import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Account} from "../models/account";

@Injectable({
  providedIn: 'root'
})

/**
 * Service to send post requests to server from register page.
 */
export class RegisterService {
  private baseUrl = "http://localhost:8080/";
  private url:string;
  private headers:Headers;

  constructor(private http:HttpClient) {
    this.url=this.baseUrl+"register";
  }

  public register(account:Account){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'my-auth-token'
      })
    };

    return this.http.post(this.url, JSON.stringify(account), httpOptions);
  }
}
