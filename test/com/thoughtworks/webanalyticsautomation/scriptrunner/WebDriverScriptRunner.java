package com.thoughtworks.webanalyticsautomation.scriptrunner;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Jan 4, 2011
 * Time: 10:38:37 AM
 */

public class WebDriverScriptRunner implements IScriptRunner {
    protected WebDriver webDriverInstance;
    private Logger logger = Logger.getLogger(getClass());

    public WebDriverScriptRunner(WebDriver webDriverInstance) {
        this.webDriverInstance = webDriverInstance;
    }

    public String getHTMLSourceByExecutingScript(final String OMNITURE_DEBUGGER_URL, String OMNITURE_DEBUGGER_WINDOW_TITLE, String OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX) {
        String htmlSource = null;
        String parentWindowHandle = webDriverInstance.getWindowHandle();
        logger.info ("parentWindowHandle = " + parentWindowHandle);
        logger.info ("Opening Omniture Debugger: " + OMNITURE_DEBUGGER_URL);
        ((JavascriptExecutor)webDriverInstance).executeScript(OMNITURE_DEBUGGER_URL);
        try{
            boolean windowFound = false;
            while(!windowFound){
                Set<String> windowHandles = webDriverInstance.getWindowHandles();
                for (String windowHandle: windowHandles){
                    logger.info ("windowHandle = " + windowHandle);
                    if (!windowHandle.equalsIgnoreCase(parentWindowHandle)) {
                        webDriverInstance = webDriverInstance.switchTo().window(windowHandle);
                        WebElement element = webDriverInstance.findElement(By.name(OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX));
                        if (!element.isSelected()) {
                            logger.info ("checkbox not selected. clicking on it.");
                            element.click();
                            while (!element.isSelected()) {
                                logger.info ("checkbox status: " + element.isSelected());
                                Thread.sleep (3000);
                            }
                        }
                        Thread.sleep (3000);
                        htmlSource = webDriverInstance.getPageSource();
                        webDriverInstance.close();//child window closing
                        windowFound = true;
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        webDriverInstance.switchTo().window(parentWindowHandle);
        webDriverInstance.close();
        logger.info ("WebDriver omniture debugger page source: \n" + htmlSource);
        return htmlSource;
    }
}
