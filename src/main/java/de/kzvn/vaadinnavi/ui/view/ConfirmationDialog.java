package de.kzvn.vaadinnavi.ui.view;

/**
 *
 * @author fester
 */
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

/**
 * Server-side component for the confirm element.
 *
 * @author Frank Fester
 */
@Tag("kzvn-confirmation-dialog")
public class ConfirmationDialog extends Component {

    private static final long serialVersionUID = 1L;

    private final Dialog dialog;

    private final H3 titleField = new H3();
    private final Div messageLabel = new Div();
    private final Div extraMessageLabel = new Div();
    private final Button confirmButton = new Button();
    private final Button cancelButton = new Button("Cancel");
    private Registration registrationForConfirm;
    private Registration registrationForCancel;

    private static final Runnable NO_OP = () -> {
    };

    public ConfirmationDialog() {
        this.dialog = new Dialog();
        this.dialog.setCloseOnEsc(true);
        this.dialog.setCloseOnOutsideClick(false);
        confirmButton.addClickListener(e -> this.dialog.close());
        confirmButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        confirmButton.setAutofocus(true);
        cancelButton.addClickListener(e -> this.dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout buttonBar = new HorizontalLayout(confirmButton, cancelButton);
        buttonBar.setClassName("buttons confirm-buttons");
        Div labels = new Div(messageLabel, extraMessageLabel, new Div(new Label("")));
        labels.setClassName("confirm-text");
        titleField.setClassName("confirm-title");
        this.dialog.add(titleField, labels, buttonBar);
    }

    public void open(String title, String message, String additionalMessage, String actionName, String cancelName, boolean isDisruptive,
            Runnable confirmHandler, Runnable cancelHandler) {
        titleField.setText(title);
        messageLabel.setText(message);
        extraMessageLabel.setText(additionalMessage);
        confirmButton.setText(actionName);
        cancelButton.setText(cancelName);

        Runnable cancelAction = cancelHandler == null ? NO_OP : cancelHandler;
        Runnable confirmAction = confirmHandler == null ? NO_OP : confirmHandler;

        if (registrationForConfirm != null) {
            registrationForConfirm.remove();
        }
        registrationForConfirm = confirmButton.addClickListener(e -> confirmAction.run());

        if (registrationForCancel != null) {
            registrationForCancel.remove();
        }
        registrationForCancel = cancelButton.addClickListener(e -> cancelAction.run());

        this.dialog.addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
                cancelAction.run();
            }
        });

        confirmButton.removeThemeVariants(ButtonVariant.LUMO_ERROR);
        if (isDisruptive) {
            confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        }
        this.dialog.open();
    }
}
