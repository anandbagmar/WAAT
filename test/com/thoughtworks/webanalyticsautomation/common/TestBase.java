package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;

import static com.thoughtworks.webanalyticsautomation.common.Utils.currentDirectory;
import static com.thoughtworks.webanalyticsautomation.common.Utils.fileSeparator;

public class TestBase {
    protected final Logger logger = Logger.getLogger(getClass());
    protected Engine engine;
    protected WebAnalyticTool webAnalyticTool;
    protected final InputFileType inputFileType = InputFileType.XML;
    protected boolean keepLoadedFileInMemory = true;
    protected final String log4jPropertiesAbsoluteFilePath = currentDirectory() + fileSeparator() + "resources"  + fileSeparator() + "log4j.properties";
    protected String inputDataFileName = currentDirectory() + fileSeparator() + "test"  + fileSeparator() + "sampledata"  + fileSeparator() + "TestData.xml";

    protected void logVerificationErrors(Result verificationResult) {
        if (verificationResult.getListOfErrors().size()>0){
            for (String error: verificationResult.getListOfErrors()) {
                logger.info (error);
            }
        }
    }
}