package com.misa.chatting.config.apiconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.misa.chatting.handler.actions.BaseApiAction;
import com.misa.chatting.utils.ChatUtils;

public class APIconfig {
	  public static int PORT = 0;
	    public static String MODE = "live";
	    private static String API_CONFIG_FILE = "config/api/api.json";
	    private static  Map<String, BaseApiAction> handlers = new HashMap<String, BaseApiAction>();
	    
	    // get path
	    public static BaseApiAction getHandler(String path){
	        return handlers.get(path);
	    }
	    
	    //initialize 
	    public static JsonObject config;
	    public static void init() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
	        initJson();
	        PORT = config.get("port").getAsInt();
	        MODE = config.get("mode").getAsString();
	        JsonArray arr = config.getAsJsonArray("api");
	        for(int i = 0; i < arr.size(); i++){
	            JsonObject obj = arr.get(i).getAsJsonObject();
	            String path = obj.get("path").getAsString(); // get path
	            String handlerClassPath = obj.get("handler").getAsString(); // get handle class
	            boolean isPublic = obj.get("isPublic").getAsBoolean(); 
	            JsonArray roleArray = obj.get("roles").getAsJsonArray();
	            String method = obj.get("method").getAsString();
	            BaseApiAction handler = (BaseApiAction) Class.forName(handlerClassPath).newInstance();
	            handler.setPath(path);
	            handler.setPublic(isPublic);
	           // actions.initRoles(roleArray);
	            handler.setMethod(method);
	            handlers.put(handler.getPath(), handler);
	        }

		}
	    
	  /*
	   * */
	    private static void initJson(){
	        File file = new File(API_CONFIG_FILE);
	        StringBuffer contents = new StringBuffer();
	        BufferedReader reader = null;
	        try {
	            Reader r = new InputStreamReader( new FileInputStream(file),"UTF-8");
	            reader = new BufferedReader(r);
	            String text = null;
	            while ((text = reader.readLine()) != null) {
	                contents.append(text).append(System.getProperty("line.separator"));
	            }
	            config = ChatUtils.toJsonObject(contents.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
