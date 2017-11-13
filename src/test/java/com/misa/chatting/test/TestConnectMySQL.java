package com.misa.chatting.test;

import com.misa.chatting.config.database_config.ChatServiceConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnectMySQL {
    public static void main(String[] args) throws IOException, SQLException {
      //  ChatServiceConnect.init();
        Connection connection = ChatServiceConnect.getConnection();
        System.out.println("Connect to database successfully");
        String query = "SELECT * FROM chatting.test";

        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        connection.close();
        while (rs.next()){
            System.out.println(rs.getString("test"));
        }
        // success
    }
}
