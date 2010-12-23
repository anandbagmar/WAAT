package com.thoughtworks.webanalyticsautomation.plugins;

import com.thoughtworks.webanalyticsautomation.IWAATPlugin;
import org.apache.log4j.Logger;

public class PluginFactory {
    private Logger logger = Logger.getLogger(getClass());

    public IWAATPlugin getWebAnalyticsPluginInstance(WebAnalyticTool webAnalyticTool) {
        if (webAnalyticTool.equals(WebAnalyticTool.OMNITURE)) {
            return new OmnitureDebugger();
        }
        else {
            throw new IllegalArgumentException("Invalid type of Web Analytic Tool (" + webAnalyticTool + ") specified");
        }
    }
}
