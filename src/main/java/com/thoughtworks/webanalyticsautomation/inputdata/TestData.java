package com.thoughtworks.webanalyticsautomation.inputdata;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static com.thoughtworks.webanalyticsautomation.common.FileUtils.deserializeSectionsFromFile;

public class TestData extends CONFIG implements Serializable {

    static {
        logger = Logger.getLogger(TestData.class.getName());
    }

    private static final HashMap<String, ArrayList<Section>> loadedSections = new HashMap <String, ArrayList<Section>>();
    private final ArrayList <Section> sectionsLoadedFromFile = new ArrayList<Section>();

    public static ArrayList<Section> getSectionsFor(String absoluteFilePath, String actionName) {
        logger.info ("Loading input data file: " + absoluteFilePath);
        loadFile(absoluteFilePath);
        ArrayList<Section> subsetList = getExpectedSectionsForActionNameFromLoadedSections(absoluteFilePath, actionName);
        if (!CONFIG.isKEEP_LOADED_INPUT_FILE_IN_MEMORY()) {
            logger.info("Removing input data file " + absoluteFilePath + " from memory");
            loadedSections.remove(absoluteFilePath);
        }
        return subsetList;
    }

    private static ArrayList<Section> getExpectedSectionsForActionNameFromLoadedSections(String absoluteFilePath, String actionName) {
        ArrayList<Section> subsetList = new ArrayList<Section>();
        for (Section section: loadedSections.get(absoluteFilePath)) {
            if (section.hasAction(actionName)) {
                subsetList.add(section);
            }
        }
        return subsetList;
    }

    public ArrayList<Section> getSectionsLoadedFromFile() {
        return sectionsLoadedFromFile;
    }

    private static void loadFile(String absoluteFilePath) {
        if (isFileLoaded(absoluteFilePath)) {
            logger.info ("Input file is already loaded in memory");
        }
        else {
            logger.info ("Loading Input data file ...");
            loadedSections.put(absoluteFilePath, deserializeSectionsFromFile(absoluteFilePath, configureXStream()));
        }
    }

    static boolean isFileLoaded(String absoluteFilePath) {
        boolean isTestDataFileLoaded = loadedSections.containsKey(absoluteFilePath); 
        logger.debug (absoluteFilePath + " file loaded: " + isTestDataFileLoaded);
        return isTestDataFileLoaded;
    }

    private static XStream configureXStream() {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("Sections", TestData.class);
        xStream.omitField(TestData.class, "loadedSections");
        xStream.addImplicitCollection(TestData.class, "sectionsLoadedFromFile", "Section", Section.class);
        xStream = Section.configurePageLayoutXStream(xStream);
        return xStream;
    }
}