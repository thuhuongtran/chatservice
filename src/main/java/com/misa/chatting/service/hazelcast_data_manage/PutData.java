package com.misa.chatting.service.hazelcast_data_manage;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.misa.chatting.config.hazelcast.HazelcastClientFactory;
import com.misa.chatting.dao.UserRequest;

public class PutData {
    public static void putUserDataToHazel(UserRequest userRequest){
        HazelcastInstance instance = HazelcastClientFactory.getSingleClient();
        IMap<String, UserRequest> userMap = instance.getMap("users");
        userMap.put(String.valueOf(userRequest.getUser_id()), userRequest);
    }
}
