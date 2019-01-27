/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.SessionDestroyEvent;
import com.vaadin.flow.server.SessionDestroyListener;
import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.SessionInitListener;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frank
 */
@Component
@VaadinSessionScope
public class SessionService implements SessionDestroyListener, SessionInitListener {
    
    private static final Logger LOG = LoggerFactory.getLogger(SessionService.class);

    // Session basiert
    private String loginName;

    private String uid = UUID.randomUUID().toString();
    
    public String getText() {
        return "session " + uid;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        LOG.info("Session Destroyed....");
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        LOG.info("Session Init....");
    }

       
}
