package com.br.konekta.linkLayer;

import com.br.konekta.networkLayer.Datagram;

public class Frame {

	public Datagram datagram;
	public String sourceMAC;
	public String destinationMAC;
	
	

	public String getSourceMAC() {
		return sourceMAC;
	}

	public void setSourceMAC(String sourceMAC) {
		this.sourceMAC = sourceMAC;
	}

	public String getDestinationMAC() {
		return destinationMAC;
	}

	public void setDestinationMAC(String destinationMAC) {
		this.destinationMAC = destinationMAC;
	}

	public Frame(Datagram datagram, String destinationMAC, String sourceMAC) {
		this.sourceMAC = sourceMAC;
		this.datagram = datagram;
		this.destinationMAC = destinationMAC;
	}

	public Datagram getDatagram() {
		return datagram;
	}

	public void setDatagram(Datagram datagram) {
		this.datagram = datagram;
	}
	
	
}
