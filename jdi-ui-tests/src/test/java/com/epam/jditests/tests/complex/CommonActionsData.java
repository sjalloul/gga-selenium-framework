package com.epam.jditests.tests.complex;

import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;

import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.ggasoftware.jdiuitest.web.testng.asserter.Assert.*;

/**
 * Created by Roman_Iovlev on 9/18/2015.
 */
public class CommonActionsData {
    public static final String noElementsMessage = "No elements selected. Override getSelectedAction or place locator to <select> tag";
    public static final long waitTimeOut = 1000;
    public static Timer timer;
    private static String _name = null;
    private static String _path = null;

    public static void checkActionThrowError(final JAction action, final String msg) {
        try {
            action.invoke();
        } catch (Exception | AssertionError ex) {
            assertContains(ex.getMessage(), msg);
            return;
        }
        throw exception("Exception not thrown");
    }

    public static long getTimePassed() {
        return timer.timePassedInMSec();
    }

    public static void runParallel(final JAction action) {
        new Thread() {
            @Override
            public void run() {
                timer = new Timer();
                Timer.sleep(waitTimeOut);
                action.invoke();
            }
        }.run();
    }

    private static void createFile() {
        try {
            File temp = File.createTempFile("tmp", ".tmp");
            _name = temp.getName();
            _path = temp.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFPath() {
        if (_path == null) {
            createFile();
        }
        return _path;
    }

    public static String getFName() {
        if (_name == null) {
            createFile();
        }
        return _name;
    }

    @Step
    public static void checkAction(final String text) {
        assertContains(actionsLog::getFirstText, text);
    }


    @Step
    public static void checkText(final JFuncT<String> func, String expected) {
        areEquals(func.invoke(), expected);
    }

    @Step
    public static void checkCalculate(final String text) {
        assertContains(metalsColorsPage.calculateText::getText, text);
    }

    @Step
    public static void checkResult(final String text) {
        assertContains(contactFormPage.result::getText, text);
    }

}
