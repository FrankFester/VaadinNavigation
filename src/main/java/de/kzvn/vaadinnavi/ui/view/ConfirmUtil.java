/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.view;

import com.vaadin.flow.server.Command;
import java.io.Serializable;
import org.springframework.stereotype.Service;

/**
 *
 * @author fester
 */
@Service
public class ConfirmUtil {

    private ConfirmationDialog dialog = new ConfirmationDialog;

    /**
     * Zeigt einen Abmeldedialog
     * Es wird das übergebene Command bei Klicken auf "JA" ausgeführt.
     * @param cmd 
     */
    public void zeigeAbmeldeDialog(Command neinCmd, Command jaCmd) {
        this.dialog = erzeugeJaNeinDialog(false,false,"Warnung","Wollen Sie sich wirklich abmelden?",neinCmd,jaCmd);
        this.dialog.open();
    }
    
    /**
     * Zeigt einen Verlassendialog
     * Es wird das übergebene Command bei Klicken auf "JA" ausgeführt.
     * @param neinCmd
     * @param jaCmd
     * @param cmd 
     */
    public void zeigeSeiteVerlassenDialog(Command neinCmd, Command jaCmd) {
        this.dialog = erzeugeJaNeinDialog(false,false,"Warnung","Wollen Sie die Seite wirklich verlassen?",neinCmd,jaCmd);
        this.dialog.open();
    }
    
    private ConfirmationDialog erzeugeJaNeinDialog(boolean closeOnEsc,boolean closeOnClick,String title,String msg, Command neinCmd, Command jaCmd) {

        dialog.prepare(title, msg, "addMsg", "OK", false, "Item", () ->{
                    dialog.close();
        }, () ->{
                    dialog.close();
        
        });
                       
        return dialog;
    }
    
    
       protected final void openConfirmationDialog(String title, String message,
            String additionalMessage) {
        dialog.open(title, message, additionalMessage, "Delete",
                true, "", this::deleteConfirmed, null);
    }

    
    
       private void deleteConfirmed(T item) {
        System.out.println("Delete");
    }
}
