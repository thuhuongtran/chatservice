package com.misa.chatting.chatHandler.codec;

import com.misa.chatting.dao.TextMsg;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class Messages_codec implements MessageCodec<TextMsg, TextMsg>{
    /*
    * obj class to string then push to buffer
    * */
    @Override
    public void encodeToWire(Buffer buffer, TextMsg textMsg) {
        // use JsonObj
        JsonObject jsonToEncode = new JsonObject();
        jsonToEncode.put("msgID", textMsg.getMessageID());
        jsonToEncode.put("roomID", textMsg.getChatroomID());
        jsonToEncode.put("senderID", textMsg.getUserID());
        jsonToEncode.put("senderNick", textMsg.getNickname());
        jsonToEncode.put("senderAvatar", textMsg.getUserAvatar());
        jsonToEncode.put("msg", textMsg.getMessage());
        jsonToEncode.put("timeCreated", textMsg.getCreateAt());

        // encode obj to string
        String jsonToStr = jsonToEncode.encode();

        // get length of string
        int length = jsonToStr.getBytes().length;

        // write data into given buffer
        buffer.appendInt(length);
        buffer.appendString(jsonToStr);
    }

    /*
    * decode from buffer to obj class
    * */
    @Override
    public TextMsg decodeFromWire(int pos, Buffer buffer) {
        // message start from this pos - position of buffer
        int position = pos;

        // get length of json
        int length = buffer.getInt(position);

        //get json string by its length
        // jump 4 because getInt() == 4 bytes
        String jsonStr = buffer.getString(position+=4, position+=length);
        JsonObject jsonObj = new JsonObject(jsonStr);

        // get fields of class
        String msgID = jsonObj.getString("msgID");
        String roomID = jsonObj.getString("roomID");
        String senderID = jsonObj.getString("senderID");
        String senderNick = jsonObj.getString("senderNick");
        String senderAvatar = jsonObj.getString("senderAvatar");
        String msg = jsonObj.getString("msg");
        String timeCreated = jsonObj.getString("timeCreated");

        // create TextMsg obj
        return new TextMsg(msgID, roomID, senderID, senderNick, senderAvatar, msg, timeCreated);
    }
/*
* if msg sent locally to eventbus
* then this method is called for transform type S - send to R- receive
* */
    @Override
    public TextMsg transform(TextMsg textMsg) {
        return textMsg;
    }

    @Override
    public String name() {
        // each codec must have a unique name
        // this is to identify a codec when sending a msg and for unregistering codec
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        // always -1
        return -1;
    }
}
