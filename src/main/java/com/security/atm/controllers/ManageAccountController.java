package com.security.atm.controllers;

import com.security.atm.viewmodels.AccountVm;
import com.security.atm.viewmodels.ApiResponseVm;
import com.security.atm.viewmodels.TransactionVm;
import com.security.atm.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles the requests from the client related to manage account, deals with bank functionalities.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ManageAccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private HttpServletRequest context;


    @PostMapping("/getBalance")
    @ResponseBody
    public ApiResponseVm getBalance(@RequestBody String transactiondetails){
        ApiResponseVm response = new ApiResponseVm();
        try {
            TransactionVm transactionVm = TransactionVm.parse(transactiondetails,context.getHeader("Content-Type"));

            AccountVm account = accountService.getAccountBalance(transactionVm);

            response.setStatusCode(200);
            response.setReturnObject(account);
        }
        catch (IllegalArgumentException ex){
            response.setStatusCode(400);
            response.setExceptionMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(400);
            response.setExceptionMessage("Transaction unsuccessful !!");
        }
        return response;
    }


    @PostMapping("/deposit")
    @ResponseBody
    public ApiResponseVm deposit(@RequestBody String transactiondetails){
        ApiResponseVm response = new ApiResponseVm();
        try {
            TransactionVm transactionVm = TransactionVm.parse(transactiondetails,context.getHeader("Content-Type"));
            boolean status = accountService.deposit(transactionVm);
            AccountVm account = accountService.getAccountBalance(transactionVm);
            response.setReturnObject(account);

            if(status){
                response.setStatusCode(200);
            }else{
                response.setStatusCode(400);
                response.setExceptionMessage("Transaction unsuccessful !!");
            }
        }
        catch (IllegalArgumentException ex){
            response.setStatusCode(400);
            response.setExceptionMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(400);
            response.setExceptionMessage("Transaction unsuccessful !!");
        }
        return response;
    }

    @PostMapping("/withdraw")
    @ResponseBody
    public ApiResponseVm withdraw(@RequestBody String transactiondetails){
        ApiResponseVm response = new ApiResponseVm();
        try {
            TransactionVm transactionVm = TransactionVm.parse(transactiondetails,context.getHeader("Content-Type"));
            boolean status = accountService.withdraw(transactionVm);
            AccountVm account = accountService.getAccountBalance(transactionVm);

            response.setReturnObject(account);

            if(status){
                response.setStatusCode(200);
            }else{
                response.setStatusCode(400);
                response.setExceptionMessage("Transaction unsuccessful !!");
            }
        }
        catch (IllegalArgumentException ex){
            response.setStatusCode(400);
            response.setExceptionMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(400);
            response.setExceptionMessage("Transaction unsuccessful !!");
        }
        return response;
    }
}