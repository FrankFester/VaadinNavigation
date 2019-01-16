/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

/**
 *
 * @author fester
 */
@Route("")
public class RootView extends Div {
    public RootView(){
        UI.getCurrent().navigate(LoginView.class);
    }    
}
