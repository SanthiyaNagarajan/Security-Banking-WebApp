package com.security.atm.controllers;

import com.security.atm.viewmodels.AccountVm;
import com.security.atm.viewmodels.ApiResponseVm;
import com.security.atm.viewmodels.CredentialsVm;
import com.security.atm.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles the requests from the client related to login.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpServletRequest context;

    @PostMapping("/login")
    @ResponseBody()
    public ApiResponseVm login(@RequestBody String loginDetails){
        ApiResponseVm response = new ApiResponseVm();
        try {
            CredentialsVm credentialsVm = CredentialsVm.parse(loginDetails,context.getHeader("Content-Type"));
            AccountVm account =  accountService.isValidCredentials(credentialsVm);
            if(account!=null){
                response.setStatusCode(200);
                response.setReturnObject(account);
            }
            else {
                response.setStatusCode(400);
                response.setExceptionMessage("Invalid input !!");
            }

        }
        catch (Exception ex){
            response.setStatusCode(400);
            response.setExceptionMessage("Login error !!");
        }
        return response;
    }
}
