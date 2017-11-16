package com.misa.chatting.response;

import com.google.gson.JsonObject;

public class UserResponse extends BaseResponse {
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toJonString() {
        JsonObject json = new JsonObject();
        json.addProperty("e", getError());
        json.addProperty("test", getUserID());
        return json.toString();
    }

}
