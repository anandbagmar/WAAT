package com.thoughtworks.webanalyticsautomation.common;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Jan 12, 2011
 * Time: 12:55:27 PM
 * <p/>
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class NetworkDevicePacketCapture implements Runnable {
    private Logger logger;
    private NetworkInterface device;
    private volatile Thread myThread;
    private ArrayList<String> capturedPackets = new ArrayList<String>();
    private boolean doMore;
    private String id;
    Map<Long, TCPBodyData> tcpBodys = new HashMap<Long, TCPBodyData>();


    private void setup(Logger logger, NetworkInterface device) {
        this.logger = logger;
        this.device = device;
        myThread = new Thread(this);
        id = myThread.getName() + "_" + myThread.getId();
        logger.info("Starting: " + id);
        myThread.start();
    }

    public NetworkDevicePacketCapture(Logger logger, NetworkInterface device) {
        setup(logger, device);
    }

    public void run() {
        try {
            logger.info("Running: " + id);
            JpcapCaptor jpcap = JpcapCaptor.openDevice(device, 2000, false, 20);

            doMore = true;
            while (doMore) {
                //capture a single packet and print it out
                Packet packet = jpcap.getPacket();
                if (null != packet && packet instanceof TCPPacket) {
                    TCPPacket tcppacl = (TCPPacket) packet;
                    // Done to handle the fragmented Packets
                    byte[] body = addBodyData(tcppacl);
                    if (tcppacl.psh) {
                        capturedPackets.add(new String(body));
                    }
                }
            }
            jpcap.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (myThread.isAlive()) {
            logger.info("Stopping: " + id);
            doMore = false;
        } else {
            logger.info(id + " already STOPPED");
        }
    }

    public ArrayList<String> getCapturedPackets() {
        logger.debug("getCapturedPackets for: " + id + ": " + capturedPackets.size());
        return capturedPackets;
    }

    private byte[] addBodyData(TCPPacket packet) {
        TCPBodyData tcpBodyData;
        Long ack = new Long(packet.ack_num);
        if (tcpBodys.containsKey(ack)) {
            tcpBodyData = tcpBodys.get(ack);
            tcpBodyData.addBytes(packet.data);
        } else {
            tcpBodyData = new TCPBodyData(packet.data);
            tcpBodys.put(ack, tcpBodyData);
        }

        if (packet.psh) {
            tcpBodys.remove(ack);
        }

        return tcpBodyData.getBytes();
    }

    private class TCPBodyData {

        byte[] bytes = null;

        public TCPBodyData(byte[] bytes) {
            this.bytes = bytes;
        }

        public void addBytes(byte[] bytes) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(this.bytes);
                outputStream.write(bytes);
                this.bytes = outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public byte[] getBytes() {
            return bytes;
        }
    }

}
