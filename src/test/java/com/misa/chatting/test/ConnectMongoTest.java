package com.misa.chatting.test;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;
import org.jongo.Jongo;

import com.misa.chatting.config.database_config.MongoPool;

public class ConnectMongoTest {
	public static void main(String[] args) throws IOException {
		PropertyConfigurator.configure("apiconfig/log4j.properties");
		// initialize 
		MongoPool.init();
		// get collection
		Jongo jongo = new Jongo(MongoPool.getJongoDb());
		 Document doc = new Document();
         
         doc.append("handler", "chatting");
         doc.append("Document", "Messages");
         doc.append("document", "Chat Room");
         
         MongoPool.log("chatting", doc);
	}
}
