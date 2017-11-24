package com.misa.chatting.dbAccess.imp;

import com.misa.chatting.config.database_config.ChatServiceConnect;
import com.misa.chatting.dao.Message;
import com.misa.chatting.dao.SendMsgSingleUsers;
import com.misa.chatting.dbAccess.MessageDataAccess;
import com.misa.chatting.main.APILauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDataAccessImp implements MessageDataAccess {
    private static Logger logger = LoggerFactory.getLogger(APILauncher.class.getName());

    @Override
    public void writeMsgToDB(Message msg) throws IOException {
        ChatServiceConnect.init();
        Connection connection = null;
        try {
            connection = ChatServiceConnect.getConnection();
            String query = "insert into Messages (\n" +
                    "`chat_room_id`\n" +
                    ",`user_id`,\n" +
                    "`username`,\n" +
                    "`user_avatar`,\n" +
                    "`message`,\n" +
                    "`create_at`,\n" +
                    "`msg_type`) value (?,?,?,?,?,?,?);";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, Long.parseLong(msg.getRoomID()));
            st.setLong(2, Long.parseLong(msg.getUserID()));
            st.setString(3, msg.getUserNick());
            st.setString(4, msg.getUserAvatar());
            st.setString(5, msg.getMsg());
            st.setString(6, msg.getCreateAt());
            st.setInt(7, msg.getMsgType());

            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {

            logger.info("SQL exception.");
            e.printStackTrace();
        }

    }

    /*
    * convert sendMsgSingleUser to Message obj
    * */

    @Override
    public Message sendTxtSingleUserToMsg(SendMsgSingleUsers msgSingleUsers) {
        Message msg = new Message();
        msg.setRoomID(msgSingleUsers.getChatRoomID());
        msg.setUserID(msgSingleUsers.getSenderID());
        msg.setUserNick(msgSingleUsers.getNickSender());
        msg.setUserAvatar(msgSingleUsers.getSenderAvatar());
        msg.setRoomName(msgSingleUsers.getChatRoomName());
        msg.setMsg(msgSingleUsers.getMsg());
        msg.setMsgType(msgSingleUsers.getMsgType());
        return msg;
    }
}
