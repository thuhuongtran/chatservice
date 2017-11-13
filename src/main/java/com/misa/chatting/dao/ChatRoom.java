package com.misa.chatting.dao;

import java.math.BigInteger;

public class ChatRoom {
    private BigInteger chatRoomID;
    private String chatRoomName;
    private String createAt;

    public BigInteger getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(BigInteger chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
