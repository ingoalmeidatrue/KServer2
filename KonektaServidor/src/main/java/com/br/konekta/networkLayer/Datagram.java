package com.br.konekta.networkLayer;

import java.net.InetAddress;

import com.br.konekta.transportLayer.Segment;

public class Datagram {
	
	public InetAddress ipSource;
	public InetAddress ipDestination;
	public Segment segment;
	
	//segmento
	
	public Datagram(Segment segment, InetAddress ipSource,InetAddress ipDestination){
		this.ipSource = ipSource;
		this.ipDestination = ipDestination;
		this.segment = segment;
	}

	public InetAddress getIpSource() {
		return ipSource;
	}

	public void setIpSource(InetAddress ipSource) {
		this.ipSource = ipSource;
	}

	public InetAddress getIpDestination() {
		return ipDestination;
	}

	public void setIpDestination(InetAddress ipDestination) {
		this.ipDestination = ipDestination;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	
	
}

