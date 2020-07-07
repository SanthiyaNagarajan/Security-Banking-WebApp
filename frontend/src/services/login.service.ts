import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import {Credentials} from "../models/credentials";

@Injectable({
  providedIn: 'root'
})

/**
 * Service to send post requests to server from login page.
 */
export class LoginService {
  private baseUrl = "http://localhost:8080/";
  private url:string;
  constructor(private http:HttpClient) {
    this.url=this.baseUrl+"login";
  }

  public login(credential:Credentials) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'my-auth-token'
      })
    };
    return this.http.post<Credentials>(this.url,JSON.stringify(credential), httpOptions);
  }
}
