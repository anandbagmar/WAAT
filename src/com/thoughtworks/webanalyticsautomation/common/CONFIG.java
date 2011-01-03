package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CONFIG {
    protected static Logger logger = Logger.getLogger(CONFIG.class.getName());
    private static WebAnalyticTool WEB_ANALYTIC_TOOL = null;
    private static InputFileType INPUT_FILE_TYPE = null;
    private static boolean KEEP_LOADED_INPUT_FILE_IN_MEMORY = true;
    private static String LOG4J_PROPERTIES_ABSOLUTE_FILE_PATH = System.getProperty("user.dir") + "\\resources\\log4j.properties";

    public static WebAnalyticTool getWEB_ANALYTIC_TOOL() {
        return WEB_ANALYTIC_TOOL;
    }

    public static InputFileType getINPUT_FILE_TYPE() {
        return INPUT_FILE_TYPE;
    }

    public static boolean isKEEP_LOADED_INPUT_FILE_IN_MEMORY() {
        return KEEP_LOADED_INPUT_FILE_IN_MEMORY;
    }

    public static String getLOG4J_PROPERTIES_ABSOLUTE_FILE_PATH() {
        return LOG4J_PROPERTIES_ABSOLUTE_FILE_PATH;
    }

    protected static void setUpConfig(String webAnalyticTool, String inputFileType, boolean keepLoadedInputFileInMemory, String log4jPropertiesAbsoluteFilePath) {
        WEB_ANALYTIC_TOOL = WebAnalyticTool.valueOf(webAnalyticTool.toUpperCase());
        INPUT_FILE_TYPE = InputFileType.valueOf(inputFileType.toUpperCase());
        KEEP_LOADED_INPUT_FILE_IN_MEMORY = keepLoadedInputFileInMemory;
        LOG4J_PROPERTIES_ABSOLUTE_FILE_PATH = log4jPropertiesAbsoluteFilePath;
        PropertyConfigurator.configure(LOG4J_PROPERTIES_ABSOLUTE_FILE_PATH);
    }
}