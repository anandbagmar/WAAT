package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;

public class Controller extends CONFIG {
    private static Engine engine;

    static {
        logger = Logger.getLogger(Controller.class.getName());
    }

    public static Engine getInstance (String webAnalyticTool,
                                      String inputFileType,
                                      boolean keepLoadedInputFileInMemory,
                                      String outputLogDirectory) throws IllegalArgumentException {
        return getEngineInstance(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, outputLogDirectory);
    }

    public static Engine getInstance () throws IllegalArgumentException {
        return getEngineInstance(WebAnalyticTool.OMNITURE.name(), InputFileType.XML.name(), true, System.getProperty("user.directory"));
    }

    private static Engine getEngineInstance(String webAnalyticTool, String inputFileType, boolean keepLoadedInputFileInMemory, String outputLogDirectory) {
        if (null != engine) {
            logger.info("Returning existing Engine reference");
            return engine;
        }
        else {
            engine = createNewEngine(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, outputLogDirectory);
            return engine;
        }
    }

    private static Engine createNewEngine(String webAnalyticTool,
                                          String inputFileType,
                                          boolean keepLoadedInputFileInMemory,
                                          String outputLogDirectory) {
        setUpConfig(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, outputLogDirectory);
        logger.info("Creating new Engine reference");
        return new Engine ();
    }
}
