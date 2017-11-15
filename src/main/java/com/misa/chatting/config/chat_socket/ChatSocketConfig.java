package com.misa.chatting.config.chat_socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ChatSocketConfig {
    private static Logger logger = LoggerFactory.getLogger(ChatSocketConfig.class.getName());

    public static String CHAT_SOCKET_CONFIG_FILE="config/chat-socket.properties";
    public static String CHAT_TEXT_MSG_ADDRESS="";

    public static void init() throws IOException {
        String configFile = CHAT_SOCKET_CONFIG_FILE;
        Properties prop = new Properties();
        InputStream in = new FileInputStream(configFile);
        prop.load(in);
        CHAT_TEXT_MSG_ADDRESS = prop.getProperty("text-msg-address");
        in.close();
    }
}
