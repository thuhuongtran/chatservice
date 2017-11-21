package com.misa.chatting.chatHandler;

import com.misa.chatting.chatHandler.codec.Messages_codec;
import com.misa.chatting.dao.TextMsg;
import com.misa.chatting.dao.SendTextSingleUsers;
import com.misa.chatting.dao.Messages;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import java.util.Date;
/*
* is receiver
* after reply to address
* - reply to json obj - not
* push msh in chatroom to hazelcast
* have to catch msg has been read event
*
* */
public class ReceiveMessages extends AbstractVerticle{
    // ChatSocket must be init firstly
    public static void sendMsg(SendTextSingleUsers sendMsg){
        // start eventbus
        EventBus eventBus = Vertx.vertx().eventBus();
        // register codec for TextMsg obj
        eventBus.registerDefaultCodec(TextMsg.class, new Messages_codec());

        //create message obj

        // receive text messages
        eventBus.consumer("/chat-room/"+sendMsg.getChatRoomID(), message ->{ // worry about address - /chat-room/
            TextMsg msg = (TextMsg) message.body();
            System.out.println("Custom received: "+msg.getMessage());

            // replying is same as publishing
            TextMsg replyMsg = new TextMsg();
            replyMsg.setUserID(sendMsg.getSenderID());
            replyMsg.setNickname(sendMsg.getNickSender());
            replyMsg.setUserAvatar(sendMsg.getSenderAvatar());
            replyMsg.setChatroomID(sendMsg.getChatRoomID());
            replyMsg.setCreateAt(sendMsg.getSendTime());
            replyMsg.setMessage("Đã gửi.");
            message.reply(replyMsg);
            System.out.println("Reply successfully!");
        });
    }

}
