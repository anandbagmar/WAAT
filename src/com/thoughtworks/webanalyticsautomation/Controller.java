package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.PluginFactory;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;

public class Controller extends CONFIG {
    private static Engine engine;

    static {
        logger = Logger.getLogger(Controller.class.getName());
    }

    public static Engine getInstance(WebAnalyticTool webAnalyticTool,
                                     InputFileType inputFileType,
                                     boolean keepLoadedInputFileInMemory,
                                     String log4jPropertiesAbsoluteFilePath) throws IllegalArgumentException {
        return getEngineInstance(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, log4jPropertiesAbsoluteFilePath);
    }

    static Engine getInstance () throws IllegalArgumentException {
        return getEngineInstance(WebAnalyticTool.OMNITURE_DEBUGGER, InputFileType.XML, true, CONFIG.getLOG4J_PROPERTIES_ABSOLUTE_FILE_PATH());
    }

    /**
       Reset all state and objects initialized / used by WAAT
     */
    public static void reset() {
        logger.info ("Resetting Engine to null");
        engine = null;
        PluginFactory.reset();
    }

    private static Engine getEngineInstance(WebAnalyticTool webAnalyticTool, InputFileType inputFileType, boolean keepLoadedInputFileInMemory, String log4jPropertiesAbsoluteFilePath) {
        if (null != engine) {
            logger.info("Returning existing Engine reference");
            return engine;
        }
        else {
            engine = createNewEngine(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, log4jPropertiesAbsoluteFilePath);
            return engine;
        }
    }

    private static Engine createNewEngine(WebAnalyticTool webAnalyticTool,
                                          InputFileType inputFileType,
                                          boolean keepLoadedInputFileInMemory,
                                          String log4jPropertiesAbsoluteFilePath) {
        setUpConfig(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, log4jPropertiesAbsoluteFilePath);
        logger.info("Creating new Engine reference");
        return new Engine ();
    }
}