package com.thoughtworks.webanalyticsautomation.plugins;

import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.scriptrunner.ScriptRunner;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProxyDebugger implements WaatPlugin {
    private static final Logger logger = Logger.getLogger(ProxyDebugger.class.getName());
    private static BrowserMobProxyServer proxy;

    public ProxyDebugger() {
        logger.info ("Creating an instance of ProxyDebugger");
    }

    @Override
    public ArrayList<Section> captureSections(ScriptRunner scriptRunner) {
        return null;
    }

    @Override
    public ArrayList<Section> captureSections(List<String> urlPatterns, int minimumNumberOfPackets) {
        Har har = proxy.getHar();
        List<HarEntry> entries = har.getLog().getEntries();
        ArrayList<Section> capturedSections = new ArrayList<>();
        logger.info("Number of requests captured - " + entries.size());
        logger.info("Number of matching URLs to be found - " + minimumNumberOfPackets);
        int numberOfPacketsCaptured = entries.size();

//        logger.debug("******************************************** URL captured ********************************************");
//        for (HarEntry entry : entries){
//            logger.debug(entry.getRequest().getUrl());
//        }
//        logger.debug("******************************************** URL captured ********************************************");
        logger.info("Finding log entries for criteria - " + urlPatterns.toString());
        for (int packetNumber=numberOfPacketsCaptured-1; packetNumber>=0 && minimumNumberOfPackets>0; packetNumber--)
        {
            HarEntry entry = entries.get(packetNumber);
            String requestUrl = entry.getRequest().getUrl();
            logger.info("Checking url - " + requestUrl);
            if (entry.getRequest() != null) {
                if (isURLMatching(urlPatterns, requestUrl)) {
                    HashMap<String, String> queryParams = new HashMap<String, String>();
                    minimumNumberOfPackets--;
                    logger.info("Found match for URL - " + requestUrl);
                    String decodedURL = getDecodedURLFromPacket(requestUrl);
                    capturedSections.add(convertToSection(decodedURL));
                }
            }
        }
        return capturedSections;
    }

    private String getDecodedURLFromPacket(String capturedPacket) {
        String httpCommand = capturedPacket.split(Utils.getLineSeparator(), 2)[0];
        String decodedURL = null;

        try {
            decodedURL = URLDecoder.decode(httpCommand, getCharset(capturedPacket));
            logger.info("Decoded URL: " + decodedURL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedURL;
    }

    private String getCharset(String capturedPacket) {
        String charSet = "utf-8";
        String[] packetParameters = capturedPacket.split(Utils.getLineSeparator());

        for (String param : packetParameters) {
            if (param.contains("Charset")) {
                charSet = param.split(": ")[1];
                charSet = charSet.split(";")[0];
                charSet = charSet.split(",")[0];
                logger.debug ("Charset: " + charSet);
                break;
            }
        }
        return charSet;
    }

    private Section convertToSection(String decodedURL) {
        decodedURL = decodedURL.replaceAll("&", Utils.TEST_DATA_DELIMITER);
        logger.debug ("Converted tag list: " + decodedURL);
        return new Section("", decodedURL);
    }

    private boolean isURLMatching(List<String> urlPatterns, String requestUrl) {
        boolean patternMatched = false;
        for (String pattern: urlPatterns) {
            if (requestUrl.contains(pattern)) {
                logger.info("Request matches the pattern - " + pattern);
                patternMatched = true;
                break;
            }
        }
        return patternMatched;
    }

    @Override
    public BrowserMobProxy getProxy(int port) {
        logger.info("Initializing BrowserMobProxy for the port - " + port);
        proxy= new BrowserMobProxyServer();
        InetAddress hostInetAddress = null;
        try {
            hostInetAddress = InetAddress.getByName(InetAddress.getLocalHost().getHostName());
            proxy.start(port, hostInetAddress);
        } catch (UnknownHostException e) {
            logger.error(String.format("Unable to start proxy on host:port - '%s':'%s'\nError: %s", hostInetAddress.getHostName(), port, e.getMessage()), e);
            throw new RuntimeException(e);
        }
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS,
                CaptureType.REQUEST_CONTENT,
                CaptureType.REQUEST_BINARY_CONTENT,
                CaptureType.REQUEST_COOKIES,
                CaptureType.RESPONSE_HEADERS,
                CaptureType.RESPONSE_CONTENT,
                CaptureType.RESPONSE_BINARY_CONTENT,
                CaptureType.RESPONSE_COOKIES);
        logger.info("Returning proxy");
        return proxy;
    }

    @Override
    public void enableCapture(String name) {
        logger.info ("Start capture");
        if (proxy == null) { logger.info ("proxy is null"); }
        proxy.newHar(name);
    }

    @Override
    public Object getSeleniumProxy(int port) {
        BrowserMobProxy proxy = this.getProxy(port);
        logger.info ("Get Proxy for the port "+ proxy.getPort());
        return ClientUtil.createSeleniumProxy(proxy);
    }
}
