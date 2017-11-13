package com.misa.chatting.test.hazelcastTest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.misa.chatting.dao.UserRequest;

public class HazelCastClient {

    @SuppressWarnings({"deprecation"})
    public static void main(String[] args) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress("127.0.0.1");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap<Object, Object> map = client.getMap("customers");
        printMap(map);
        map.put(5, "New Delhi");
        map.put(6, "Cochin");
        printMap(map);
    }

    @SuppressWarnings("unchecked")
    private static void printMap(@SuppressWarnings("rawtypes") Map map) {
        System.out.println("Map Size:" + map.size());
        Set<Entry<Integer, String>> customers = map.entrySet();
        for (Iterator<Entry<Integer, String>> iterator = customers.iterator(); iterator.hasNext(); ) {
            Entry<Integer, String> entry = (Entry<Integer, String>) iterator.next();
            System.out.println("Customer Id : " + entry.getKey() + " Customer Name : " + entry.getValue());
        }
    }
    public static void startHazelcastClient(){
        ClientConfig config = new ClientConfig();
        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");
        groupConfig.setPassword("dev-pass");
        HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient(config);
        IMap<Long, String> map = hazelcastInstanceClient.getMap("data");
        for (Entry<Long, String> entry : map.entrySet()) {
            System.out.println(String.format("Key: %d, Value: %s", entry.getKey(), entry.getValue()));
        }
    }
    public static void startHazelClient(){
        ClientConfig config = new ClientConfig();
        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");
        groupConfig.setPassword("dev-pass");
        HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient(config);
        //IMap<String, UserRequest> map = hazelcastInstanceClient.getMap("users");
        IMap<Long, String> map = hazelcastInstanceClient.getMap("users");
        for (Entry<Long, String> entry : map.entrySet()) {
            System.out.println(String.format("Key: %d, Value: %s", entry.getKey(), entry.getValue()));
        }
        System.out.println("Run to here!");
    }
}
