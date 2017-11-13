package com.misa.chatting.dbAccess;

import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.response.UserResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDataAccess {

    public ResultSet loadUserResultSetByID(long id, UserResponse userResponse) throws SQLException, IOException;
    public UserRequest getUserDataByID(ResultSet rs, UserRequest user) throws SQLException;
}
