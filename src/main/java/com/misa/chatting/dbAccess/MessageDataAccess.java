package com.misa.chatting.dbAccess;

import com.misa.chatting.dao.Message;
import com.misa.chatting.dao.SendMsgSingleUsers;
import com.misa.chatting.dao.UserRequest;

import java.io.IOException;
import java.sql.SQLException;

public interface MessageDataAccess {
    // push message to msg table
    public void writeMsgToDB(Message msg) throws SQLException, IOException;
    public Message sendTxtSingleUserToMsg(SendMsgSingleUsers msgSingleUsers);

}