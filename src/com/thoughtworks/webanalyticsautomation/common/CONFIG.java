package com.thoughtworks.webanalyticsautomation.common;

import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;

public class CONFIG {
    protected static Logger logger = Logger.getLogger(CONFIG.class.getName());

    private static WebAnalyticTool WEB_ANALYTIC_TOOL = null;
    private static InputFileType INPUT_FILE_TYPE = null;
    private static boolean KEEP_LOADED_INPUT_FILE_IN_MEMORY = true;
    private static String OUTPUT_LOG_DIRECTORY = null;
    
    public static WebAnalyticTool getWEB_ANALYTIC_TOOL() {
        return WEB_ANALYTIC_TOOL;
    }

    public static InputFileType getINPUT_FILE_TYPE() {
        return INPUT_FILE_TYPE;
    }

    public static boolean isKEEP_LOADED_INPUT_FILE_IN_MEMORY() {
        return KEEP_LOADED_INPUT_FILE_IN_MEMORY;
    }

    public static String getOUTPUT_LOG_DIRECTORY() {
        return OUTPUT_LOG_DIRECTORY;
    }

    protected static void setUpConfig(String webAnalyticTool, String inputFileType, boolean keepLoadedInputFileInMemory, String outputLogDirectory) {
        WEB_ANALYTIC_TOOL = WebAnalyticTool.valueOf(webAnalyticTool.toUpperCase());
        INPUT_FILE_TYPE = InputFileType.valueOf(inputFileType.toUpperCase());
        KEEP_LOADED_INPUT_FILE_IN_MEMORY = keepLoadedInputFileInMemory;
        OUTPUT_LOG_DIRECTORY = outputLogDirectory;
    }
}
