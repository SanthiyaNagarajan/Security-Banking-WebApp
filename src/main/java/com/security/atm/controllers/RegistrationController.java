package com.security.atm.controllers;

import com.security.atm.viewmodels.AccountVm;
import com.security.atm.viewmodels.ApiResponseVm;
import com.security.atm.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Handles the requests from the client related to registration.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private HttpServletRequest context;


    @PostMapping(value = "/register")
    @ResponseBody
    public ApiResponseVm register(@RequestBody String accountDetails){
        ApiResponseVm response = new ApiResponseVm();
        try {
            AccountVm account= AccountVm.parse(accountDetails,context.getHeader("Content-Type"));
            boolean status = accountService.save(account);
            if(status){
                response.setStatusCode(200);
            }
            else{
                response.setStatusCode(400);
                response.setExceptionMessage("Registration unsuccessful !!");
            }
        }
        catch (IllegalArgumentException iex){
            response.setStatusCode(400);
            response.setExceptionMessage("Invalid input!! " + iex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(400);
            response.setExceptionMessage(ex.getMessage());
        }
        return response;
    }
}
