package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
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
    private static String LOG4J_PROPERTIES_ABSOLUTE_FILE_PATH = Utils.getAbsolutePath(new String[]{"resources", "log4j.properties"});

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

    protected static void setUpConfig(WebAnalyticTool webAnalyticTool, InputFileType inputFileType, boolean keepLoadedInputFileInMemory, String log4jPropertiesAbsoluteFilePath) {
        WEB_ANALYTIC_TOOL = webAnalyticTool;
        INPUT_FILE_TYPE = inputFileType;
        KEEP_LOADED_INPUT_FILE_IN_MEMORY = keepLoadedInputFileInMemory;
        if (null != log4jPropertiesAbsoluteFilePath) {
            LOG4J_PROPERTIES_ABSOLUTE_FILE_PATH = log4jPropertiesAbsoluteFilePath;
        }
        PropertyConfigurator.configure(LOG4J_PROPERTIES_ABSOLUTE_FILE_PATH);
    }
}