package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.scriptrunner.IScriptRunner;

import java.util.ArrayList;

public interface IWAATPlugin {
    public ArrayList<Section> captureSections(IScriptRunner scriptRunner);
}
