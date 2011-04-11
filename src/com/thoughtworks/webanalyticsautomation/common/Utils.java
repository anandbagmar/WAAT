package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import java.util.ArrayList;

public class Utils {
    public final static String TEST_DATA_DELIMITER = "\\|\\|";
    private static String lineSeparator = System.getProperty("line.separator"); 
    private static String REQUIRES_WEBANALYTICS_TESTING = "requiresOmnitureTesting";
    private static String fileSeparator = System.getProperty("file.separator");
    private static String currentDirectory = System.getProperty("user.dir");

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

    public static String fileSeparator() {
        return fileSeparator;
    }

    public static String currentDirectory() {
        return currentDirectory;
    }
}