package de.kzvn.vaadinnavi.ui.view;

/**
 *
 * @author fester
 */

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Server-side component for the confirm element.
 *
 * @author Frank Fester
 */
@Tag("kzvn-confirm-dialog")
public class ConfirmDialog extends Component {

    private static final long serialVersionUID = 1L;

    private final Dialog dialog;

    public ConfirmDialog() {
        this.dialog = new Dialog();
    }       

    /**
     * Konstruktor für ConfirmDialog
     * 
     * @param closeOnEsc
     * @param closeOnClick
     * @param title
     * @param message
     * @param cancel
     * @param clickListenerCancel
     * @param ok
     * @param clickListenerOk 
     */
    public void prepare(boolean closeOnEsc,boolean closeOnClick,
            String title, String message,
            String cancel, ComponentEventListener<ClickEvent<Button>> clickListenerCancel,
            String ok, ComponentEventListener<ClickEvent<Button>> clickListenerOk) {

        dialog.add(new H3(title));
        dialog.setCloseOnEsc(closeOnEsc);
        dialog.setCloseOnOutsideClick(closeOnClick);
        dialog.add(new Span(new H5(message)));

        Button confirmButton = new Button(ok, clickListenerOk);
        Button cancelButton = new Button(cancel, clickListenerCancel);
        cancelButton.setIcon(new Icon(VaadinIcon.CLOSE));
        confirmButton.setIcon(new Icon(VaadinIcon.CHECK));
        confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        dialog.add(new Span(new HorizontalLayout(confirmButton, cancelButton)));
    }

    /**
     * Öffnen des Dialogs
     */
    public void open() {
        dialog.open();
    }

    /**
     * Schließen des Dialogs
     */
    public void close() {
        dialog.close();
    }
}