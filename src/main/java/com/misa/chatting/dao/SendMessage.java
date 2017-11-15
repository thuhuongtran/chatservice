package com.misa.chatting.dao;

public class SendMessage {
    private String senderID;
    private String chatRoomID;
    private String nickSender;
    private String chatRoomName;
    private String msg;
    private String senderAvatar;
    private String senderToken;


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

    public void setChatRoomID(String chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }
}
