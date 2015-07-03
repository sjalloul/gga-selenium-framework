package com.ggasoftware.uitest.apiAccessors.selenium;

import com.ggasoftware.uitest.apiAccessors.IAPIAvatar;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;
import static com.ggasoftware.uitest.utils.common.LinqUtils.select;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class SeleniumAvatar/* implements IAPIAvatar<WebElementAvatar>, WebDriver */{
    private MapArray<String, WebDriver> drivers = new MapArray<>();
    public void registerDriver(WebDriver driver) throws Exception {
        registerDriver("Driver" + drivers.size() + 1, driver);
    }
    public void registerDriver(String driverName, WebDriver driver) throws Exception {
        if (!drivers.add(driverName, driver))
            throw asserter.exception(format("Can't register Webdriver '%s'. Driver with same name already registered", driverName));
        currentDriverName = driverName;
    }
    public WebDriver useDriver(String driverName) throws Exception {
        WebDriver resultDriver = drivers.get(driverName);
        if (resultDriver != null)
            return resultDriver;
        else throw asserter.exception(format("Can't use Webdriver '%s'. This Driver name not registered", driverName));
    }
    public String currentDriverName = "";

    public void runApplication() {

    }

    public void closeApplication() {

    }

    public void get(String s) {

    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public List<WebElement> findElements(By by) {
        return null;
    }

    public WebElement findElement(By by) {
        return null;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {

    }

    public void quit() {

    }

    public Set<String> getWindowHandles() {
        return null;
    }

    public String getWindowHandle() {
        return null;
    }
}
