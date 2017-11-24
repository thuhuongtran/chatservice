package com.misa.chatting.handler.actionsImp;


import com.misa.chatting.config.chat_socket.MessageType;
import com.misa.chatting.dao.SendMsgSingleUsers;
import com.misa.chatting.dbAccess.MessageDataAccess;
import com.misa.chatting.dbAccess.imp.MessageDataAccessImp;
import com.misa.chatting.handler.actions.BaseApiAction;
import com.misa.chatting.main.APILauncher;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.response.ErrorCode;
import com.misa.chatting.service.hazelcast_data_manage.PutData;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/*
* read file firstly
* then setFileDirectory- SECURITY
* --- check user security - get user info - push to hazelcast
* 1. get file from client - check uploading file successfully then do next things
* 2. store file in default directory
* 3. push file link to hazelcast - push data to DB
* 4. response throughout TextMsg obj - msg:ok- return file name to response
* */
public class SendFileHandler extends BaseApiAction {
    private static String fileLink;
    private static String fileName;
    public static Logger logger = LoggerFactory.getLogger(APILauncher.class.getName());
    MessageDataAccess msgDB = new MessageDataAccessImp();

    @Override
    public BaseResponse handle(HttpServerRequest request) throws Exception {
        // create response
        BaseResponse response;
        // get file - sender info
        String senderID = request.getHeader("senderID");
        String senderNick = request.getHeader("nickSender");
        String sendAva = request.getHeader("senderAvatar");
        String senderToken = request.getHeader("senderToken");
        String recceiverID = request.getHeader("receiverID");
        String receiverNick = request.getHeader("receiverNick");
        // get sending time
        Date date = new Date();
        String time = date.toString();
        // check null param - return error null param
        if (senderID == null || sendAva == null || senderNick == null || senderToken == null || recceiverID == null
                || receiverNick == null) {
            response = new SendMsgSingleUsers(senderID, recceiverID, senderNick, receiverNick, sendAva,
                    senderToken, time, MessageType.MSG_FILE);
            response.setError(ErrorCode.NULL_REQUEST_PARAM);
            logger.info(SendFileHandler.class.getName() + " null param");
        } else {
            // if not null param do these kind of thing
            response = new SendMsgSingleUsers();

            if (request.uri().equals("/sendFile/") || request.uri().equals("/sendFile")) {
                response.setError(ErrorCode.FAIL_UPLOAD_FILE);
                logger.info(SendFileHandler.class.getName() + " can not get action form.");
            } else if (request.uri().equals("/sendFile/uploadFile")) {
                // check fileLink is null or not
                if (response.getFileLink() == null) {
                    response.setError(ErrorCode.FAIL_UPLOAD_FILE);
                    logger.info(SendFileHandler.class.getName() + " Upload file is null.");

                } else {
                    // create response obj
                    response = new SendMsgSingleUsers(senderID, recceiverID, senderNick, receiverNick,
                            response.getFileLink(), sendAva, senderToken, time, MessageType.MSG_FILE); // work or not - file link

                    //push msg history to roomHistory
                    PutData.putMsgToRoom((SendMsgSingleUsers) response);
                    // push msg to DB
                    try {
                        msgDB.writeMsgToDB(msgDB.sendTxtSingleUserToMsg((SendMsgSingleUsers) response));
                    } catch (SQLException e) {
                        response.setError(ErrorCode.SQL_EXCEPTION);
                        logger.info(SendFileHandler.class.getName() + " SQL exception...");
                        e.printStackTrace();
                    } catch (IOException e) {
                        logger.info(SendFileHandler.class.getName() + " IO exception...");
                        e.printStackTrace();
                    }
                    response.setError(ErrorCode.SUCCESS);
                    logger.info(SendFileHandler.class.getName() + " Successfully upload file.");

                }
            }
        }
        return response;
    }
}
