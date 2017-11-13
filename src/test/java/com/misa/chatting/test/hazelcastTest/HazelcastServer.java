package com.misa.chatting.test.hazelcastTest;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;
import com.misa.chatting.dao.UserRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HazelcastServer
{
    public static void main(String[] args) {
        Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Map<Integer, String> customerMap = instance.getMap("customers");
        customerMap.put(1, "Bangalore");
        customerMap.put(2, "Chennai");
        customerMap.put(3, "Hyderabad");

        System.out.println("Map Size:" + customerMap.size());
        Set<Map.Entry<Integer,String>> customers = customerMap.entrySet();

        for (Iterator<Map.Entry<Integer, String>> iterator = customers.iterator(); iterator.hasNext();) {
            Map.Entry<Integer, String> entry =  iterator.next();
            System.out.println("Customer Id : "+ entry.getKey()+" Customer Name : "+entry.getValue());
        }

    }
    public static void startHazelServer(){
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Long, String> map = hazelcastInstance.getMap("data");
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator("newid");
        for (int i = 0; i < 10; i++) {
            map.put(idGenerator.newId(), "message" + 1);
        }
    }
    public static void startHazelcastServer(){
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Long, String> userRequestMap = hazelcastInstance.getMap("users");
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator("newid");
        for (int i = 0; i < 10; i++) {
            userRequestMap.put(idGenerator.newId(), "message" + 1);
        }
    }
}
