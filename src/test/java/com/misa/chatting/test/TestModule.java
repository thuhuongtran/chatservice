package com.misa.chatting.test;

import com.misa.chatting.dao.TextMsg;

public class TestModule {
    /*
    * check filter security in BaseApiAction
    * */
    public static void main(String[] args) {
        TextMsg msg = new TextMsg("testing");
        System.out.println("First created msg: msg= "+msg.getMessage());
        msg.setMessage("Changed test");
        System.out.println("Changed msg: "+msg.getMessage());

    }
}
