package com.misa.chatting.dbAccess.imp;

import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.config.database_config.UserServiceConnect;
import com.misa.chatting.dbAccess.UserDataAccess;
import com.misa.chatting.response.ErrorCode;
import com.misa.chatting.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataAccessImp implements UserDataAccess {
    private static Logger logger = LoggerFactory.getLogger(UserDataAccessImp.class.getName());

    /*
    * load user data from ResultSet
    * */
    @Override
    public ResultSet loadUserResultSetByID(long id, UserResponse userResponse){
        ResultSet rs = null;
        // cactch error - if user is not in db
        try{
            UserServiceConnect.init();
            Connection connection = UserServiceConnect.getConnection();
            String query = "SELECT `id`, `username`, `nickname`, `email`, `avatar`,`phone`, `location`, `timeCreated`" +
                    "FROM user WHERE `id`="+id;
            PreparedStatement st = connection.prepareStatement(query);
            rs = st.executeQuery();
            connection.close();
        }
        catch(SQLException e){
            userResponse.setError(ErrorCode.SQL_EXCEPTION);
            logger.info("SQL exception.");
            e.printStackTrace();

        }
        catch (IOException e){
            userResponse.setError(ErrorCode.SYSTEM_ERROR);
            logger.info("system error.");
            e.printStackTrace();

        }
        return rs;
    }
    /*
    * get User Data from ResultSet
    * */
    @Override
    public UserRequest getUserDataByID(ResultSet rs, UserRequest user) {
        try {
            while (rs.next()){
                user.setUser_id(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("avatar"));
                user.setPhone(rs.getString("phone"));
                user.setLocation(rs.getString("location"));
                user.setTimeCreated(rs.getString("timeCreated"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find user from userID");
            logger.info("Could not find user from userID");
            e.printStackTrace();
            return user;
        }
        return user;
    }
}
