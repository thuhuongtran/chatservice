package com.misa.chatting.config.apiconfig;

import com.google.gson.JsonObject;
import com.misa.chatting.utils.FileUtils;

/*
* apiconfig to run in multiple platform
* change latter
* */
public class PlatformConfig {
    public static final String PLATFORM_WEB = "web";
    public static final String PLATFORM_ANDROID = "ad";
    public static final String PLATFORM_IOS = "ios";

    public static JsonObject config_android = null;
    public static JsonObject config_ios = null;
    public static JsonObject config_web = null;

    public static JsonObject config_common = null;

    public static JsonObject getConfig(String platform, String version){
        switch (platform){
            case  PLATFORM_WEB:
                return config_web;
            case PLATFORM_ANDROID:
                return config_android;
            case PLATFORM_IOS:
                return config_ios;
        }
        return null;
    }
    public static void loadConfig(String mode){
        switch (mode){
            case "local":
                config_web = FileUtils.loadJsonFromFile("apiconfig/platform/local/config_web.json");
                config_android = FileUtils.loadJsonFromFile("apiconfig/platform/local/config_android.json");
                config_ios = FileUtils.loadJsonFromFile("apiconfig/platform/local/config_ios.json");
                config_common = FileUtils.loadJsonFromFile("apiconfig/platform/local/config_common.json");
                break;
            case "test":
                config_web = FileUtils.loadJsonFromFile("apiconfig/platform/test/config_web.json");
                config_android = FileUtils.loadJsonFromFile("apiconfig/platform/test/config_android.json");
                config_ios = FileUtils.loadJsonFromFile("apiconfig/platform/test/config_ios.json");
                config_common = FileUtils.loadJsonFromFile("apiconfig/platform/test/config_common.json");
                break;
            default:
                config_web = FileUtils.loadJsonFromFile("apiconfig/platform/live/config_web.json");
                config_android = FileUtils.loadJsonFromFile("apiconfig/platform/live/config_android.json");
                config_ios = FileUtils.loadJsonFromFile("apiconfig/platform/live/config_ios.json");
                config_common = FileUtils.loadJsonFromFile("apiconfig/platform/live/config_common.json");
                break;
        }
    }

    public static JsonObject getConfigCommon() {
        return config_common;
    }
}
