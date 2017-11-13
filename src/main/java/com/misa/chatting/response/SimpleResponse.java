package com.misa.chatting.response;

public class SimpleResponse extends BaseResponse {
    public SimpleResponse(){

    }
    public SimpleResponse(int error){
        setError(error);
    }
    @Override
    public String toJonString() {
        StringBuilder sb = new StringBuilder("{\"e\":");
        sb.append(getError()).append("}");
        return sb.toString();
    }
}
