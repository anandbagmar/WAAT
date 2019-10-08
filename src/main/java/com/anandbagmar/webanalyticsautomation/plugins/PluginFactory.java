package com.anandbagmar.webanalyticsautomation.plugins;

import org.apache.log4j.Logger;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class PluginFactory {
    private static final Logger logger = Logger.getLogger(PluginFactory.class.getName());
    private static OmnitureDebugger omnitureDebugger;
    private static ProxyDebugger proxyDebugger;

    public static void reset() {
        logger.info ("Resetting PluginInstances");
        omnitureDebugger = null;
        proxyDebugger = null;
    }

    public static WaatPlugin getWebAnalyticsPluginInstance(WebAnalyticTool webAnalyticTool) {
        if (webAnalyticTool.equals(WebAnalyticTool.OMNITURE_DEBUGGER)) {
            if (null == omnitureDebugger) {
                omnitureDebugger = new OmnitureDebugger();
            }
            logger.info ("Returning Omniture Debugger plugin instance");
            return omnitureDebugger;
        }
        else if (webAnalyticTool.equals(WebAnalyticTool.PROXY)) {
            if (null == proxyDebugger) {
                proxyDebugger = new ProxyDebugger();
            }
            logger.info ("Returning Proxy Debugger plugin instance");
            return proxyDebugger;
        }
        else {
            throw new IllegalArgumentException("Invalid type of Web Analytic Tool (" + webAnalyticTool + ") specified");
        }
    }
}