package com.misa.chatting.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.response.ErrorCode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Authentication {
    /*
    * check user token whether out or in a login session
    * true - return userID and its token
    * false - response false code
    * */

    public static UserRequest checkToken(String token) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // closeableHTTp is an abstract class, imp httpclient, help to manage numbers of request
        HttpGet request = new HttpGet("http://192.168.1.92:8081/token"); // have to change this link later
        UserRequest accessToken=null;
        try {
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.addHeader("at",token);
            request.addHeader("ai","ns");
            CloseableHttpResponse response = httpclient.execute(request);
            // get message of response
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            JsonObject result= (JsonObject) new JsonParser().parse(json);
            int status=result.get("e").getAsInt();
            if(status==0){
                // status = 0 means success
                accessToken=new UserRequest();
                accessToken.setUser_id(result.get("id").getAsInt());
                accessToken.setRole(0); // set role for user equal to 0
                accessToken.setStatus(ErrorCode.ACTIVE_TOKEN);
            }else if(status==14){
                // status = 14 - login again
                accessToken=new UserRequest();
                accessToken.setUser_id(result.get("id").getAsInt());
                accessToken.setToken(result.get("at").getAsString());
                accessToken.setRole(0); // set role for user eq 0
                accessToken.setStatus(ErrorCode.UPDATE_TOKEN);
            }else if(status==15){
                // status = 15 - fail
                accessToken=new UserRequest();
                accessToken.setStatus(ErrorCode.INVALID_TOKEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }
}
