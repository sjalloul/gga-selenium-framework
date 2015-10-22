package com.epam.jditests.tests.composite;

import com.epam.jditests.InitTests;
import com.epam.jditests.entities.Contact;
import com.epam.jditests.enums.Preconditions;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Form;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jditests.entities.Contact.DEFAULT_CONTACT;
import static com.epam.jditests.enums.Buttons.SUMBIT;
import static com.epam.jditests.enums.Preconditions.CONTACT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.contactFormPage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.checkResult;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.getJSExecutor;
import static org.testng.Assert.assertEquals;

/**
 * Created by Dmitry_Lebedev1 on 10/15/2015.
 */
public class FormTestsTwoButton extends InitTests {
    private Preconditions _onPage = CONTACT_PAGE;
    JFuncTT<IElement, WebElement> gwe = (e) -> e.getWebElement();

    public Form<Contact> get() {
        return contactFormPage.contactFormTwoButtons;
    }

    @BeforeMethod
    public void before(final Method method) {
        isInState(_onPage, method);
    }

    @Test
    public void fillTest() {
        get().fill(DEFAULT_CONTACT);
        String src = "return [arguments[0].value, arguments[1].value, arguments[2].value];";
        List<String> res = (ArrayList<String>) exec(src);
        assertEquals(res, DEFAULT_CONTACT.toArrayList());
    }

    @Test
    public void submitTest() {
        get().submit(DEFAULT_CONTACT);
        checkResult(DEFAULT_CONTACT.toString());
    }

    @Test
    public void submitSpecButtonStringTest() {
        get().submit(DEFAULT_CONTACT, "submit");
        checkResult(DEFAULT_CONTACT.toString());
    }

    @Test
    public void submitSpecButtonEnumTest() throws Exception {
        get().submit(DEFAULT_CONTACT, SUMBIT);
        checkResult(DEFAULT_CONTACT.toString());
    }

    @Test
    public void submitStringTest() throws Exception {
        get().submit(DEFAULT_CONTACT.name);
        checkResult(String.format("Summary: 3\nName: %s", DEFAULT_CONTACT.name));
    }

    @Test
    public void verifyTest() {
        String src = String.format(
                "arguments[0].value = '%s'; arguments[1].value = '%s'; arguments[2].value = '%s';",
                DEFAULT_CONTACT.name, DEFAULT_CONTACT.lastName, DEFAULT_CONTACT.description);
        exec(src);
        get().verify(DEFAULT_CONTACT);
    }

    private Object exec(String src) {
        return getJSExecutor().executeScript(
                src,
                gwe.invoke(contactFormPage.name),
                gwe.invoke(contactFormPage.lastName),
                gwe.invoke(contactFormPage.description)
        );
    }
}