/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kzvn.vaadinnavi.ui.layout;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;

import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import de.kzvn.vaadinnavi.ui.view.LoginView;
import de.kzvn.vaadinnavi.ui.view.UIService;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import de.kzvn.vaadinnavi.ui.view.ConfirmUtil;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author fester
 */
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
// @Theme(value = Material.class, variant = Material.LIGHT)
@PWA(name = "VaadinNavigation - Example", shortName = "VaadinNavigation")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Push(transport = Transport.LONG_POLLING)
public class MainLayout extends Div implements RouterLayout, BeforeEnterObserver, AfterNavigationObserver , PageConfigurator , BeforeLeaveObserver {

    private static final Logger LOG = LoggerFactory.getLogger(MainLayout.class);

    private final static String APPLICATION_BACKGROUND_COLOR = "#F4F4F4";

    private UIService uiService;
    private ConfirmUtil confirm;
    private Label userName;
    private Button headerOffButton;
    private HorizontalLayout userHl;

    //Component to delegate content through.
    private Div content = new Div();
    private HorizontalLayout headerLogo;
    private HorizontalLayout headerUser;
    private ProgressBar progressBar;

    public MainLayout(@Autowired UIService service, @Autowired ConfirmUtil confirm) {
        this.uiService = service;
        this.confirm = confirm;
        init();
    }

    private void init() {
        progressBar = new ProgressBar(1, 100, 100);
        progressBar.setId("default-progress-bar");
        progressBar.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
        // progressBar.setIndeterminate(true);        
        progressBar.setHeight("1px");
        progressBar.getElement().getStyle().set("margin-top","-4px");
        headerLogo = getKZVNHeaderLogo();
        headerUser = getKZVNHeaderUser();
        headerOffButton = getHeaderOnOffButton();
        HorizontalLayout topLayout = new HorizontalLayout(headerLogo, headerUser, headerOffButton);
        topLayout.getElement().getStyle().set("border-bottom", "1px solid #E8E8E8");
        add(topLayout,progressBar,
                content);
        // Background color - angepasst an die Farbe vom Logo
        UI.getCurrent().getElement().getStyle().set("background-color", APPLICATION_BACKGROUND_COLOR);
    }
    
    private HorizontalLayout getKZVNHeaderLogo() {
        Image img = new Image("/kzvn-logo.png", "");
        img.setWidth("103px");
        img.setHeight("48px");
        img.getElement().getStyle().set("margin-left", "24px");
        H2 anwendungsName = new H2(this.uiService.getAppName()); //this.appName
        anwendungsName.getElement().getStyle().set("margin-top", "8px");
        return new HorizontalLayout(img, anwendungsName, getSystemLabel());
    }

    private HorizontalLayout getKZVNHeaderUser() {
        Icon logOffIcon = new Icon(VaadinIcon.SIGN_OUT);
        Button logOffButton = new Button(logOffIcon, e -> {
            this.uiService.abmeldenMitDialog(LoginView.class);
        });
        logOffButton.getElement().setAttribute("title", "Von der Anwendung abmelden.");
        Icon userIcon = new Icon(VaadinIcon.USER);
        String name = (this.uiService == null) ? "" : this.uiService.getLoginName();
        this.userName = new Label(name);
        this.userName.getElement().setAttribute("id", "username");
        userName.getElement().getStyle().set("margin-top", "10px");
        logOffButton.getElement().getStyle().set("margin-top", "10px");
        userIcon.getElement().getStyle().set("margin-top", "10px");
        userHl = new HorizontalLayout(userIcon, userName, logOffButton);
        // Info nach ganz rechts 
        userHl.getElement().getStyle().set("margin-left", "auto");
        return userHl;
    }

    private H6 getSystemLabel() {
        H6 systemLabel = new H6("DEV");
        systemLabel.getElement().getStyle().set("margin-top", "24px");
        return systemLabel;
    }

