/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.kzvn.vaadinnavi.ui.layout.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author fester
 */
@PageTitle("Übersicht")
@Route(value = "main", layout = MainLayout.class, absolute = false)
public class MainView extends Div implements BeforeEnterObserver {

    private static final Logger LOG = LoggerFactory.getLogger(MainView.class);

    private UIService uiService;
    private ConfirmUtil confirm;

    public MainView(@Autowired UIService uiService, @Autowired ConfirmUtil confirm) {
        this.uiService = uiService;
        this.confirm = confirm;
        init();
    }

    private void init() {
        // confirmDialog = new ConfirmDialog();
        Button btn = new Button("zurueck", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        Button btn2 = new Button("changes", e -> {
            this.uiService.setHasChanges(true);
        });
        add(btn, btn2);
        // getElement().appendChild(confirmDialog.getElement());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.uiService.setPageTitle("Berichtsjahr Übersicht");
        //
    } 

}
