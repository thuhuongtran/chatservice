package com.misa.chatting.dao;

public class Message {
    private String msgID;
    private String roomID;
    private String userID;
    private String userNick;
    private String userAvatar;
    private String msg;
    private int msgType;
    private String createAt;
    private String roomName;

    public Message() {
    }

    public Message(String msgID, String roomID, String userID, String userNick,
                   String userAvatar, String msg, int msgType, String createAt, String roomName) {
        this.msgID = msgID;
        this.roomID = roomID;
        this.userID = userID;
        this.userNick = userNick;
        this.userAvatar = userAvatar;
        this.msg = msg;
        this.msgType = msgType;
        this.createAt = createAt;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
