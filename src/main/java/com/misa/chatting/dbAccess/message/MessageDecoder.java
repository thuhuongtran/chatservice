package com.misa.chatting.dbAccess.message;

import com.google.gson.Gson;
import com.misa.chatting.dao.TextMsg;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<TextMsg>{
    private static Gson gson = new Gson();
    @Override
    public TextMsg decode(String s) throws DecodeException {
        return gson.fromJson(s, TextMsg.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s !=null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
