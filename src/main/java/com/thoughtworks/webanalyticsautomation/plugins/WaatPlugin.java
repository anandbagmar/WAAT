package com.thoughtworks.webanalyticsautomation.plugins;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.scriptrunner.ScriptRunner;
import net.lightbody.bmp.BrowserMobProxy;

import java.util.ArrayList;
import java.util.List;

public interface WaatPlugin {
    public ArrayList<Section> captureSections(ScriptRunner scriptRunner);

    public ArrayList<Section> captureSections(List<String> urlPatterns, int minimumNumberOfPackets);

    public BrowserMobProxy getProxy(int port);

    public void enableCapture(String name);

    public Object getSeleniumProxy(int port);
}

