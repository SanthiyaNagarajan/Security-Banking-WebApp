import {Component, OnInit, ViewChild} from '@angular/core';
import {Account} from "../../../models/account";
import {RegisterService} from "../../../services/register.service";
import {ConstantService} from "../../../services/constant.service";
import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

/**
 * Facilitates registration procedures.
 */
export class RegisterComponent implements OnInit {
  accountDetails: Account;
  confirmpassword: string;
  errormessage: string;
  errorstatus: boolean;
  successstatus:boolean;
  successmessage:string;
  @ViewChild('myForm',{static: false}) form: any;

  constructor(private registerService: RegisterService,private constant: ConstantService,) {
    this.accountDetails = new Account();
    this.errorstatus = false;
  }

  ngOnInit() {
  }

  onInput(event){
    let regex= /^[1-9][0-9]+\.?[0-9][0-9]$/ ///^[1-9][0-9]+\.?[0-9][0-9]$/;
    if(!regex.test(event.target.value)) {
      this.form.form.controls['initialbalance'].setErrors({'incorrect': true});
    }
  }

  /**
   * Submits the registration details entered by user for account creation after validating the data.
   */
  onSubmit() {
    this.errorstatus = false;
    this.accountDetails.balance = Number.parseFloat(this.accountDetails.balance).toFixed(2)

    if (this.accountDetails.password.trim() != this.confirmpassword.trim()) {
      this.errormessage = "Passwords should match";
      this.errorstatus = true;
      return;
    }

    this.errorstatus = false;
    var encryptedOutput = CryptoJS.AES.encrypt(this.accountDetails.password,this.constant.password).toString();
    this.accountDetails.password = encryptedOutput;
    this.confirmpassword = encryptedOutput;
    this.registerService.register(this.accountDetails).subscribe(result => {
      if (result != null) {
        if(result["statusCode"]=="400"){
          this.errorstatus = true;
          this.successstatus = false;
          this.errormessage = "Error!! "+result["exceptionMessage"];
        }
        if(result["statusCode"]=="200"){
          this.successstatus = true;
          this.errorstatus = false;
          this.successmessage = "Registration successful!!";
        }
      }
    });
    this.accountDetails.password = "";
    this.confirmpassword = "";
  }
}

