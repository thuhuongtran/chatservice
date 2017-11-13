package com.misa.chatting.dao;

public class SendMessage {
    private String senderID;
    private String receiverID;
    private String nickSender;
    private String nickReceiver;
    private String msg;
    private String senderAvatar;
    private String receiverAvatar;
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

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getNickSender() {
        return nickSender;
    }

    public void setNickSender(String nickSender) {
        this.nickSender = nickSender;
    }

    public String getNickReceiver() {
        return nickReceiver;
    }

    public void setNickReceiver(String nickReceiver) {
        this.nickReceiver = nickReceiver;
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

    public String getReceiverAvatar() {
        return receiverAvatar;
    }

    public void setReceiverAvatar(String receiverAvatar) {
        this.receiverAvatar = receiverAvatar;
    }
}
