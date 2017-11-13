package com.misa.chatting.websocket_config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebsocketVersion;
import org.vertx.java.platform.Verticle;

/*
* 1. security
* 2. open a chat room- connectionID
* 3. Processing something
* 4. close session
* 5. use eventbus and connectionID to deliver message to all members in chat room
* chat listen to port 8090
* */
public class ChatSocket extends AbstractVerticle{
    // start a chat socket
    public void startChatRoom(){
        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
            @Override
            public void handle(ServerWebSocket event) {

            }
        });
    }

}
