package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.inputdata.TestData;
import com.thoughtworks.xstream.XStream;

import java.io.*;
import java.util.ArrayList;

public class FileUtils implements Serializable {
    public static ArrayList<Section> deserializeSectionsFromFile(String absoluteFilePath, XStream xStream) {
        TestData deserializedTestData = (TestData) xStream.fromXML(getXMLContentFromFile(absoluteFilePath));

        for (Object sectionObject : deserializedTestData.getSectionsLoadedFromFile()) {
            Section section = (Section) sectionObject;
            section.setup();
        }
        return deserializedTestData.getSectionsLoadedFromFile();
    }

    private static String getXMLContentFromFile(String absoluteFilePath) {
        byte[] buffer = new byte[(int) new File(absoluteFilePath).length()];
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(absoluteFilePath));
            inputStream.read(buffer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return new String(buffer);
    }
}
