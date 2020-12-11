package com.br.konekta.util;

public abstract class Types {
	
	protected final static String NUM_ONLINES = "numOnlines";
	protected final static String MESSAGE = "sendMessage";
	protected final static String MARK_MEETING = "markMeetings";
	protected final static String SEE_MEETINGS = "seeMeetings";
	protected final static String DISCONNECT = "disconnect";
	protected final static String CONNECT ="connect";
	protected final static String FULL ="full";
	
	
	public static String getFull() {
		return FULL;
	}
	public static String getNumOnlines() {
		return NUM_ONLINES;
	}
	public static String getMessage() {
		return MESSAGE;
	}
	public static String getMarkMeeting() {
		return MARK_MEETING;
	}
	public static String getSeeMeetings() {
		return SEE_MEETINGS;
	}
	public static String getDisconnect() {
		return DISCONNECT;
	}
	public static String getConnect() {
		return CONNECT;
	}
	
	
	
}
