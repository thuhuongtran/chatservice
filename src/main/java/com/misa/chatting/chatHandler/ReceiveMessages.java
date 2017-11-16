package com.misa.chatting.chatHandler;

import com.misa.chatting.chatHandler.codec.Messages_codec;
import com.misa.chatting.dao.Messages;
import com.misa.chatting.dao.SendMessageSingleUsers;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import java.util.Date;

public class ReceiveMessages extends AbstractVerticle{
    // ChatSocket must be init firstly
    public static void sendMsg(SendMessageSingleUsers sendMsg){
        // start eventbus
        EventBus eventBus = Vertx.vertx().eventBus();
        // register codec for Messages obj
        eventBus.registerDefaultCodec(Messages.class, new Messages_codec());

        // receive text messages
        eventBus.consumer("chat-room/"+sendMsg.getChatRoomID(), message ->{
            Messages msg = (Messages) message.body();
            System.out.println("Custom received: "+msg.getMessage());

            // replying is same as publishing
            Messages replyMsg = new Messages();
            replyMsg.setUserID(sendMsg.getSenderID());
            replyMsg.setNickname(sendMsg.getNickSender());
            replyMsg.setUserAvatar(sendMsg.getSenderAvatar());
            replyMsg.setChatroomID(sendMsg.getChatRoomID());
            Date date = new Date();
            replyMsg.setCreateAt(date.toString());
            replyMsg.setMessage("Đã xem");
            message.reply(replyMsg);
            System.out.println("Reply successfully!");
        });

    }

}
