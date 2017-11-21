package com.misa.chatting.dao;

public class ChatRoomTextHistory {
    private String roomID;
    private String roomName;
    private SendTextSingleUsers text;
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

    public SendTextSingleUsers getText() {
        return text;
    }

    public void setText(SendTextSingleUsers text) {
        this.text = text;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
