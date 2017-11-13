package com.misa.chatting.config.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.IOException;

public class HazelcastClientFactory {
    private static Logger logger = LoggerFactory.getLogger(HazelcastClientFactory.class.getName());
    public static void start() throws IOException {
        singleClient = newClient();
    }
    private static HazelcastInstance singleClient = null;
    public static HazelcastInstance getSingleClient(){
        if(!singleClient.getLifecycleService().isRunning()){
            singleClient = newClient();
        }
        return singleClient;
    }

    private static HazelcastInstance newClient() {
        ClientConfig clientConfig = new ClientConfig();
        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        networkConfig.addAddress(HazelcastConfig.ADDRESS);
        GroupConfig groupConfig = new GroupConfig();
        groupConfig.setName(HazelcastConfig.GROUP_NAME);
        groupConfig.setPassword(HazelcastConfig.GROUP_PASS);
        clientConfig.setGroupConfig(groupConfig);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        logger.info("Initial new chat service hazelcast client instance: ", client.getName());
        return client;
    }
}
