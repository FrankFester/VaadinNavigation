/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import java.util.Date;
import java.util.HashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frank
 */
@Service
public class UserService {

    private HashMap<String, User> userMap = new HashMap<>();

    public class User {
        String userName;
        Date loginDate;
        Date logOffDate;
        boolean loggedIn;

        public User(String userName, Date loginDate, Date logOffDate, boolean loggedIn) {
            this.userName = userName;
            this.loginDate = loginDate;
            this.logOffDate = logOffDate;
            this.loggedIn = loggedIn;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Date getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(Date loginDate) {
            this.loginDate = loginDate;
        }

        public Date getLogOffDate() {
            return logOffDate;
        }

        public void setLogOffDate(Date logOffDate) {
            this.logOffDate = logOffDate;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public void setLoggedIn(boolean loggedIn) {
            this.loggedIn = loggedIn;
        }
             
    }   
    
      
    public boolean addUserLoggedIn(String userName) {
        User u = userMap.get(userName);
        if ((u != null) && (u.isLoggedIn())) {
                // Benutzer bereits angemeldet...
                // Logik, ob 2. Mal möglich???
                // LOG.info("User {} hat sich bereits am {} angemeldet...",u.getUserName(), u.getLoginDate());
                return false;
                ///
        } else if (u!=null) {
                u.setLoginDate(new Date());
                u.setLoggedIn(true);
        } else {
            u = new User(userName,new Date(),null,true);
        }
        userMap.put(userName, u);
        return true;

   }
    
    public void removeUserLogff(String userName) {
        User u = userMap.get(userName);
        if(u != null) {
            u.setLogOffDate(new Date());
            u.setLoggedIn(false);
            userMap.put(userName, u);
        } else {
            // Fehler...
        }
   }   
    
    public HashMap<String, User> getUserMap() {
        return this.userMap;   
    }

}
