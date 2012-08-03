package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import java.util.ArrayList;

public class Utils {
    public final static String TEST_DATA_DELIMITER = "\\|\\|";
    private static String lineSeparator = System.getProperty("line.separator"); 
    private static String REQUIRES_WEBANALYTICS_TESTING = "requiresOmnitureTesting";
    private static String fileSeparator = System.getProperty("file.separator");
    private static String currentDirectory = System.getProperty("user.dir");

    public static String getAbsolutePath(String[] paths) {
        String combinedPaths = "";
        for (String path : paths) {
            combinedPaths += fileSeparator + path;
        }

        return currentDirectory + combinedPaths;
    }

    public static ArrayList<String> convertStringArrayToArrayList(String[] arrayElements) {
        ArrayList<String> convertedArrayList = new ArrayList<String>();
        for (String arrayElement : arrayElements) {
            convertedArrayList.add(arrayElement.trim());
        }
        return convertedArrayList;
    }

    public static String getThreadLocalID() {
        return REQUIRES_WEBANALYTICS_TESTING + Thread.currentThread().getId();
    }

    public static String getLineSeparator() {
        return lineSeparator;
    }
}