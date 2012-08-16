package com.thoughtworks.webanalyticsautomation.plugins;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.scriptrunner.ScriptRunner;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class OmnitureDebugger implements WaatPlugin {
    private final Logger logger = Logger.getLogger(getClass());
    private final String OMNITURE_DEBUGGER_URL = "javascript:(window.open(\"\",\"stats_debugger\",\"width=600,height=600,location=0,menubar=0,status=1,toolbar=0,resizable=1,scrollbars=1\").document.write(\"<script language='JavaScript' src='http://sitecatalyst.omniture.com/sc_tools/stats_debugger.html'></script>\"));";
    private static final String OMNITURE_DEBUGGER_WINDOW_TITLE = "Omniture Debugger";
    private static final String OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX = "url_decode";
    private final String OMNITURE_DEBUGGER_SPLITTER = ">Image</span>";

    public OmnitureDebugger() {
        logger.info ("Creating an instance of Omniture Debugger");
    }

    public ArrayList<Section> captureSections(ScriptRunner scriptRunner) {
        logger.info("Capturing sections from Omniture Debugger");
        String sSource= scriptRunner.getHTMLSourceByExecutingScript(OMNITURE_DEBUGGER_URL, OMNITURE_DEBUGGER_WINDOW_TITLE, OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX);
        logger.debug("Omniture Debugger HTML source: \n" + sSource);
        return parseOmnitureDebuggerSections (sSource);
    }

    public ArrayList<Section> captureSections(String url) {
        logger.info ("ERROR - INVALID API CALLED ON OMNITURE DEBUGGER Plugin");
        return null;
    }

    public ArrayList<Section> captureSections(String[] urlPatterns, int minimumNumberOfPackets) {
        logger.info ("ERROR - INVALID API CALLED ON OmnitureDebugger Plugin");
        return null;
    }

    private ArrayList<Section> parseOmnitureDebuggerSections(String sSource) {
        ArrayList<Section> capturedSections = new ArrayList<Section>();
        ArrayList<String> convertedCapturedSections = Utils.convertStringArrayToArrayList(splitCapturedHTML(sSource));
        for (String convertedCapturedSection: convertedCapturedSections) {
            capturedSections.add(new Section("", convertedCapturedSection));
        }
        return capturedSections;
    }

    private String[] splitCapturedHTML(String sSource) {
        sSource = sSource.replace("</SPAN>", "</span>");
        sSource = sSource.replace("Image</span>", "image</span>");
        sSource = sSource.replace("IMAGE</span>", "image</span>");

        return sSource.split(OMNITURE_DEBUGGER_SPLITTER);
    }
}