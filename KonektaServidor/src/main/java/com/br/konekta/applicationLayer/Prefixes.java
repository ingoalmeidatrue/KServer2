package com.br.konekta.applicationLayer;

public abstract class Prefixes {
	
	protected final static String NUM_ONLINES = "@KONEKTAPREFIXO1";
	protected final static String MESSAGE = "@KONEKTAPREFIXO2";
	protected final static String MARK_MEETING = "@KONEKTAPREFIXO3";
	protected final static String SEE_MEETINGS = "@KONEKTAPREFIXO4";
	protected final static String DISCONNECT = "@KONEKTAPREFIXO5";
	protected final static String FULL ="@KONEKTAPREFIX06";
	protected final static String CONNECT ="@KONEKTAPREFIX07";
	
	
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
	public static String getFull() {
		return FULL;
	}
	public static String getConnect() {
		return CONNECT;
	}
	
}
