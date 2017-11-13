package com.misa.chatting.dbAccess.message;

import com.google.gson.Gson;
import com.misa.chatting.dao.Messages;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Messages>{
    private static Gson gson = new Gson();
    @Override
    public String encode(Messages message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
