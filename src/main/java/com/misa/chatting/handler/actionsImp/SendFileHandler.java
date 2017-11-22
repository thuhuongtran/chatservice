package com.misa.chatting.handler.actionsImp;

import com.misa.chatting.dao.SendFileSingleUser;
import com.misa.chatting.dao.SendTextSingleUsers;
import com.misa.chatting.handler.actions.BaseApiAction;
import com.misa.chatting.main.APILauncher;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.response.ErrorCode;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.util.Date;
import java.util.Set;

/*
* read file firstly
* then setFileDirectory
* --- check user security - get user info - push to hazelcast
* 1. get file from client
* 2. store file in default directory
* 3. push file link to hazelcast
* 4. response throughout TextMsg obj - msg:ok- return file name to response
* */
public class SendFileHandler extends BaseApiAction{
    public static Logger logger = LoggerFactory.getLogger(APILauncher.class.getName());

    @Override
    public BaseResponse handle(HttpServerRequest request) throws Exception {
        // create response
        BaseResponse response = new SendFileSingleUser();
        // get file - sender info
        String senderID = request.getHeader("senderID");
        String senderNick = request.getHeader("nickSender");
        String sendAva = request.getHeader("senderAvatar");
        String senderToken = request.getHeader("senderToken");
        String recceiverID = request.getHeader("receiverID");
        String receiverNick = request.getHeader("receiverNick");
        // get upload file
        if(request.uri().startsWith("/uploadFile")){
            request.setExpectMultipart(true);
            BaseResponse tempResponse = response; // temp response
            request.uploadHandler(upload->{
                upload.exceptionHandler(cause->{
                  // upload fail - set error
                    tempResponse.setError(ErrorCode.FAIL_UPLOAD_FILE);
               });
                return tempResponse;
                upload.endHandler(event -> {
                    // successfully upload file
                   logger.info("Successfully upload file.");
                });
                // store in a directory
                upload.streamToFileSystem("upload/file/"+upload.filename());
            });
        }
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
        else {


        }
        return response;
    }
}
