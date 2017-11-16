package com.misa.chatting.service.serviceImp;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.misa.chatting.config.hazelcast.HazelcastClientFactory;
import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.dbAccess.UserDataAccess;
import com.misa.chatting.dbAccess.imp.UserDataAccessImp;
import com.misa.chatting.response.UserResponse;
import com.misa.chatting.service.UserDataService;

import java.io.IOException;
import java.sql.SQLException;

public class UserDataServiceImp implements UserDataService {
    private UserDataAccess userDataAccess = new UserDataAccessImp();

    /*
    * get UserRequest from ID
    * */
    @Override
    public UserRequest getUserRequestFromID(UserRequest user, long id) throws SQLException, IOException {

        UserResponse userResponse = new UserResponse();
        // check whether user is on hazelcast otherwise get from db
        HazelcastInstance instance = HazelcastClientFactory.getSingleClient();
        IMap<Long, UserRequest> userMap = instance.getMap("users");
        if (userMap.containsKey(id)) {
            UserRequest userReq = userMap.get(id);
            return userReq;
        } else {
            UserRequest userReq = userDataAccess.getUserDataByID(
                    userDataAccess.loadUserResultSetByID(id, userResponse), user);
            return userReq;
        }
    }
}
