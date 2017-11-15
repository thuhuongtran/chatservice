package com.misa.chatting.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.misa.chatting.dao.Messages;
import com.misa.chatting.dao.SendMessage;
import com.misa.chatting.dao.UserRequest;

public class ChatUtils {
	/*
	 * Gson is to convert a java obj to json obj in opposition
	 * */
	private static Gson gson = new Gson();
	/*
	 * turn json string to jsonObject
	 * */
    public static JsonObject toJsonObject(String json){
        try {
            return gson.fromJson(json, JsonObject.class);
        }catch (Exception e){
            return null;
        }
    }
    /*
    * get sendMsg from string body
    * */
    public static SendMessage jsonToSendMessage(String body){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(body);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setSenderID(jsonObject.get("senderID").getAsString());
        sendMessage.setNickSender(jsonObject.get("nickSender").getAsString());
        sendMessage.setSenderAvatar(jsonObject.get("sendAva").getAsString());
        sendMessage.setChatRoomID(jsonObject.get("roomID").getAsString());
        sendMessage.setChatRoomName(jsonObject.get("roomName").getAsString());
        sendMessage.setSenderToken(jsonObject.get("senderToken").getAsString());
        return sendMessage;
    }
    /*
    * date time format
    * */
}
