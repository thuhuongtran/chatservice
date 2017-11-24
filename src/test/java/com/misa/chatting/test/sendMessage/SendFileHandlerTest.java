package com.misa.chatting.test.sendMessage;

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


public class SendFileHandlerTest extends BaseApiAction{
    private static String fileLink;
    private static String fileName;
    public static Logger logger = LoggerFactory.getLogger(APILauncher.class.getName());
    private static BaseResponse response;
    MessageDataAccess msgDB = new MessageDataAccessImp();

    @Override
    public BaseResponse handle(HttpServerRequest request) throws Exception {
        // create response
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
            logger.info("null param");
        } else {
            response = new SendMsgSingleUsers(senderID, recceiverID, senderNick, receiverNick, sendAva,
                    senderToken, time, MessageType.MSG_FILE);
            response.setError(ErrorCode.TEST);
            // if not null param do these kind of thing
            // get upload file
            if(request.uri().startsWith("/")){
                response.setError(ErrorCode.FAIL_UPLOAD_FILE);
                logger.info("can not get action form.");
            }
            else if (request.uri().startsWith("/uploadFile")) {
                request.setExpectMultipart(true);
                //BaseResponse tempResponse ; // temp response
                request.uploadHandler(upload -> {
                    upload.exceptionHandler(cause -> {
                        // upload fail - set error
                        response.setError(ErrorCode.FAIL_UPLOAD_FILE);
                        // request.response().setChunked(true).end("{e:"+ErrorCode.FAIL_UPLOAD_FILE+"}");
                        logger.info("upload file fail.");
                    });
                    upload.endHandler(event -> {
                        // successfully upload file
                        // request.response().setChunked(true).end("{e:"+ErrorCode.SUCCESS+"}");
                    });
                    // store in a directory
                    fileName = upload.filename();
                    fileLink = "upload/file/" + fileName;
                    upload.streamToFileSystem(fileLink);

                    response.setError(ErrorCode.UPLOAD_FILE_SUCCESS);
                    logger.info("Successfully upload file.");

                    // request.response().setChunked(true).end("{fileLink:"+fileLink+",e:"+ErrorCode.SUCCESS+"}");
                });
            }
            // create response obj
            response = new SendMsgSingleUsers(senderID, recceiverID, senderNick, receiverNick,
                    fileLink, sendAva, senderToken, time, MessageType.MSG_FILE);
            //push msg history to roomHistory
            PutData.putMsgToRoom((SendMsgSingleUsers) response);
            // push msg to DB
            try {
                msgDB.writeMsgToDB(msgDB.sendTxtSingleUserToMsg((SendMsgSingleUsers) response));
            } catch (SQLException e) {
                response.setError(ErrorCode.SQL_EXCEPTION);
                logger.info("SQL exception...");
                e.printStackTrace();
            } catch (IOException e) {
                logger.info("IO exception...");
                e.printStackTrace();
            }
        }
        return response;
    }

}
