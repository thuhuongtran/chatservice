package com.misa.chatting.websocket_config;

import com.misa.chatting.dao.Messages;
import com.misa.chatting.service.socket.Messages_codec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebsocketVersion;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
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
   public void start(Messages messages){
        // get event bus
        EventBus eventBus = getVertx().eventBus();
        // register codec for message - evetbus only accept messagecodec to pass through
        eventBus.registerDefaultCodec(Messages.class, new Messages_codec());

        Router router = Router.router(vertx);

        // allow outbound to the bus traffic to the outbound address config
       BridgeOptions options = new BridgeOptions()
               .addInboundPermitted(
               new PermittedOptions().setAddress(ChatSocketConfig.CHAT_INBOUND_ADDRESS))
               .addOutboundPermitted(
               new PermittedOptions().setAddress(ChatSocketConfig.CHAT_OUTBOUND_ADDRESS));

       router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options));
       // to serve static resource using staticHandler set a webroot foulder
       router.route().handler(StaticHandler.create().setWebRoot("webroot"));

       vertx.createHttpServer().requestHandler(router::accept).listen(8090);
       // publish a message to the outbound address every second
       vertx.setPeriodic(1000, t-> vertx.eventBus().publish(ChatSocketConfig.CHAT_OUTBOUND_ADDRESS, messages));

       // how to close it ? it is closed or not
   }

}
