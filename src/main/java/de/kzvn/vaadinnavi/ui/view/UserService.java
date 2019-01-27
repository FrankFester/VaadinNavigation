/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.server.VaadinSession;
import java.util.Collection;
import java.util.HashMap;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Frank
 */

public class UserService {

    public HashMap<String, String> getUserListe() {
        HashMap<String, String> userMap = new HashMap<>();
       // Collection<VaadinSession> sessions = VaadinSession.getAllSessions();

        return userMap;
    }

}
