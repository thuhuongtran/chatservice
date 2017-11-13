package com.misa.chatting.websocket_config;

import com.misa.chatting.dbAccess.message.MessageDecoder;
import com.misa.chatting.dbAccess.message.MessageEncoder;

import javax.websocket.ClientEndpoint;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class Client_EndPoint {
    public String username;


    private String uri = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Client_EndPoint() {
    }


}
