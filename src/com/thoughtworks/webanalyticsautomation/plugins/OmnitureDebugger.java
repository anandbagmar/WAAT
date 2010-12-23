package com.thoughtworks.webanalyticsautomation.plugins;

import com.thoughtworks.webanalyticsautomation.IWAATPlugin;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class OmnitureDebugger implements IWAATPlugin {
    private Logger logger = Logger.getLogger(getClass());
    
    public OmnitureDebugger() {
        logger.info ("Creating an instance of Omniture Debugger");
    }

    public ArrayList<Section> captureSections() {
        logger.info ("Capturing sections from Omniture Debugger");
        return null;
    }
}
