package com.thoughtworks.webanalyticsautomation.samples;

import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.Status;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.WebDriverScriptRunnerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class JsSnifferSampleTest extends TestBase {
    private Logger logger = Logger.getLogger(getClass());
    private Engine engine;
    private WebAnalyticTool webAnalyticTool = WebAnalyticTool.JS_SNIFFER;
    private InputFileType inputFileType = InputFileType.XML;
    private boolean keepLoadedFileInMemory = true;
    private String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[]{"resources", "log4j.properties"});
    private String inputDataFileName = Utils.getAbsolutePath(new String[]{"test", "sampledata", "TestData.xml"});
    private String actionName = "OpenAMirenaSecureSite_JsSniffer";
    private WebDriverScriptRunnerHelper webDriverScriptRunnerHelper;
    private WebDriver driverInstance;
    private static final String JS_FOR_MIRENA_SITE = "window.WAAT_URL=\"\";window.WebTrends.prototype.dcsTag=function(){if(document.cookie.indexOf(\"WTLOPTOUT=\")!=-1){return}var WT=this.WT;var DCS=this.DCS;var DCSext=this.DCSext;var i18n=this.i18n;var P=\"http\"+(window.location.protocol.indexOf('https:')==0?'s':'')+\"://\"+this.domain+(this.dcsid==\"\"?'':'/'+this.dcsid)+\"/dcs.gif?\";if(i18n){WT.dep=\"\"}for(var N in DCS){if(DCS[N]&&(typeof DCS[N]!=\"function\")){P+=this.dcsA(N,DCS[N])}}for(N in WT){if(WT[N]&&(typeof WT[N]!=\"function\")){P+=this.dcsA(\"WT.\"+N,WT[N])}}for(N in DCSext){if(DCSext[N]&&(typeof DCSext[N]!=\"function\")){if(i18n){WT.dep=(WT.dep.length==0)?N:(WT.dep+\";\"+N)}P+=this.dcsA(N,DCSext[N])}}if(i18n&&(WT.dep.length>0)){P+=this.dcsA(\"WT.dep\",WT.dep)}if(P.length>2048&&navigator.userAgent.indexOf('MSIE')>=0){P=P.substring(0,2040)+\"&WT.tu=1\"}this.dcsCreateImage(P);window.WAAT_URL=P;this.WT.ad=\"\"};_tag.dcsCustom=function(){}; _tag.DCS.dcscfg=\"1\"; _tag.dcsCollect();return window.WAAT_URL" ;


    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_JsSniffer_Webtrends_Selenium_Firefox() throws Exception {
        String baseURL = "https://www.mirena.com/en/home/index.php";
        String navigateToURL = "https://www.mirena.com/en/privacy_statement_user.php";

        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);

        startWebDriver(BROWSER.firefox, baseURL);
        driverInstance.get(navigateToURL);

        String url = extractUrlFromJavascript(JS_FOR_MIRENA_SITE);

        Result verificationResult = engine.verifyWebAnalyticsData(inputDataFileName, actionName, url);

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        Assert.assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");

    }

    private String extractUrlFromJavascript(String javascript) {
        String url = (String) ((JavascriptExecutor) driverInstance).executeScript(javascript);
        return url;
    }

    private void startWebDriver(BROWSER browser, String baseURL) {
        webDriverScriptRunnerHelper = new WebDriverScriptRunnerHelper(logger, browser, baseURL);
        webDriverScriptRunnerHelper.startDriver();
        driverInstance = (WebDriver) webDriverScriptRunnerHelper.getDriverInstance();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        webDriverScriptRunnerHelper.stopDriver();
    }
}

