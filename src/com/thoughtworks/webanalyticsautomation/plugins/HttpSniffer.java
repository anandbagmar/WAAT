package com.thoughtworks.webanalyticsautomation.plugins;

import com.thoughtworks.webanalyticsautomation.common.NetworkDevicePacketCapture;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.scriptrunner.ScriptRunner;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Jan 18, 2011
 * Time: 12:57:21 PM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class HttpSniffer implements WaatPlugin, PacketReceiver {
    private static final Logger logger = Logger.getLogger(HttpSniffer.class.getName());
    private NetworkDevicePacketCapture[] networkDevicePacketCaptures;
    private static final String LIBRARY_NOTES = "ERROR. Missing libraries." +
            "\n\tCheck the Jpcap setup for your platform (http://github.com/anandbagmar/WAAT/wiki/Jpcap-Setup)";

    public HttpSniffer() {
        logger.info ("Creating an instance of HttpSniffer");
    }

    public void receivePacket(Packet packet) {
        logger.debug("Packet received: \n" + packet);
    }

    public ArrayList<Section> captureSections(ScriptRunner scriptRunner) {
        logger.info ("ERROR - INVALID API CALLED ON HttpSniffer Plugin");
        return null;
    }

    public ArrayList<Section> captureSections(String url) {
        logger.info ("ERROR - INVALID API CALLED ON HttpSniffer Plugin");
        return null;
    }

    public ArrayList<Section> captureSections(String[] urlPatterns, int minimumNumberOfPacketsToCapture) {
        return processCapturedPackets(urlPatterns, minimumNumberOfPacketsToCapture);
    }

    private ArrayList<Section> processCapturedPackets(String[] urlPatterns, int minimumNumberOfPacketsToCapture) {
        List<String> capturedPackets = capturePacketsFromNetworkInterfaces();
        ArrayList<String> filteredPacketsOfInterest = new ArrayList<String> ();
        for (int numberOfPackets = capturedPackets.size()-1; ((numberOfPackets >= 0) && (filteredPacketsOfInterest.size() != minimumNumberOfPacketsToCapture)); numberOfPackets--) {
            String capturedPacket = capturedPackets.get(numberOfPackets);

            if (capturedPacketContainsAllUrlPatterns (capturedPacket, urlPatterns)) {
                filteredPacketsOfInterest.add(capturedPacket);
            }
        }
        return processCapturedPackets(filteredPacketsOfInterest);
    }

    private boolean capturedPacketContainsAllUrlPatterns(String capturedPacket, String[] urlPatterns) {
        boolean isPacketMatch = true;
        for (String urlPattern: urlPatterns) {
            if (!capturedPacket.contains(urlPattern)) {
                isPacketMatch = false;
                break;
            }
        }
        return isPacketMatch;
    }

    private ArrayList<String> capturePacketsFromNetworkInterfaces() {
        ArrayList<String> capturedPackets = new ArrayList<String>();
        for (NetworkDevicePacketCapture networkDevicePacketCapture: networkDevicePacketCaptures) {
            if (null != networkDevicePacketCapture) {
                capturedPackets.addAll(networkDevicePacketCapture.getCapturedPackets());
            }
        }
        logger.info ("Total packets captured: " + capturedPackets.size());
        return capturedPackets;
    }

    private ArrayList<Section> processCapturedPackets(ArrayList<String> capturedPackets) {
        ArrayList<Section> processedPackets = new ArrayList<Section>();

        int count = 0;
        for (String capturedPacket: capturedPackets) {
            logger.info ("Packet: " + (count+1) + "\n" + capturedPackets.get(count));
            count++;
            String decodedURL = getDecodedURLFromPacket(capturedPacket);
            processedPackets.add(convertToSection(decodedURL));
        }
        return processedPackets;
    }

    private String getDecodedURLFromPacket(String capturedPacket) {
        String httpCommand = capturedPacket.split(Utils.getLineSeparator(), 2)[0];
        String decodedURL = null;

        try {
            decodedURL = URLDecoder.decode(httpCommand, getCharset(capturedPacket));
            logger.debug("Decoded URL: " + decodedURL);
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

    private NetworkDevicePacketCapture[] monitorAllNetworkInterfaces() {
        NetworkInterface[] devices;
        try {
            devices = JpcapCaptor.getDeviceList();
            logger.info ("Number of network devices found: " + devices.length);
            networkDevicePacketCaptures = new NetworkDevicePacketCapture[devices.length];
            for (int count=0; count<devices.length; count++) {
                networkDevicePacketCaptures[count] = new NetworkDevicePacketCapture(logger, devices[count]);
            }
        } catch (UnsatisfiedLinkError ule) {
            logger.info (LIBRARY_NOTES);
            ule.printStackTrace();
            throw ule;
        }
        return networkDevicePacketCaptures;
    }

    public void enableCapture() {
        logger.info ("Enabling capturing packets on all Network Devices");
        if (null == networkDevicePacketCaptures) {
            networkDevicePacketCaptures = monitorAllNetworkInterfaces();
        }
    }

    public void disableCapture() {
        logger.info ("Disabling capturing packets on all Network Devices");
        if (null != networkDevicePacketCaptures) {
            for (NetworkDevicePacketCapture networkDevicePacketCapture: networkDevicePacketCaptures) {
                if (null != networkDevicePacketCapture) {
                    networkDevicePacketCapture.stop();
                }
            }
        }
    }
}
