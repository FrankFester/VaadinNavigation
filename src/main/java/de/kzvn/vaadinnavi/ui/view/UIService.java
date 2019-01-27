package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author fester
 */
@Component
@UIScope
public class UIService {
   
    @Value("${info.build.description}")
    private String appName;
    
    @Autowired
    SessionService sessionService;
    // UI basiert
    private String pageTitle;

    
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
        this.sessionService.setLoginName(name);
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
        zeigeAbmeldeDialog(null, () ->{ 
            abmelden(navClass);
        });
    }
    /**
     * Abmelden ohne Dialog
     * @param navClass 
     */
    public void abmelden(Class navClass) {
        UI ui = UI.getCurrent().getUI().get();
        ui.navigate(navClass);
        ui.getSession().close();
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
    
   public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public void setLoginName(String loginName) {
        this.sessionService.setLoginName(loginName);
    }

    public String getPageTitle() {
        return this.pageTitle;
    }

    public String getLoginName() {
        return this.sessionService.getLoginName();
    }
        
  
     private ConfirmationDialog dialog;

    /**
     * Zeigt einen Abmeldedialog
     * Es wird das übergebene Command bei Klicken auf "JA" ausgeführt.
     * @param cmd 
     */
    public void zeigeAbmeldeDialog(Runnable neinCmd, Runnable jaCmd) {
        this.dialog = new ConfirmationDialog();
        this.dialog.open("Warnung", "Wollen Sie sich wirklich abmelden?", "Mit Bestätigung melden Sie sich von der Anwendung ab.", 
                "Abmelden", "Nein", true, jaCmd, neinCmd);
    }
    
    /**
     * Zeigt einen Verlassendialog
     * Es wird das übergebene Command bei Klicken auf "JA" ausgeführt.
     * @param cmd 
     */
    public void zeigeSeiteVerlassenDialog(Runnable neinCmd, Runnable jaCmd) {
        this.dialog = new ConfirmationDialog();
        this.dialog.open("Warnung", "Wollen Sie die Seite wirklich verlassen?", "Mit Bestätigung verlassen Sie die aktuelle Seite.", 
                "Verlassen", "Nein", true, jaCmd, neinCmd);
    }

}
