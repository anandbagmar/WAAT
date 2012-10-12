package com.thoughtworks.webanalyticsautomation.plugins;

import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.scriptrunner.ScriptRunner;

import java.util.ArrayList;

public class JsSniffer implements WaatPlugin {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(JsSniffer.class.getName());

    public JsSniffer() {
        logger.info ("Creating an instance of JsSniffer");
    }

    public ArrayList<Section> captureSections(ScriptRunner scriptRunner) {
        logger.info("ERROR - INVALID API CALLED ON JsSniffer Plugin");
        return null;
    }

    public ArrayList<Section> captureSections(String url) {
        ArrayList<Section> processedPackets = new ArrayList<Section>();
        processedPackets.add(convertToSection(url));
        return processedPackets;
    }

    private Section convertToSection(String url) {
        url = url.replaceAll("&", Utils.TEST_DATA_DELIMITER);
        logger.debug ("Converted tag list: " + url);
        return new Section("", url);
    }

    public ArrayList<Section> captureSections(String[] urlPatterns, int minimumNumberOfPackets) {
        logger.info("ERROR - INVALID API CALLED ON HttpSniffer Plugin");
        return null;
    }
}
