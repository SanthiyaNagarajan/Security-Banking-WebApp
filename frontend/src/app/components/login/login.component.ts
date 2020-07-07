import { Component, OnInit } from '@angular/core';
import {Credentials} from "../../../models/credentials";
import {LoginService} from "../../../services/login.service";
import {ConstantService} from "../../../services/constant.service";
import {ActivatedRoute, Router} from "@angular/router";
import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

/**
 * Facilitates login credentials
 */
export class LoginComponent implements OnInit {
  credentials:Credentials;
  loginErrorStatus:boolean;
  errormsg:string;
  successStatus:boolean;
  passwordHolder:string;
  constructor(private loginService: LoginService,
              private constant: ConstantService,
              private route: ActivatedRoute,
              private router: Router ) {
    this.credentials = new Credentials();
    this.constant.firstName = null;
    this.constant.accountId = null;
    this.loginErrorStatus = false;
  }

  /**
   * Submits the credentials entered by users and validates with the stored account details.
   */
  onSubmit() {
    this.passwordHolder = this.credentials.password;

    var encryptedOutput = CryptoJS.AES.encrypt(this.credentials.password,this.constant.password).toString();
    this.credentials.password = encryptedOutput;
    this.loginService.login(this.credentials).subscribe(result => {
      if (result != null) {
        if(result["statusCode"]=="400"){
          this.loginErrorStatus = true;
          this.errormsg= "Error! " + result["exceptionMessage"];
          this.credentials.password = null;
        }
        if(result["statusCode"]=="200"){
          this.loginErrorStatus = false;
          this.successStatus = true;
          this.credentials=result["returnObject"]
          this.constant.accountId = this.credentials.id;
          this.constant.firstName = this.credentials.firstName;
          this.router.navigate(['/manage']);
        }
      }
    });

    this.credentials.password = this.passwordHolder;
  }

  ngOnInit() {
  }
}
