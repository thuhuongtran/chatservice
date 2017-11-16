package com.misa.chatting.response;

/*
 * Bean class: contains error code
 * */
public class ErrorCode {

	// define constant error code here
	public static final int SUCCESS = 0;
	public static int SYSTEM_ERROR = 1;
	public static int HANDLER_NOT_FOUND = 2;
	public static int INVALID_TOKEN = 3;
	public static int INVALID_PARAMS = 4;
	public static int UPDATE_TOKEN=5;
	public static int ACTIVE_TOKEN=6;
	public static final int NOT_AUTHORISED = 7;
	public static final int SQL_EXCEPTION = 8;
	public static final int NULL_REQUEST_PARAM = 9;
}
