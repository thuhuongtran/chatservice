package com.misa.chatting.chatHandler;

import com.misa.chatting.chatHandler.codec.Messages_codec;
import com.misa.chatting.dao.TextMsg;
import com.misa.chatting.dao.SendTextSingleUsers;
import com.misa.chatting.main.APILauncher;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* is sender
* after reply to address
* - reply to json obj - not
* push msh in chatroom to hazelcast
* have to catch msg has been read event
*
* */
public class ReceiveMessages extends AbstractVerticle{
    private static Logger logger = LoggerFactory.getLogger(APILauncher.class.getName());

    // ChatSocket must be init firstly
    public static void sendMsg(SendTextSingleUsers sendMsg){
        // start eventbus
        EventBus eventBus = Vertx.vertx().eventBus();
        // register codec for TextMsg obj
        eventBus.registerDefaultCodec(TextMsg.class, new Messages_codec());

        //create message obj
        TextMsg msg = new TextMsg();
        msg.setUserID(sendMsg.getSenderID());
        msg.setNickname(sendMsg.getNickSender());
        msg.setUserAvatar(sendMsg.getSenderAvatar());
        msg.setChatroomID(sendMsg.getChatRoomID());
        msg.setCreateAt(sendMsg.getSendTime());
        msg.setMessage(sendMsg.getMsg());
        // receive text messages
        eventBus.send("localhost:8090/chat-room/"+sendMsg.getChatRoomID(),msg, reply ->{ // worry about address - /chat-room/
            // get reply msg from other clients
            if(reply.succeeded()){
                TextMsg otherTextMsg = (TextMsg) reply.result().body();
                System.out.println(otherTextMsg.getNickname()+" other client sent: " +otherTextMsg.getMessage());
                logger.info(otherTextMsg.getNickname()+"sent: "+otherTextMsg.getMessage());
            }
            else{
                logger.info("No reply.");
            }
        });
        logger.info("Send text msg successfully!");
        System.out.println("Send text msg successfully!");
    }

}
