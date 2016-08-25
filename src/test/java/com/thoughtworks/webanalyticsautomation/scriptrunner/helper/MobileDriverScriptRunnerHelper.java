package com.thoughtworks.webanalyticsautomation.scriptrunner.helper;

import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by gshilpa on 8/25/16.
 */
public class MobileDriverScriptRunnerHelper extends ScriptRunnerHelper{

    private  AppiumDriverLocalService service;
    private  AppiumServiceBuilder serBuilder;
    private  AndroidDriver<MobileElement> driverAndroid;
    FileInputStream input;
    Properties prop=new Properties();

    public MobileDriverScriptRunnerHelper(Logger logger, BROWSER browser, String baseURL) {
        super(logger,browser, baseURL);
    }

    @Override
    public void startDriver() {
    }

    private void instantiateChromeDriver(DesiredCapabilities capabilities) {
        driverAndroid =new AndroidDriver<>(service.getUrl(), capabilities);
        driverAndroid.get(BASE_URL);
    }

    @Override
    public void startDriverUsingProxy(Proxy proxy) {

        String mobilePropertyFilePath = Utils.getAbsolutePath(new String[] {"resources","mobileSetUp.properties"});
        try {

            input= new FileInputStream(mobilePropertyFilePath);
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Initializing the appium server at port 7000");
        serBuilder=new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")).usingPort(7000);
        service = serBuilder.build();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("Unable to start Appium node server");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        if(browser.equals(BROWSER.chrome))
        {
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, prop.getProperty("BROWSER_NAME"));
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("DEVICE_NAME"));
            capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("ignore-certificate-errors");
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            capabilities.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, 500000);
            capabilities.setCapability(CapabilityType.PROXY, proxy);
            instantiateChromeDriver(capabilities);
        }
        logger.info ("Mobile Driver started: " + browser.name());
        logger.info ("Page title: " + driverAndroid.getTitle());
    }

    @Override
    public void stopDriver() {
        if(null !=this.driverAndroid)
        driverAndroid.quit();
    }

    @Override
    public Object getDriverInstance() {
        if (null == driverAndroid) {
            logger.info("Driver is null");
        }
        return driverAndroid;
    }


}
