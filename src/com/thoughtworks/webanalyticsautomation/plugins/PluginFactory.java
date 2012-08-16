package com.thoughtworks.webanalyticsautomation.plugins;

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
    private static HttpSniffer httpSniffer;
    private static final Logger logger = Logger.getLogger(PluginFactory.class.getName());

    public static void reset() {
        logger.info ("Resetting PluginInstances");
        httpSniffer = null;
    }

    public static WaatPlugin getWebAnalyticsPluginInstance(WebAnalyticTool webAnalyticTool) {
        if (webAnalyticTool.equals(WebAnalyticTool.OMNITURE_DEBUGGER)) {
            return new OmnitureDebugger();
        }
        else if (webAnalyticTool.equals(WebAnalyticTool.HTTP_SNIFFER)) {
            if (null == httpSniffer) {
                httpSniffer = new HttpSniffer ();
            }
            return httpSniffer;
        }
        else if (webAnalyticTool.equals(WebAnalyticTool.JS_SNIFFER)){
            return  new JsSniffer();
        }
        else {
            throw new IllegalArgumentException("Invalid type of Web Analytic Tool (" + webAnalyticTool + ") specified");
        }
    }
}