    /**
     * getHeaderOnOffButton
     *
     * @return
     */
    private Button getHeaderOnOffButton() {
        Icon headerOffIcon = new Icon(VaadinIcon.CHEVRON_UP_SMALL);
        this.headerOffButton = new Button("", headerOffIcon, e -> {
            if (this.headerLogo.isVisible()) {
                this.headerOffButton.setIcon(new Icon(VaadinIcon.CHEVRON_DOWN_SMALL));
                setHeaderVisible(false);
            } else {
                this.headerOffButton.setIcon(new Icon(VaadinIcon.CHEVRON_UP_SMALL));
                setHeaderVisible(true);
            }
        });
        Element element = this.headerOffButton.getElement();
        element.setAttribute("title", "Kopfzeile ein/ausblenden");
        element.getStyle().set("right", "0");
        element.getStyle().set("position", "absolute");
        element.getStyle().set("top", "54px");
        element.getStyle().set("max-width", "48px");
        element.getStyle().set("min-width", "48px");
        return this.headerOffButton;
    }

    private void setHeaderVisible(boolean visible) {
        this.headerLogo.setVisible(visible);
        if (isLoggedIn()) {
            this.headerUser.setVisible(visible);
        } else {
            this.headerUser.setVisible(false);
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement hasElement) {
        Objects.requireNonNull(hasElement);
        Objects.requireNonNull(hasElement.getElement());
        content.removeAll();
        String pageTitle = this.uiService.getPageTitle();
        if (pageTitle != null && !pageTitle.isEmpty()) {
            content.getElement().appendChild(new H2(pageTitle).getElement());
        }
        content.getElement().appendChild(hasElement.getElement());
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }

    /**
     * Called when the location changes so the menu can be updated based on the
     * currently shown view.
     *
     * @param event before navigation change event
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String loginPath = event.getLocation().getPath();
        // Angemeldet ?
        if (!isLoggedIn()) {
            // Routing auf Startseite, falls noch kein Login erfolgte, path = "ui" , "main" etc.
            if (!loginPath.equals( "ui" )) {
                UIService.routeTo(event, LoginView.class);
            }
            // this.headerUser.setVisible(false);
        } else {
//            this.userName.setText(this.uiService.getLoginName());
//            if (this.headerLogo.isVisible() && !loginPath.equals( "ui" )) {
//                this.headerUser.setVisible(true);
//            }
            /*            userHl.getChildren().forEach(c -> {
                if (c.getId().isPresent()) {
                    LOG.info("Element: {}", c.getId().get());
                    if (c.getId().get().equals("username")) {
                        if (this.uiService != null) {
                            c.getElement().setText(this.uiService.getLoginName());
                        }
                    }
                }
            });
             */
        }
    }
    
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String loginPath = event.getLocation().getPath();
        if (!isLoggedIn()) {
            this.headerUser.setVisible(false);
        } else {
            this.userName.setText(this.uiService.getLoginName());
            if (this.headerLogo.isVisible() && !loginPath.equals( "ui" )) {
                this.headerUser.setVisible(true);
            }
        }
    }

    private boolean isLoggedIn() {
        return !(this.uiService.getLoginName() == null || this.uiService.getLoginName().isEmpty());
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        LOG.info("Before LEAVE....");
        if (this.uiService.hasChanges()) {
            ContinueNavigationAction action = event.postpone();
            confirm.zeigeSeiteVerlassenDialog(null, () -> {
                // ggf. Datenzurücksetzen oder merken...
                uiService.setHasChanges(false);
                // Route weitergehen
                action.proceed();
            });        
        }
    }    
        
    // SESSION TIMEOUT PROGRESSBAR
    private double progress = 100;

    @Scheduled(fixedRate=12000)
    // @Scheduled(cron = "*/1 * * * * *")
    public void progressBarAendern() {
        UI ui = (this.getUI().isPresent()) ? this.getUI().get() : null;
        if (this.progressBar != null && ui != null && ui.getSession() != null) {
            progress = this.progressBar.getValue() - 1;

            if (progress < this.progressBar.getMin()) {
                progress = this.progressBar.getMax();
                // AutoLogoff
                uiService.setHasChanges(false);
                ui.getSession().accessSynchronously(() -> {
                    uiService.abmelden(ui, LoginView.class);
                   // ui.getSession().close();
                });
            }
            ui.getSession().access(() -> {
            this.progressBar.getElement().setAttribute("value", ""+progress);
                this.progressBar.setValue(progress);
         //       ui.push();
            });
            LOG.info("Progress {}", progress);
        }
    }

    private void resetProgressBar() {
        UI ui = (this.getUI().isPresent()) ? this.getUI().get() : null;
        if (this.progressBar != null && ui != null && ui.getSession() != null) {
            ui.getSession().access(() -> {
                this.progressBar.setValue(100);
            });
        }
    }
    
}
