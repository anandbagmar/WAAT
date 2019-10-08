package com.anandbagmar.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.anandbagmar.webanalyticsautomation.Engine;
import com.anandbagmar.webanalyticsautomation.Result;
import com.anandbagmar.webanalyticsautomation.plugins.WebAnalyticTool;
import com.anandbagmar.webanalyticsautomation.inputdata.InputFileType;
import org.apache.log4j.Logger;

public class TestBase {
    protected final Logger logger = Logger.getLogger(getClass());
    protected Engine engine;
    protected WebAnalyticTool webAnalyticTool;
    protected final InputFileType inputFileType = InputFileType.XML;
    protected boolean keepLoadedFileInMemory = true;
    protected final String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[] {"resources", "log4j.properties"});
    protected String inputDataFileName = Utils.getAbsolutePath(new String[] {"src", "test", "sampledata", "TestData.xml"});


    protected void logVerificationErrors(Result verificationResult) {
        logger.info("Verification ERRORS:");
        logger.info (verificationResult.getListOfErrors());
//        String errors = "";
//        if (verificationResult.getListOfErrors().size()>0){
//            for (String error: verificationResult.getListOfErrors()) {
//                logger.info (error);
//            }
//        }
    }
}