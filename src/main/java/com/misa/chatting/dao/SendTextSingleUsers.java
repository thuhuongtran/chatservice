package com.misa.chatting.dao;

import com.google.gson.JsonObject;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.response.ErrorCode;

public class SendTextSingleUsers extends BaseResponse {
    private String senderID;
    private String chatRoomID;
    private String nickSender;
    private String chatRoomName;
    private String msg;
    private String senderAvatar;
    private String senderToken;
    private String sendTime;

    public SendTextSingleUsers(String senderID, String chatRoomID, String nickSender,
                               String chatRoomName, String msg, String senderAvatar, String senderToken,
                               String sendTime) {
        this.senderID = senderID;
        // chat_roomID = senderID + receiverID
        this.chatRoomID = senderID+chatRoomID;
        this.nickSender = nickSender;
        this.chatRoomName = chatRoomName;
        this.msg = msg;
        this.senderAvatar = senderAvatar;
        this.senderToken = senderToken;
        this.sendTime = sendTime;
    }

    public SendTextSingleUsers() {
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getNickSender() {
        return nickSender;
    }

    public void setNickSender(String nickSender) {
        this.nickSender = nickSender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

   /* public void setChatRoomID(String chatRoomID, String senderID) {
        this.chatRoomID = chatRoomID;
    }*/

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    @Override
    public String toJonString() {
        JsonObject json = new JsonObject();
        if (getError() == ErrorCode.SUCCESS) {
            json.addProperty("senderID", getSenderID());
            json.addProperty("chatroomID", getChatRoomID());
            json.addProperty("chatroomName", getChatRoomName());
            json.addProperty("nickSender", getNickSender());
            json.addProperty("senderAva", getSenderAvatar());
            json.addProperty("msg", getMsg());
            json.addProperty("userToken", getSenderToken());

        }
        // add error
        json.addProperty("e", getError());
        return json.toString();
    }
}
