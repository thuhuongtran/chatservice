package com.misa.chatting.handler.actionsImp;

import com.misa.chatting.chatHandler.ReceiveMessages;
import com.misa.chatting.config.chat_socket.MessageType;
import com.misa.chatting.dao.Message;
import com.misa.chatting.dao.SendMsgSingleUsers;
import com.misa.chatting.dao.UserRequest;
import com.misa.chatting.dbAccess.MessageDataAccess;
import com.misa.chatting.dbAccess.imp.MessageDataAccessImp;
import com.misa.chatting.handler.actions.BaseApiAction;
import com.misa.chatting.main.APILauncher;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.response.ErrorCode;
import com.misa.chatting.service.UserDataService;
import com.misa.chatting.service.hazelcast_data_manage.PutData;
import com.misa.chatting.service.serviceImp.UserDataServiceImp;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Date;

/**
 * get messages from client and then start a room chat
 */
public class SendMessageHandler extends BaseApiAction {
    private UserDataService userDataService = new UserDataServiceImp();
    private MessageDataAccess msgDB = new MessageDataAccessImp();
    /*
    * send text
    * call whenever send a msg
    *
    * */
    public static Logger logger = LoggerFactory.getLogger(APILauncher.class.getName());

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
            response = new SendMsgSingleUsers(senderID,recceiverID,senderNick,receiverNick,msg,sendAva,senderToken, time,
                    MessageType.MSG_TEXT);
            response.setError(ErrorCode.NULL_REQUEST_PARAM);
        }
        else{

            // set senderMessage obj
            // chat_roomId = senderId + receiverId

            response = new SendMsgSingleUsers(senderID,recceiverID,senderNick,receiverNick,msg,sendAva,senderToken, time,
                    MessageType.MSG_TEXT);
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
                ReceiveMessages.sendMsg((SendMsgSingleUsers) response);
                // put text message to hazelcast - roomHistory
                PutData.putMsgToRoom((SendMsgSingleUsers) response);
                // put text msg to db
                msgDB.writeMsgToDB(msgDB.sendTxtSingleUserToMsg((SendMsgSingleUsers) response));
                // if success response to json obj which has its msg is Sent - Da gui
               ((SendMsgSingleUsers) response).setMsg("Da gui"); // while can not change
               // logger.info(((SendMsgSingleUsers) response).getMsg());
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
