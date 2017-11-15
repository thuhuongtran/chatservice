package com.misa.chatting.config.vertx;

import com.misa.chatting.config.apiconfig.APIconfig;
import com.misa.chatting.handler.actions.BaseApiAction;
import com.misa.chatting.response.BaseResponse;
import com.misa.chatting.response.ErrorCode;
import com.misa.chatting.response.SimpleResponse;
import io.netty.util.AsciiString;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class VertxHttpServer extends AbstractVerticle implements Handler<HttpServerRequest> {

    static Logger logger = LoggerFactory.getLogger(VertxHttpServer.class.getName());
    private HttpServer server;
    private static final CharSequence RESPONSE_TYPE_JSON = new AsciiString("application/json");
    /*
	* override start method when extens AbstractVerticle
	* to call vertx - core
	* */

    @Override
    public void start() {
        int port = APIconfig.PORT;

        server = vertx.createHttpServer();
        // call sockjsBridge
        createEventBusBridge();

        server.requestHandler(VertxHttpServer.this).listen(port);
        logger.debug("start on port {}", port);
    }

    /*
    * config eventBus - sockJSBridge
    * */
    public SockJSHandler createEventBusBridge() {
        BridgeOptions options = new BridgeOptions()
                // add permitted HERE
                .addInboundPermitted(new PermittedOptions())
                .addOutboundPermitted(new PermittedOptions());
        return SockJSHandler.create(vertx).bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                logger.info("A socket was created");
                System.out.println("A socket was created");
            } else if (event.type() == BridgeEventType.SOCKET_CLOSED) {
                logger.info("Socket was closed");
                System.out.println("Socket was closed");
            } else {
                logger.info("Can not open a socket.");
            }
            event.complete(true);
        });

    }

    @Override
    public void handle(HttpServerRequest request) {
        BaseResponse response = null;
        BaseApiAction handler = APIconfig.getHandler(request.path()); // return path
        try {
            if (handler != null) {
                switch (handler.getMethod()) {
                    case "GET":
                        handleGet(handler, request);
                        break;
                    case "POST":
                        handlePOST(handler, request);
                        break;
                }
            } else {
                response = new SimpleResponse(ErrorCode.HANDLER_NOT_FOUND);
                makeHttpResponse(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new SimpleResponse(ErrorCode.SYSTEM_ERROR);
            makeHttpResponse(request, response);
        }
    }

    private void handleGet(BaseApiAction handler, HttpServerRequest request) throws Exception {
        BaseResponse response = null;
        if (handler.isPublic()) {
            // call the action handler
            response = handler.handle(request);
        } else {
            //String nickname = request.getParam("n");
            String accessToken = request.getParam("at");
            response = handlePrivateRequest(handler, request, accessToken);
        }
        makeHttpResponse(request, response);
    }

    private void handlePOST(BaseApiAction handler, HttpServerRequest request) throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        //
        router.route().handler(StaticHandler.create().setWebRoot("webroot"));
        request.setExpectMultipart(true);
        request.endHandler(req -> {
            BaseResponse response = null;
            try {
                if (handler.isPublic()) {
                    response = handler.handle(request);
                } else {
                    //String nickname = request.formAttributes().get("n");
                    String accessToken = request.formAttributes().get("at");
                    response = handlePrivateRequest(handler, request, accessToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = new SimpleResponse(ErrorCode.SYSTEM_ERROR);
            }
            makeHttpResponse(request, response);
        });
    }

    private BaseResponse handlePrivateRequest(BaseApiAction handler, HttpServerRequest request, String accessToken) throws Exception {
        BaseResponse response = null;
        if (accessToken != null) {
            // check authentication firstly
            boolean securityCheck = handler.filterSecutiry(accessToken);
            if (securityCheck) {
                response = handler.handle(request);
            } else {
                response = new SimpleResponse(ErrorCode.NOT_AUTHORISED);
            }
        } else {
            response = new SimpleResponse(ErrorCode.INVALID_PARAMS);
        }
        return response;
    }

    public void allowAccessControlOrigin(HttpServerRequest request) {
        request.response().putHeader("Access-Control-Allow-Origin", "*");
        request.response().putHeader("Access-Control-Allow-Credentials", "true");
        request.response().putHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
        request.response().putHeader("Access-Control-Allow-Headers",
                "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    }

    private void makeHttpResponse(HttpServerRequest request, BaseResponse response) {
        allowAccessControlOrigin(request);
        String content = response.toJonString();
        CharSequence contentLength = new AsciiString(String.valueOf(content.length()));
        Buffer contentBuffer = Buffer.buffer(content);
        request.response().putHeader("CONTENT_TYPE", RESPONSE_TYPE_JSON)
                .putHeader("CONTENT_LENGTH", contentLength).end(contentBuffer);
    }

    @Override
    public void stop() {
        if (server != null) server.close();
    }

}

