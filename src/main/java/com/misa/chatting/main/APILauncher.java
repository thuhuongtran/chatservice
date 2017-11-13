package com.misa.chatting.main;

import com.misa.chatting.config.apiconfig.APIconfig;
import com.misa.chatting.config.hazelcast.HazelcastClientFactory;
import com.misa.chatting.config.hazelcast.HazelcastConfig;
import com.misa.chatting.config.vertx.VertxHttpServer;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APILauncher {
	private static Logger logger = LoggerFactory.getLogger(APILauncher.class.getName());
	public static String sessionID = "";

	public static void main(String[] args) {
		runVertx();
	}

	/*
	 * run vertx
	 */
	public static void runVertx() {
		try {
			PropertyConfigurator.configure("config/log4j.properties");
			logger.info("Logger is initialed");
			APIconfig.init();
			logger.info("APIConfig is initialed");

			HazelcastConfig.init();
			HazelcastConfig.startHazelcastServer();
			logger.info("HazelServer is started.");
			HazelcastClientFactory.start();
			logger.info("HazelcastClientFactory is initialized.");

			/*
			// connect to chat service database
			ChatServiceConnect.init();
			logger.info("ChatServiceConnect is initialed");

			//  connect to user service database
			UserServiceConnect.init();
			logger.info("UserServiceConnect is initialed");
*/
			int procs = Runtime.getRuntime().availableProcessors();

			Vertx vertx = Vertx.vertx();
			// to deploy
			vertx.deployVerticle(VertxHttpServer.class.getName(), new DeploymentOptions().setInstances(procs * 2),
					event -> {
						if (event.succeeded()) {
							logger.debug("Your Vert.x application is started!");
							System.out.println("Your Vert.x application is started!");
						} else {
							logger.error("Unable to start your application", event.cause());
							System.out.println("Unable to start your application" + event.cause());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:" + e.toString());
		}
	}
}
