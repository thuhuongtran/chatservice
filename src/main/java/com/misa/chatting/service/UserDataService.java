package com.misa.chatting.service;

import com.misa.chatting.dao.UserRequest;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDataService {
    /*
    * get UserRequest from DB
    * */
    public UserRequest getUserRequestFromID(UserRequest user, long id) throws SQLException, IOException;
}
