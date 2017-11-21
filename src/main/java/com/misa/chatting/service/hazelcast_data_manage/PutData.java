package com.misa.chatting.service.hazelcast_data_manage;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.misa.chatting.config.hazelcast.HazelcastClientFactory;
import com.misa.chatting.dao.ChatRoomTextHistory;
import com.misa.chatting.dao.SendTextSingleUsers;
import com.misa.chatting.dao.UserRequest;

public class PutData {
    public static void putUserDataToHazel(UserRequest userRequest){

        if(!isOnHazel(userRequest)) {
            HazelcastInstance instance = HazelcastClientFactory.getSingleClient();
            IMap<String, UserRequest> userMap = instance.getMap("users");
            userMap.put(String.valueOf(userRequest.getUser_id()), userRequest);
        }
    }
    // check user on hazel
    public static boolean isOnHazel(UserRequest userRequest){
        HazelcastInstance instance = HazelcastClientFactory.getSingleClient();
        IMap<String, UserRequest> userMap = instance.getMap("users");
        long id = userRequest.getUser_id();
        if (userMap.containsKey(id)) {
            return true;
        }
        return false;
    }
    // put message to roomHistory - call whenever user send a new msg
    public static void putMsgToRoom(SendTextSingleUsers msg){
        HazelcastInstance instance = HazelcastClientFactory.getSingleClient();
        IMap<String, ChatRoomTextHistory> roomHistoryIMap = instance.getMap("roomHistory");
        ChatRoomTextHistory chatRoom = new ChatRoomTextHistory();
        chatRoom.setRoomID(msg.getChatRoomID());
        chatRoom.setRoomName(msg.getChatRoomName());
       // chatRoom.setCreateAt("");
        chatRoom.setText(msg);
        roomHistoryIMap.put(msg.getChatRoomID(), chatRoom);
    }

}
