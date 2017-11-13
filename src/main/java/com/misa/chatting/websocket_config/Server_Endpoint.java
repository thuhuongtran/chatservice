package com.misa.chatting.websocket_config;

import com.misa.chatting.dbAccess.message.MessageDecoder;
import com.misa.chatting.dbAccess.message.MessageEncoder;


@javax.websocket.server.ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class Server_Endpoint {

}
