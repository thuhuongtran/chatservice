package com.misa.chatting.handler.actions;

import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.dao.Roles;
import com.misa.chatting.utils.Authentication;
import io.vertx.core.http.HttpServerRequest;
/*
 * get and set path/ method
 * call request
 * */

public abstract class BaseApiAction {
    protected String path = "";
    protected boolean isPublic = false;
    protected String method;
    protected static int[] roles = {Roles.USER}; // init role equal to user

    public abstract BaseResponse handle(HttpServerRequest request) throws Exception;

    /*
    * getter and setter method
    * */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    /*
    * filter security - check login session
    * */

    public  boolean filterSecutiry(String accessToken){
        //check token
        UserRequest userRequest = Authentication.checkToken(accessToken);
        if(userRequest != null) {
            return Roles.USER == userRequest.getRole();
        }
        return false;
    }
}
