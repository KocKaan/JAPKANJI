
package com.kaankarakas.japkanji.Authentication;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//HTTP sessions  allows Web servers 
//to maintain user identity and to store user-specific data during multiple 
//request/response interactions between a client application and a Web application.

public class AuthUtil {
          public static HttpSession getSession() {
        return (HttpSession)
          FacesContext.  
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }
          
  //Returns the current HttpSession associated with this request or,
 //if there is no current session and create is true, returns a new session. 
      public static HttpServletRequest getRequest() {
       return (HttpServletRequest) FacesContext.
          getCurrentInstance().
          getExternalContext().getRequest();
      }
 
      // get method user name 
      public static String getUserName()
      {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return  session.getAttribute("username").toString();
      }
       
      // // get method user name 
      public static String getUserId()
      {
        HttpSession session = getSession();
        if ( session != null )
            return (String) session.getAttribute("userid");
        else
            return null;
      }
    
}
