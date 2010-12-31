package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.Engine;
import org.apache.log4j.Logger;

public class TestBase {
    protected Logger logger = Logger.getLogger(getClass());
    protected Engine engine;
    protected String actionName;

    protected String webAnalyticTool = "omniture";
    protected String inputFileType = "xml";
    protected boolean keepLoadedFileInMemory = true;
    protected String log4jPropertiesAbsoluteFilePath = System.getProperty("user.dir") + "\\resources\\log4j.properties";

    protected String inputDataFileName = System.getProperty("user.dir") + "\\test\\sampledata\\OmnitureTestData.xml";
}
