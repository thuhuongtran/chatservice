package com.misa.chatting.dbAccess.message;

import com.google.gson.Gson;
import com.misa.chatting.dao.TextMsg;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<TextMsg>{
    private static Gson gson = new Gson();
    @Override
    public String encode(TextMsg message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
