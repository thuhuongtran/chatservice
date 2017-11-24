package com.misa.chatting.dao;

import java.io.Serializable;

public class ChatRoomTextHistory implements Serializable{
    public static final long serialVersionUID = 1L;
    private String roomID;
    private String roomName;
    private SendMsgSingleUsers text;
    private String createAt;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public SendMsgSingleUsers getText() {
        return text;
    }

    public void setText(SendMsgSingleUsers text) {
        this.text = text;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
