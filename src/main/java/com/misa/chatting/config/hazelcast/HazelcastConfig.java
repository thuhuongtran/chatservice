package com.misa.chatting.config.hazelcast;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.misa.chatting.dao.ChatRoomTextHistory;
import com.misa.chatting.dao.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class HazelcastConfig {
    private static Logger logger = LoggerFactory.getLogger(HazelcastConfig.class.getName());

    public static String HAZELCAST_CONFIG_FILE = "config/hazelcast.properties";
    public static String ADDRESS = "";
    public static String GROUP_NAME = "";
    public static String GROUP_PASS = "";

    public static void init() throws IOException {
        String configFile = HAZELCAST_CONFIG_FILE;
        Properties prop = new Properties();
        InputStream in = new FileInputStream(configFile);
        prop.load(in);
        ADDRESS = prop.getProperty("address");
        GROUP_NAME = prop.getProperty("group_name");
        GROUP_PASS = prop.getProperty("group_pass");
        in.close();
    }
   /*
    * start hazel server node
    * */
    public static void startHazelcastServer(){
        Config cfg = new Config();
        NetworkConfig networkConfig = cfg.getNetworkConfig();
        networkConfig.setPublicAddress(HazelcastConfig.ADDRESS);
        cfg.setNetworkConfig(networkConfig);
        GroupConfig groupConfig = cfg.getGroupConfig();
        groupConfig.setName(HazelcastConfig.GROUP_NAME);
        groupConfig.setPassword(HazelcastConfig.GROUP_PASS);
        cfg.setGroupConfig(groupConfig);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(cfg);
        Map<String, UserRequest> userRequestMap = hazelcastInstance.getMap("users");
        Map<String, ChatRoomTextHistory> roomHistoryMap = hazelcastInstance.getMap("roomHistory");
    }

}
