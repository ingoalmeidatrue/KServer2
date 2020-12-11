package com.br.konekta.transportLayer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Socket {
	
	private  final String sourcePort = "1080";
	private  String destinationPort;
	private  InetAddress sourceIp;
	private  InetAddress destinationIp;
	
	public Socket(String destinationPort, InetAddress destinationIp) {
		
		this.destinationPort = destinationPort;
		this.destinationIp = destinationIp;
		
	}
	
	

	public String getSourcePort() {
		return sourcePort;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	public InetAddress getSourceIp() {
		
		try {
			sourceIp = InetAddress.getByName("192.168.0.107");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sourceIp;
	}

	public void setSourceIp(InetAddress sourceIp) {
		this.sourceIp = sourceIp;
	}

	public InetAddress getDestinationIp() {
		return destinationIp;
	}

	public void setDestinationIp(InetAddress destinationIp) {
		this.destinationIp = destinationIp;
	}
	
	
	
	
}
