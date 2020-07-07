# ATM

## Description
The program is an Online banking web application built with Spring Boot and Angular. 
The application is purposefully injected with a few vulnerabilities to demonstrate understanding of core security concepts.
It is assumed that all logged in bank functionalities are over HTTPS.
It is also assumed that some sensitive data stored in the source code/source files, such as the raw DB file and encryption keys, would not be known to an attacker unless they were able to obtain it through an attack. 

The application opens up with a login page where the user is prompted for an Username and password. 
The user is also presented with a link for registration. 
Upon opening this link (/register), the user is presented with a form to fill various credentials. 
The user will be able to successfully register once they fill in the details. The requirements enforced are:
* Balance amount falls within the range of USD 0.00 - USD 4294967296.00.  
* Amount entered should follow the US currency standards by inputting a whole number followed by a two decimal precise number. 
(Example: $12.31 is valid but $12.315 is invalid)
* Account names and passwords are restricted to underscores, hyphens, dots, digits, and lowercase alphabetical characters. 
Account names and passwords are restricted to a length of 127 characters. 
* Usernames are considered unique. Any attempt to use existing usernames would lead to unsuccessful registration. 
Once the user is done registering, they have a redirection link back to the login page.

Upon returning to the login page (/login), the user can enter their registered credentials to login successfully. 
This redirects to the page with key bank functionalities (/manage) and has an option to logout. The functionalities covered are:
* Check balance
* Withdraw amount
* Deposit amount

Each of these functionalities require the user to retype their password for added security concerns. 
Both withdraw and deposit amounts have an additional input form for the amount, this amount entered again should fall within the range of USD 0.00 - USD 4294967296.00. 
Once the user is done, they can logout from their account which will redirect them back to the login page (/login).


## Program Execution

The program was implemented using a RESTful backend with Spring Boot and a JavaScript frontend with Angular. 
Before program execution, follow the steps below to install npm which is essential for running Angular. 
* [Download Node.js](https://nodejs.org/en/download/),which will automatically install npm.
* To check if you already have Node.js installed and npm installed, use ```node -v``` and ```npm -v``` commands. 
* Make sure you have the updated npm version by running ```npm install npm@latest -g```

After installing npm, follow the steps below for successful program execution.
* Clone the project to local by running the command: 
```
git clone https://github.com/SanthiyaNagarajan/ATM-SWE266P.git
```
* Open IntelliJ IDEA → File → New project → From existing sources → Locally cloned project directory → Import project from Maven → Select SDK → Click Finish.
* If there is a pop-up in the bottom right corner that Angular framework is detected, click configure. 
* Make sure port 8080 is available for the server and port 4200 for the frontend.
* Build and run the Spring Boot application (SecurityAtmApplication) in IntelliJ.
* Parallelly, open command line → Navigate to project directory
* Change to frontend directory by running command  ```cd  frontend```
* Install the latest version of Angular CLI by running the command:
```
npm install -g @angular/cli@1.7.4
```
* Get the dependencies installed by running ```npm install```
* Execute the following command to have the frontend up and running 
```ng serve --open --prod```

