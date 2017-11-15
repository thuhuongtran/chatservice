package com.misa.chatting.handler.actionsImp;

import com.misa.chatting.chatHandler.ReceiveMessages;
import com.misa.chatting.dao.SendMessage;
import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.dbAccess.UserDataAccess;
import com.misa.chatting.dbAccess.imp.UserDataAccessImp;
import com.misa.chatting.handler.actions.BaseApiAction;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.response.ErrorCode;
import com.misa.chatting.response.SimpleResponse;
import com.misa.chatting.service.UserDataService;
import com.misa.chatting.service.hazelcast_data_manage.PutData;
import com.misa.chatting.service.serviceImp.UserDataServiceImp;
import com.misa.chatting.utils.Authentication;
import com.misa.chatting.utils.ChatUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * get messages from client and then start a room chat
* */
public class SendMessageHandler extends BaseApiAction {
    private Authentication authen = new Authentication();
    private UserDataAccess userDataManagement = new UserDataAccessImp();
    private UserDataService userDataService = new UserDataServiceImp();

    /*
    * send text 
    * */
    public static Logger logger = LoggerFactory.getLogger(SendMessageHandler.class.getName());

    @Override
    public BaseResponse handle(HttpServerRequest request) throws Exception {
        BaseResponse response = new SimpleResponse();
        response.setError(555);
        return response;
    }
    @Override
    public BaseResponse handler(String bodyReq) throws Exception {
        // get info from string body
        SendMessage sendMessage = ChatUtils.jsonToSendMessage(bodyReq); // not confirm -  wrong have to change to message obj
        // check filter security - return UserID
        UserRequest userReq = authen.checkToken(sendMessage.getSenderToken()); // check sender token - security
        if(userReq.getStatus()== ErrorCode.ACTIVE_TOKEN
                ||userReq.getStatus()== ErrorCode.UPDATE_TOKEN){
            // get userData from user service then push to hazelcast
            long user_id = userReq.getUser_id();
            PutData.putUserDataToHazel(userDataService.getUserRequestFromID(userReq, user_id));
            //call eventbus publish text messages
            ReceiveMessages.sendMsg(sendMessage);
            // response
            BaseResponse response = new SimpleResponse();
            response.setError(332);
        }
        else if(userReq.getStatus()==ErrorCode.INVALID_TOKEN){
            BaseResponse response = new SimpleResponse();
            response.setError(333);
            System.out.println("Error invalid");
        }
        else{
            BaseResponse response = new SimpleResponse();
            response.setError(335);
        }
        // get userData from userID
        // start a chat room:
        /*
        *1. call @onOpen: add 1 session
        * 2.send message obj - json to client
        *
        * */
        return null;
    }


}
