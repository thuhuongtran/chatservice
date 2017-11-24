package com.misa.chatting.response;


/*
 * return errorCode 
 * check if success or not
 * */
public abstract class BaseResponse {
    private int error = ErrorCode.UNPASS;
    private String fileLink = null;

    /*
    * compare errorCode and successCode
    * return true false
    * */
    public boolean isSuccess() {

        return error == ErrorCode.SUCCESS;
    }

    public BaseResponse() {
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

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}
