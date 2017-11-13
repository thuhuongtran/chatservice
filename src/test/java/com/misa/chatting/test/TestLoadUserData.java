package com.misa.chatting.test;

import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.service.UserDataService;
import com.misa.chatting.service.serviceImp.UserDataServiceImp;

import java.io.IOException;
import java.sql.SQLException;

public class TestLoadUserData {
    private static UserDataService userDataService = new UserDataServiceImp();

    public static void main(String[] args) throws SQLException, IOException {
        UserRequest userReq = new UserRequest();
        userDataService.getUserRequestFromID(userReq, 3);

        System.out.println(userReq.getUsername()+userReq.getEmail()+userReq.getAvatar()+userReq.getLocation()+userReq.getPhone());

    }
}
