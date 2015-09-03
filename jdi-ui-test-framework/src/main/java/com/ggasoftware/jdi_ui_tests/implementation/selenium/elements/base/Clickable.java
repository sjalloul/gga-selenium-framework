package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IClickable;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Clickable extends Element implements IClickable {
    public Clickable() { }
    public Clickable(By byLocator) { super(byLocator); }

    protected void clickJSAction() { jsExecutor().executeScript("arguments[0].click();", getWebElement()); }
    protected void clickAction() { getWebElement().click(); }
    public final void click() { actions.click(this::clickAction); }

    public void clickByXY(int x, int y) {
        invoker.doJAction(format("Click on webElement with coordinates (x,y) = (%s, %s)", x, y),
                () -> new Actions(getDriver())
                        .moveToElement(getWebElement(), x, y).click().build().perform());
    }
}
