package com.thoughtworks.webanalyticsautomation.inputdata;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;

import static com.thoughtworks.webanalyticsautomation.common.Utils.convertStringArrayToArrayList;

public class Section {
    private ArrayList<String> loadedTagList = new ArrayList<String>();
    private String actionName;
    private String tagList;

    public Section(String actionName, String tagList) {
        this.actionName = actionName;
        this.tagList = tagList;
        setup();
    }

    public boolean hasAction(String actionName) {
        return (this.actionName.equals(actionName));
    }

    public static XStream configurePageLayoutXStream(XStream xStream) {
        xStream.useAttributeFor(Section.class, "actionName");
        xStream.useAttributeFor(Section.class, "tagList");
        xStream.omitField(Section.class, "loadedTagList");
        return xStream;
    }

    public ArrayList<String> getLoadedTagList() {
        return loadedTagList;
    }

    public void setup() {
        loadedTagList = convertStringArrayToArrayList(tagList.split(Utils.TEST_DATA_DELIMITER));
    }
}
