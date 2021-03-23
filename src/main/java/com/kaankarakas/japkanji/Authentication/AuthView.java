
package com.kaankarakas.japkanji.Authentication;

import com.kaankarakas.japkanji.entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

//creating private variables for using inside the class
public class AuthView {
     
    private String username;
    private String authusername; 
    private String password;
    private String firstname;
    private String surname;
    
     
        public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getUsername() {
        return username;
    }
   
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAuthusername() {
        return AuthUtil.getUserName();
    }
     public void setAuthusername(String authusername) {
        
    }
   
    public String login() {
        int result = AuthDB.checkUser(username, password);
        FacesMessage message = null;
        if (result==1) {
            // 1 result means admin user.
            // get Http Session and store username
            HttpSession session = AuthUtil.getSession();
            session.setAttribute("username", username);
             session.setAttribute("usertype", "admin");
     
            // After the user is checked Welcome message appears with the username popping
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().addCallbackParam("loggedIn", result);
            
            return "home";
        } 
        else if (result==0) { 
            // 0 means the the user us a student. Thats how I created for simplicity
            // get Http Session and store username
            HttpSession session = AuthUtil.getSession();
            session.setAttribute("username", username);
             session.setAttribute("usertype", "student");
             Users user=AuthDB.getUser(username, password);
             session.setAttribute("userid", user);
            
             // Again Welcome message appears when the students credidentials is accepted.
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().addCallbackParam("loggedIn", result);
            
            return "student";
        }
        
        
        else {
            // In case the credidentials arent accepted login error appears. 
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().addCallbackParam("loggedIn", result);
            
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Invalid Login!",
                    "Please Try Again!"));
 
            // Redirect to other pages
 
          
            return "login";
        }
    }
    // When the logout is tried the session ends and it returms to the home page.
    public String logout() {
      HttpSession session = AuthUtil.getSession();
      session.invalidate();
      return "/index.xhtml";
   }
    public String add() {
        // This section helps the program to add new users.
        Users us = new Users();
        us.setId(0);
        us.setUsertype(false);
        us.setFirstname(firstname);
        us.setSurname(surname);
        us.setUsername(username);
        us.setPassword(password);
        Users usRes=AuthDB.saveUsers(us);
        
        if (usRes.getId() > 0) {
            return "home";
        } else
            return "error";
    }
}
