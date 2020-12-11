package com.br.konekta.transportLayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.br.konekta.applicationLayer.Message;

public class Segment {
	
	public Message message;
	public int seqNum;
	public int ackNum;
	public int checksum;
	private String sourcePort;
	private String destinationPort;
	public boolean isAck; //flag
	
	private static final int LENGTH_CHECKSUM = 4;
	private static final int LENGTH_SEQNUM = 4;
	private static final int LENGTH_PREFIX = 4;
	private static final int OFFSET_CHECKSUM = 17;
	private static final int OFFSET_SEQNUM = 16;
	private static final int OFFSET_PREFIX = 15;
	
	public Segment(Message message, int ackNum, int seqNum, boolean isAck, String sourcePort, 
			String destinationPort) {
		this.message = message;
		this.destinationPort = destinationPort;
		this.sourcePort = sourcePort;
		this.ackNum = ackNum;
		this.seqNum = seqNum;
		this.isAck = isAck;
	}


	public String getSourcePort() {
		return sourcePort;
	}


	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}



	public String getDestinationPort() {
		return destinationPort;
	}



	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}



	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}



	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public int getAckNum() {
		return ackNum;
	}

	public void setAckNum(int ackNum) {
		this.ackNum = ackNum;
	}

	public int getChecksum() {
		return checksum;
	}

	public void setChecksum(String message) {
		
		int asciiInt; 
		int sum = 0; 
		boolean last = false;
		
		for (int i = 0; i < message.length(); i++) { 
					asciiInt = (int) message.charAt(i); 
						sum = sum + asciiInt; 
				 
					if ((int) message.charAt(i) == 46) { 
			 				last = true; 
					} 
		} 
				
		this.checksum = sum;
		    
	}
	public int verifyChecksum(String message2) {
		
		int asciiInt; 
		int sum = 0; 
		boolean last = false;
		
		for (int i = 0; i < message2.length(); i++) { 
					asciiInt = (int) message2.charAt(i); 
						sum = sum + asciiInt; 
				 
					if ((int) message2.charAt(i) == 46) { 
			 				last = true; 
					} 
		} 	
		return sum;
		    
	}
	
	
}