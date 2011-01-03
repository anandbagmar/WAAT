package com.thoughtworks.webanalyticsautomation.scriptrunner;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

public interface IScriptRunner {
    String getHTMLSourceByExecutingScript(String OMNITURE_DEBUGGER_URL, String OMNITURE_DEBUGGER_WINDOW_TITLE, String OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX);
}