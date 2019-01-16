/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.kzvn.vaadinnavi.ui.layout.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author fester
 */
@PageTitle("Login")
@Route(value = "ui", layout = MainLayout.class, absolute = false)
public class LoginView extends Div implements BeforeEnterObserver {

    private UIService uiService;
    
    private TextField name;
            
    public LoginView(@Autowired UIService uiService) {
        this.uiService = uiService;
        init();
    }
        
    private void init() {
        Button btn = new Button("Login", e -> {
            this.uiService.anmelden(name.getValue());
        });
        name = new TextField("Name");
                
        add(btn,name);
    }
    
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.uiService.setPageTitle("");
    }
    
}
