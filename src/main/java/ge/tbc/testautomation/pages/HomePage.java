package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.CookieComponent;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.components.SideMenuComponent;

public class HomePage {

    public final CookieComponent cookie;
    public final HeaderComponent header;
    public final SideMenuComponent sideMenu;

    public HomePage(Page page) {
        cookie = new CookieComponent(page);
        header = new HeaderComponent(page);
        sideMenu = new SideMenuComponent(page);
    }
}