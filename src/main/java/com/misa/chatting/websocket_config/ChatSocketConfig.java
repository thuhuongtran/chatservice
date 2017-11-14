package com.misa.chatting.websocket_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ChatSocketConfig {
    private static Logger logger = LoggerFactory.getLogger(ChatSocketConfig.class.getName());
    public static String CHAT_SOCKET_FILE = "config/chat-socket.properties";
    public static String CHAT_OUTBOUND_ADDRESS = "";
    public static String CHAT_INBOUND_ADDRESS = "";

    public static void init() throws IOException {
        String configFile = CHAT_SOCKET_FILE;
        Properties properties = new Properties();
        InputStream in = new FileInputStream(configFile);
        properties.load(in);
        CHAT_INBOUND_ADDRESS = properties.getProperty("inbound-address");
        CHAT_OUTBOUND_ADDRESS = properties.getProperty("outbound-address");
        in.close();
    }
}
