package com.misa.chatting.dao;

import com.google.gson.JsonObject;

import java.util.Date;

public class Messages {
    private String messageID;
    private String chatroomID;
    private String userID;
    private String nickname;
    private String userAvatar;
    private String message;
    private String createAt;

    public Messages(String messageID, String chatroomID, String userID,
                    String nickname, String userAvatar, String message, String createAt) {
        this.messageID = messageID;
        this.chatroomID = chatroomID;
        this.userID = userID;
        this.nickname = nickname;
        this.userAvatar = userAvatar;
        this.message = message;
        this.createAt = createAt;
    }

    public Messages() {
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(String chatroomID) {
        this.chatroomID = chatroomID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
    /*
    * convert class to json obj
    * */
    public String toJsonString(Messages msg){
        JsonObject json = new JsonObject();
        json.addProperty("msgID", msg.getMessageID());
        json.addProperty("roomID", msg.getChatroomID());
        json.addProperty("userID", msg.getUserID());
        json.addProperty("nick", msg.getNickname());
        json.addProperty("avatar",msg.getUserAvatar());
        json.addProperty("msg", msg.getMessage());
        json.addProperty("time", msg.getCreateAt());
        return json.toString(); // and add error
    }
}
