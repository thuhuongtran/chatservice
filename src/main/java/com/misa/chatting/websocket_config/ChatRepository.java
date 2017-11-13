package com.misa.chatting.websocket_config;

import com.misa.chatting.dao.Messages;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

import java.util.Optional;

public class ChatRepository {
    private SharedData sharedData;

    public ChatRepository(SharedData sharedData) {
        this.sharedData = sharedData;
    }
    /*
    * OPTIONAL in java is to check null object getting from db
     */
    public Optional<Messages> getMessageByID(String messageID){
        LocalMap<String, Messages> messageSharedData = this.sharedData.getLocalMap(messageID);
        return Optional.of(messageSharedData)
                .filter(m -> !m.isEmpty())
                .map(this::getMessageFromChatRepo);
    }
    /*
    get messages from chat localMap
    * */
    private Messages getMessageFromChatRepo(LocalMap<String, Messages> messagesLocalMap){
       return new Messages(messagesLocalMap.get("msg").getMessageID(),
               messagesLocalMap.get("msg").getChatroomID(),
               messagesLocalMap.get("msg").getUserID(),
               messagesLocalMap.get("msg").getNickname(),
               messagesLocalMap.get("msg").getUserAvatar(),
               messagesLocalMap.get("msg").getMessage(),
               messagesLocalMap.get("msg").getCreateAt());
    }
    /*
    * put message to chat repository localMap
    * */
    public void putMessageToLocal(Messages msg){
        LocalMap<String, Messages> messageSharedData = this.sharedData.getLocalMap(msg.getMessageID());
        messageSharedData.put("msg",msg);

    }
}
