package com.misa.chatting.test.hazelcastTest;

import com.misa.chatting.config.hazelcast.HazelcastConfig;

import java.io.IOException;

public class TestHazelcastConnect {
    public static void main(String[] args) throws IOException {
        /*
        HazelcastServer.startHazelServer();
        System.out.println("Start server success!");
        HazelCastClient.startHazelcastClient();
        System.out.println("Start client success!");
        */
        /*
        HazelcastServer.startHazelcastServer();
        System.out.println("Start server successfully");
        HazelCastClient.startHazelClient();
        System.out.println("Start client successfully");*/
        try {
            HazelcastConfig.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("is there any "+HazelcastConfig.ADDRESS);
    }
}
