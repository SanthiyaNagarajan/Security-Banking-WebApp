import { Component, OnInit } from '@angular/core';
import {ConstantService} from "../../../services/constant.service";
import {Account} from "../../../models/account";
import {ManageService} from "../../../services/manage.service";
import {Transaction} from "../../../models/transaction";
import * as CryptoJS from 'crypto-js';
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";
import {isNullOrUndefined} from "util";


@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.scss']
})

/**
 * Facilitates bank functionalities.
 */
export class ManageComponent implements OnInit {
  account:Account;
  transaction:Transaction;
  firstName: SafeHtml;
  manageErrorStatus:boolean;
  passwordHolder:string;
  errormessage:string;
  successStatus:boolean;
  successmessage:string;

  checkBalanceSelect: boolean;
  submitCheckBalanceSelect: boolean;

  withdrawSelect: boolean;
  submitWithDrawSelect: boolean;

  depositSelect: boolean;
  submitDepositSelect: boolean;

  constructor(private manageService: ManageService,
              private constant: ConstantService,
              private sanitizer: DomSanitizer) {
    this.account = new Account();
    this.transaction = new Transaction();
    this.manageErrorStatus = false;
    this.firstName = this.sanitizer.bypassSecurityTrustHtml(this.constant.firstName);
  }

  ngOnInit() {
  }

  /**
   * Logs out of the current account.
   */
  onLogout(){
    this.checkBalanceSelect = false;
    this.successStatus = false;
    this.manageErrorStatus = false;
    this.submitCheckBalanceSelect = false;
    this.submitDepositSelect = false;
    this.submitWithDrawSelect = false;
    this.account = null;
    this.transaction = null;
  }

  /**
   * Triggers a password validation to proceed to check balance.
   */
  onClickCheckBalance() {
    this.withdrawSelect = false;
    this.depositSelect = false;
    this.checkBalanceSelect = true;
    this.successStatus = false;
    this.manageErrorStatus = false;
    this.account.balance = null;
    this.submitCheckBalanceSelect = false;
    this.submitDepositSelect = false;
    this.submitWithDrawSelect = false;
  }

  /**
   * Subscribes to get balance event.
   */
  onCheckBalance() {
    this.passwordHolder = this.transaction.password
    this.submitCheckBalanceSelect = true;

    /**
     * Retrieves the stored Id from login page.
     */
    this.transaction.accountId = this.constant.accountId;

    var encryptedOutput = CryptoJS.AES.encrypt(this.transaction.password,this.constant.password).toString();
    this.transaction.password = encryptedOutput;

    this.manageService.getBalance(this.transaction).subscribe(result => {
      if(isNullOrUndefined(this.transaction.password) || this.transaction.password.trim().length == 0){
        return;
      }
      if (result != null) {
        if(result["statusCode"]=="400"){
          this.manageErrorStatus = true;
          this.errormessage= "Error! " + result["exceptionMessage"];
          this.checkBalanceSelect = false;
          this.transaction.password = null;
        }
        if(result["statusCode"]=="200"){
          this.manageErrorStatus = false;
          this.account=result["returnObject"];
          this.checkBalanceSelect = false;
          this.transaction.password = null;
        }
      }
    });

    this.transaction.password = this.passwordHolder;
  }

  /**
   * Triggers a password validation to proceed to deposit amount.
   */
  onClickDeposit() {
    this.withdrawSelect = false;
    this.checkBalanceSelect = false;
    this.depositSelect = true;
    this.successmessage = "";
    this.successStatus = false;
    this.manageErrorStatus = false;
    this.account.balance = null;
    this.submitCheckBalanceSelect = false;
    this.submitDepositSelect = false;
    this.submitWithDrawSelect = false;
  }

  /**
   * Subscribes to deposit event.
   */
  onDeposit(){
    this.submitDepositSelect = true;
    this.transaction.accountId = this.constant.accountId;
    this.transaction.amount = Number.parseFloat(this.transaction.amount).toFixed(2)

    var encryptedOutput = CryptoJS.AES.encrypt(this.transaction.password,this.constant.password).toString();
    this.transaction.password = encryptedOutput;

    this.manageService.deposit(this.transaction).subscribe(result => {
      if(isNullOrUndefined(this.transaction.password) || this.transaction.password.trim().length == 0){
        return;
      }
      if (result != null) {
        if(result["statusCode"]=="400"){
          this.manageErrorStatus = true;
          this.errormessage= "Error! " + result["exceptionMessage"];
          this.depositSelect = false;
          this.transaction.amount = null;
          this.transaction.password = null;
        }
        if(result["statusCode"]=="200"){
          this.manageErrorStatus = false;
          this.successStatus = true;
          this.account=result["returnObject"];
          this.successmessage = "Transaction successful !! :)";
          this.depositSelect = false;
          this.transaction.amount = null;
          this.transaction.password = null;
        }
      }
    });

    this.transaction.password =this.passwordHolder;
  }

  /**
   * Triggers a password validation to proceed to withdraw amount.
   */
  onClickWithdraw() {
    this.checkBalanceSelect = false;
    this.depositSelect= false;
    this.withdrawSelect = true;
    this.successmessage = "";
    this.successStatus = false;
    this.manageErrorStatus = false;
    this.account.balance = null;
    this.submitCheckBalanceSelect = false;
    this.submitDepositSelect = false;
    this.submitWithDrawSelect = false;
  }

  /**
   * Subscribes to withdraw event.
   */
  onWithdraw(){
    this.submitWithDrawSelect = true;
    this.transaction.accountId = this.constant.accountId;
    this.transaction.amount = Number.parseFloat(this.transaction.amount).toFixed(2)

    var encryptedOutput = CryptoJS.AES.encrypt(this.transaction.password,this.constant.password).toString();
    this.transaction.password = encryptedOutput;

    this.manageService.withdraw(this.transaction).subscribe(result => {
      if(isNullOrUndefined(this.transaction.password) || this.transaction.password.trim().length == 0){
        return;
      }
      if (result != null) {
        if(result["statusCode"]=="400"){
          this.manageErrorStatus = true;
          this.errormessage= "Error! " + result["exceptionMessage"];
          this.withdrawSelect = false;
          this.transaction.amount = null;
          this.transaction.password = null;
        }
        if(result["statusCode"]=="200"){
          this.manageErrorStatus = false;
          this.successStatus = true;
          this.account=result["returnObject"];
          this.successmessage = "Transaction successful !! :)";
          this.withdrawSelect = false;
          this.transaction.amount = null;
          this.transaction.password = null;
        }
      }
    });
    this.transaction.password = this.passwordHolder;
  }
}
