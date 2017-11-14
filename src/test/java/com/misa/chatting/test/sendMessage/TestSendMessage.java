package com.misa.chatting.test.sendMessage;

import com.misa.chatting.websocket_config.ChatSocket;
import com.misa.chatting.websocket_config.ChatSocketConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.glassfish.tyrus.client.ClientManager;


import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.function.Consumer;

public class TestSendMessage {
    private static Session session;
    private static ClientManager client;

    private static final String WEB_EXAMPLES_DIR = "web-examples";
    private static final String WEB_EXAMPLES_JAVA_DIR = WEB_EXAMPLES_DIR + "/src/main/java/";

    public static void main(String[] args) throws IOException, InterruptedException {
        /*
        callServerEndpoint("test1");
        System.out.println("success!");*/

        // test starting chatSocket
        ChatSocketConfig.init();
        System.out.println("chat socket config is initialized");
        runExample(ChatSocket.class);
        System.out.println("success");

    }

    public static void callServerEndpoint(String username) throws InterruptedException, IOException {
        /*client = null;
        session = null;
        String uri = "ws://localhost:8083/chat/" + username + "";
        try {
            client = ClientManager.createClient();
            session = client.connectToServer(Client_EndPoint.class, URI.create(uri));
            System.out.println("Connected successfully!");
            Thread.sleep(1000);
        } catch (DeploymentException e) {
            System.out.println("Can not connect.");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
                System.out.println("session id closed!");
            }
        }

*/
    }
    public static void runExample(Class clazz) {
        runExample(WEB_EXAMPLES_JAVA_DIR, clazz, new VertxOptions().setClustered(false), null);
    }

    public static void runExample(String exampleDir, Class clazz, VertxOptions options, DeploymentOptions
            deploymentOptions) {
        runExample(exampleDir + clazz.getPackage().getName().replace(".", "/"), clazz.getName(), options, deploymentOptions);
    }

    public static void runExample(String exampleDir, String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
        if (options == null) {
            // Default parameter
            options = new VertxOptions();
        }
        // Smart cwd detection

        // Based on the current directory (.) and the desired directory (exampleDir), we try to compute the vertx.cwd
        // directory:
        try {
            // We need to use the canonical file. Without the file name is .
            File current = new File(".").getCanonicalFile();
            if (exampleDir.startsWith(current.getName()) && !exampleDir.equals(current.getName())) {
                exampleDir = exampleDir.substring(current.getName().length() + 1);
            }
        } catch (IOException e) {
            // Ignore it.
        }

        System.setProperty("vertx.cwd", exampleDir);
        Consumer<Vertx> runner = vertx -> {
            try {
                if (deploymentOptions != null) {
                    vertx.deployVerticle(verticleID, deploymentOptions);
                } else {
                    vertx.deployVerticle(verticleID);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        if (options.isClustered()) {
            Vertx.clusteredVertx(options, res -> {
                if (res.succeeded()) {
                    Vertx vertx = res.result();
                    runner.accept(vertx);
                } else {
                    res.cause().printStackTrace();
                }
            });
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
    }
}
