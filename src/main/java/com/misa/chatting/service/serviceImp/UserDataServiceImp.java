package com.misa.chatting.service.serviceImp;

import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.dbAccess.UserDataAccess;
import com.misa.chatting.dbAccess.imp.UserDataAccessImp;
import com.misa.chatting.response.UserResponse;
import com.misa.chatting.service.UserDataService;

import java.io.IOException;
import java.sql.SQLException;

public class UserDataServiceImp implements UserDataService{
    private UserDataAccess userDataAccess = new UserDataAccessImp();
    /*
    * get UserRequest from ID
    * */
    @Override
    public UserRequest getUserRequestFromID(UserRequest user, long id) throws SQLException, IOException {
        UserResponse userResponse = new UserResponse();
        return userDataAccess.getUserDataByID(userDataAccess.loadUserResultSetByID(id, userResponse),user);
}
}
