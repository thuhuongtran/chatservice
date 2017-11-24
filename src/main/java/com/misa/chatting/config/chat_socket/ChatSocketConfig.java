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

    public static String CHAT_TEXT_MSG_ADDRESS="";
    public final static String UPLOAD_FILE_DIRECTORY="upload/file";


}
