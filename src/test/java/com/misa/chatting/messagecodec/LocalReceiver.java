package com.misa.chatting.messagecodec;

import com.misa.chatting.messagecodec.util.CustomMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Local receiver
 * @author Junbong
 */
public class LocalReceiver extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    EventBus eventBus = getVertx().eventBus();

    // Does not have to register codec because sender already registered
    /*eventBus.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec());*/

    // Receive message
    eventBus.consumer("local-message-receiver", message -> {
      CustomMessage customMessage = (CustomMessage) message.body();

      System.out.println("Custom message received: "+customMessage.getSummary());

      // Replying is same as publishing
      CustomMessage replyMessage = new CustomMessage(200, "a00000002", "Message sent from local receiver!");
      message.reply(replyMessage);
    });
  }
}
