package com.br.konekta.networkLayer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.br.konekta.applicationLayer.AppServer;
import com.br.konekta.linkLayer.Link;
import com.br.konekta.transportLayer.Segment;
import com.br.konekta.transportLayer.Socket;
import com.br.konekta.transportLayer.Tcp;

public class Network {
	
	public InetAddress localIp;
	public NetworkReceiver networkReceiver;
	public NetworkSender networkSender;
	public Tcp tcp;
	public Link link;
	
	
	public Network(Link link){
		this.link = link;
		try {
			this.localIp = InetAddress.getByName("192.168.0.107");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		networkReceiver = new NetworkReceiver(localIp);
		networkSender = new NetworkSender(link, localIp);
		networkReceiver.start();
		networkSender.start();
	}
	
	public Tcp getTcp() {
		return tcp;
	}

	public void setTcp(Tcp tcp) {
		this.tcp = tcp;
		this.networkReceiver.setTcp(tcp);
	}


	public void send(Segment segment){
		
		InetAddress destinationIp = null;
		
		for(Socket s : AppServer.sockets) {
			if(s.getDestinationPort() == segment.getDestinationPort()) {
				destinationIp = s.getDestinationIp();
				break;
			}
		}
		
		if(destinationIp == null) {
			destinationIp = AppServer.socketOut.getDestinationIp();
		}
		
		Datagram item = null;
		
		item = new Datagram(segment, this.localIp, destinationIp);
		
		networkSender.getBufferSend().add(item);
	}
	
	public void receive(Datagram datagram) {
		
		networkReceiver.getBufferReceive().add(datagram);
			
	}
	
	public void  setMyID(InetAddress myID){
		this.localIp = myID;
		this.networkReceiver.setMyID(myID);
		this.networkSender.setMyID(myID);
	}
}
