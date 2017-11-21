package com.misa.chatting.handler.actionsImp;

import com.misa.chatting.chatHandler.ReceiveMessages;
import com.misa.chatting.dao.SendTextSingleUsers;
import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.dbAccess.UserDataAccess;
import com.misa.chatting.dbAccess.imp.UserDataAccessImp;
import com.misa.chatting.handler.actions.BaseApiAction;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.response.ErrorCode;
import com.misa.chatting.service.UserDataService;
import com.misa.chatting.service.hazelcast_data_manage.PutData;
import com.misa.chatting.service.serviceImp.UserDataServiceImp;
import com.misa.chatting.utils.Authentication;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Date;

/**
 * get messages from client and then start a room chat
 */
public class SendMessageHandler extends BaseApiAction {
    private Authentication authen = new Authentication();
    private UserDataAccess userDataManagement = new UserDataAccessImp();
    private UserDataService userDataService = new UserDataServiceImp();
    /*
    * send text
    * call whenever send a msg
    * */
    public static Logger logger = LoggerFactory.getLogger(SendMessageHandler.class.getName());

    /*
    * to start a new chat box
    * receiverID set to equal chatroomID
    * chat 2 single users
    * chatRoomName set to equal receiverName
    * */
    @Override
    public BaseResponse handle(HttpServerRequest request) throws Exception {

        // create response
        BaseResponse response;

        // get request param - check null params
        String senderID = request.getHeader("senderID");
        String senderNick = request.getHeader("nickSender");
        String sendAva = request.getHeader("senderAvatar");
        String senderToken = request.getHeader("senderToken");
        String recceiverID = request.getHeader("receiverID");
        String receiverNick = request.getHeader("receiverNick");
        String msg = request.getHeader("msg");
        // get sending time
        Date date = new Date();
        String time = date.toString();

        // check null param - return error null param
        if (senderID == null || sendAva == null || senderNick == null || senderToken == null || recceiverID == null
                || receiverNick == null || msg == null) {
            //response = new SimpleResponse();
            response = new SendTextSingleUsers(senderID,recceiverID,senderNick,receiverNick,msg,sendAva,senderToken, time);
            response.setError(ErrorCode.NULL_REQUEST_PARAM);
        }
        else{

            // set senderMessage obj
            // chat_roomId = senderId + receiverId

            response = new SendTextSingleUsers(senderID,recceiverID,senderNick,receiverNick,msg,sendAva,senderToken, time);
            // check security
            // set userID and user-status-token
           // UserRequest userReq = authen.checkToken(senderToken); // check sender token - security

            // test
            UserRequest userReq = new UserRequest();
            userReq.setUser_id(4);
            userReq.setStatus(0);
            if (userReq.getStatus() == ErrorCode.ACTIVE_TOKEN
                    || userReq.getStatus() == ErrorCode.UPDATE_TOKEN) {
                // get userData from user service then push to hazelcast
                long user_id = userReq.getUser_id();
                UserRequest userRequest = userDataService.getUserRequestFromID(userReq, user_id);
                PutData.putUserDataToHazel(userRequest);
                //call eventbus publish text messages - send msg to other member in chat room
                ReceiveMessages.sendMsg((SendTextSingleUsers) response);
                // put text message to hazelcast - roomHistory
                PutData.putMsgToRoom((SendTextSingleUsers) response);
                // if success response to json obj which has its msg is Sent - Da gui
               ((SendTextSingleUsers) response).setMsg("Da gui"); // while can not change
               // logger.info(((SendTextSingleUsers) response).getMsg());
               response.setError(ErrorCode.SUCCESS);

            } else if (userReq.getStatus() == ErrorCode.INVALID_TOKEN) {
                response.setError(ErrorCode.INVALID_TOKEN);
                System.out.println("Error invalid token");
                logger.info("Error invalid token");
            }

        }
        return response;
    }



}
