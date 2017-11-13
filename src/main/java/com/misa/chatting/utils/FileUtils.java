package com.misa.chatting.utils;

import com.google.gson.JsonObject;

import java.io.*;

public class FileUtils {
    public static JsonObject loadJsonFromFile(String fileName){
        File file = new File(fileName);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        try {
            Reader r = new InputStreamReader( new FileInputStream(file),"UTF-8");
            reader = new BufferedReader(r);
            String text = null;
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }
            return ChatUtils.toJsonObject(contents.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
