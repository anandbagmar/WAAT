package com.thoughtworks.webanalyticsautomation.plugins;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.scriptrunner.IScriptRunner;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class OmnitureDebugger implements IWAATPlugin {
    private Logger logger = Logger.getLogger(getClass());
    private String OMNITURE_DEBUGGER_URL = "javascript:(window.open(\"\",\"stats_debugger\",\"width=600,height=600,location=0,menubar=0,status=1,toolbar=0,resizable=1,scrollbars=1\").document.write(\"<script language='JavaScript' src='https://sitecatalyst.omniture.com/sc_tools/stats_debugger.html'></script>\"));";
    private static String OMNITURE_DEBUGGER_WINDOW_TITLE = "Omniture Debugger";
    private static String OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX = "name=url_decode";
    private String OMNITURE_DEBUGGER_SPLITTER = ">Image</span>";

    public OmnitureDebugger() {
        logger.info ("Creating an instance of Omniture Debugger");
    }

    public ArrayList<Section> captureSections(IScriptRunner scriptRunner) {
        logger.info("Capturing sections from Omniture Debugger");
        String sSource= scriptRunner.getHTMLSourceByExecutingScript(OMNITURE_DEBUGGER_URL, OMNITURE_DEBUGGER_WINDOW_TITLE, OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX);
        logger.debug("Omniture Debugger HTML source: \n" + sSource);
        ArrayList<Section> capturedSections = parseOmnitureDebuggerSections (sSource);
        return capturedSections;
    }

    private ArrayList<Section> parseOmnitureDebuggerSections(String sSource) {
        ArrayList<Section> capturedSections = new ArrayList<Section>();
        ArrayList<String> convertedCapturedSections = Utils.convertStringArrayToArrayList(sSource.split(OMNITURE_DEBUGGER_SPLITTER));
        for (String convertedCapturedSection: convertedCapturedSections) {
            capturedSections.add(new Section("", convertedCapturedSection));
        }
        return capturedSections;
    }

}
