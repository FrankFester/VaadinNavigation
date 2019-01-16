/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author fester
 */
@Component
@VaadinSessionScope
public class UIService {

    private String pageTitle;
    private String loginName;

    @Value("${info.build.description}")
    private String appName;
    
    @Autowired
    ConfirmUtil confirm;
    
    private boolean hasChanges = false;

    /**
     * routeTo
     *
     * @param bee
     * @param c
     */
    public static void routeTo(BeforeEnterEvent bee, Class c) {
        bee.rerouteTo(c);
        UI.getCurrent().navigate(c);
    }

    public void anmelden(String name) {
        this.setLoginName(name);
        UI.getCurrent().navigate(MainView.class);
    }

    /**
     * abmeldenMitDialog
     * Zeigt einen Abmeldedialog Es wird das übergebene Command bei Klicken auf
     * "JA" ausgeführt.
     *
     * @param navClass Navigiert nach dem OK zur angebenenen Klasse/Seite
     */
    public void abmeldenMitDialog(Class navClass) {
        UI ui = UI.getCurrent().getUI().get();
        confirm.zeigeAbmeldeDialog(null, () ->{ 
            abmelden(ui, navClass);
        });
    }
    /**
     * Abmelden ohne Dialog
     * @param navClass 
     */
    public void abmelden(UI ui, Class navClass) {
        ui.navigate(navClass);
        ui.getSession().close();
    }

    
    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAppName() {
        return appName;
    }

    public boolean hasChanges() {
        return hasChanges;
    }

    public void setHasChanges(boolean hasChanges) {
        this.hasChanges = hasChanges;
    }
        
}
