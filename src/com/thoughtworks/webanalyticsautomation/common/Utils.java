package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import java.util.ArrayList;

public class Utils {
    public final static String TEST_DATA_DELIMITER = "\\|\\|";
    private static String REQUIRES_WEBANALYTICS_TESTING = "requiresOmnitureTesting";
    
    public static ArrayList<String> convertStringArrayToArrayList(String[] arrayElements) {
        ArrayList<String> convertedArrayList = new ArrayList<String>();
        for (String arrayElement : arrayElements) {
            convertedArrayList.add(arrayElement.trim());
        }
/*

        for(int i=0;i<arrayElements.length;i++){
               convertedArrayList.add(arrayElements[i].trim());
        }
*/
        return convertedArrayList;
    }

    public static String getThreadLocalID() {
        return REQUIRES_WEBANALYTICS_TESTING + Thread.currentThread().getId();
    }
}
