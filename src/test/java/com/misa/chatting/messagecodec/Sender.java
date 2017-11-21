package com.misa.chatting.messagecodec;

import com.misa.chatting.messagecodec.util.CustomMessage;
import com.misa.chatting.messagecodec.util.CustomMessageCodec;
import com.misa.chatting.messagecodec.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;


/**
 * Publisher
 * @author Junbong
 */
public class Sender extends AbstractVerticle {

  public static void main(String[] args) {
    /*
    eventBus = Vertx.vertx().eventBus();
    eventBus.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec);
    */
    Runner.runClusteredExample(Sender.class);
  }
  @Override
  public void start() throws Exception {
    EventBus eventBus = getVertx().eventBus();

    // Register codec for custom message
    eventBus.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec());

    // Custom message
    CustomMessage clusterWideMessage = new CustomMessage(200, "a00000001", "Message sent from publisher!");
    CustomMessage localMessage = new CustomMessage(200, "a0000001", "Local message!");

    // Send a message to [cluster receiver] every second
    getVertx().setPeriodic(1000, _id -> {
      eventBus.send("cluster-message-receiver", clusterWideMessage, reply -> {
        if (reply.succeeded()) {
          CustomMessage replyMessage = (CustomMessage) reply.result().body();
          System.out.println("Received reply: "+replyMessage.getSummary());
        } else {
          System.out.println("No reply from cluster receiver");
        }
      });
    });


    // Deploy local receiver
    getVertx().deployVerticle(LocalReceiver.class.getName(), deployResult -> {
      // Deploy succeed
      if (deployResult.succeeded()) {
        // Send a message to [local receiver] every 2 second
        getVertx().setPeriodic(2000, _id -> {
          eventBus.send("local-message-receiver", localMessage, reply -> {
            if (reply.succeeded()) {
              CustomMessage replyMessage = (CustomMessage) reply.result().body();
              System.out.println("Received local reply: "+replyMessage.getSummary());
            } else {
              System.out.println("No reply from local receiver");
            }
          });
        });

      // Deploy failed
      } else {
        deployResult.cause().printStackTrace();
      }
    });
  }
}
