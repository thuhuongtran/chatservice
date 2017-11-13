package com.misa.chatting.test.sendMessage;

import com.misa.chatting.websocket_config.Client_EndPoint;
import org.glassfish.tyrus.client.ClientManager;


import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;

public class TestSendMessage {
    private static Session session;
    private static ClientManager client;

    public static void main(String[] args) throws IOException, InterruptedException {
        callServerEndpoint("test1");
        System.out.println("success!");
    }

    public static void callServerEndpoint(String username) throws InterruptedException, IOException {
        client = null;
        session = null;
        String uri = "ws://localhost:8083/chat/" + username + "";
        try {
            client = ClientManager.createClient();
            session = client.connectToServer(Client_EndPoint.class, URI.create(uri));
            System.out.println("Connected successfully!");
            Thread.sleep(1000);
        } catch (DeploymentException e) {
            System.out.println("Can not connect.");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
                System.out.println("session id closed!");
            }
        }


    }
}
