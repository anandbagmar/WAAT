package com.thoughtworks.webanalyticsautomation.inputdata;

import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;

import static com.thoughtworks.webanalyticsautomation.common.Utils.convertStringArrayToArrayList;

public class Section {
    private ArrayList<String> loadedKeyList = new ArrayList<String>();
    private ArrayList<String> loadedTagList = new ArrayList<String>();
    private String actionName;
    private String keyList;
    private String tagList;

    public boolean hasAction(String actionName) {
        return (this.actionName.equals(actionName));
    }

    public static XStream configurePageLayoutXStream(XStream xStream) {
        xStream.useAttributeFor(Section.class, "actionName");
        xStream.useAttributeFor(Section.class, "keyList");
        xStream.useAttributeFor(Section.class, "tagList");
        xStream.omitField(Section.class, "loadedKeyList");
        xStream.omitField(Section.class, "loadedTagList");
        return xStream;        
    }

    public ArrayList<String> getLoadedKeyList() {
        return loadedKeyList;
    }

    public ArrayList<String> getLoadedTagList() {
        return loadedTagList;
    }

    public void setup() {
        loadedKeyList = convertStringArrayToArrayList(keyList.split(Utils.TEST_DATA_DELIMITER));
        loadedTagList = convertStringArrayToArrayList(tagList.split(Utils.TEST_DATA_DELIMITER));
    }
}
