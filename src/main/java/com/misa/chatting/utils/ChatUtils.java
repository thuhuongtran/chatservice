package com.misa.chatting.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.misa.chatting.dao.SendMessageSingleUsers;

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
}
