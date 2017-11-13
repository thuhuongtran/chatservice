package com.misa.chatting.response;


/*
 * return errorCode 
 * check if success or not
 * */
public abstract class BaseResponse {
    private int error = ErrorCode.SUCCESS;

    /*
    * compare errorCode and successCode
    * return true false
    * */
    public boolean isSuccess() {

        return error == ErrorCode.SUCCESS;
    }

    public abstract String toJonString();
    /*
    * getter and setter Error
    * */

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
