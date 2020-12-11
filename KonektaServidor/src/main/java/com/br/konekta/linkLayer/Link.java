package com.br.konekta.linkLayer;

import java.net.DatagramSocket;
import java.net.SocketException;

import com.br.konekta.networkLayer.Datagram;
import com.br.konekta.networkLayer.Network;

public class Link  {
	
	public String myMAC;
	LinkReceiver linkReceiver;
	LinkSender linkSender;
	public Network network;
	public static DatagramSocket serverSocket;
	
	
	public Link() {
		this.myMAC="FA-28-19-75-FB-05";	
		this.linkReceiver= new LinkReceiver(myMAC);
		this.linkSender= new LinkSender(myMAC);
		
		try {
			serverSocket = new DatagramSocket(1080);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.linkReceiver.start();
		this.linkSender.start();
		

	}
	
	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
		this.linkReceiver.setNetwork(network);
	}



	public String getMyMAC() {
		return myMAC;
	}

	public void setMyMAC(String myMAC) {
		this.myMAC = myMAC;
		linkReceiver.setMyMAC(myMAC);
		linkSender.setMyMAC(myMAC);
	}

	public LinkReceiver getLinkReceiver() {
		return linkReceiver;
	}

	public void setLinkReceiver(LinkReceiver linkReceiver) {
		this.linkReceiver = linkReceiver;
	}

	public LinkSender getLinkSender() {
		return linkSender;
	}

	public void setLinkSender(LinkSender linkSender) {
		this.linkSender = linkSender;
	}
	
	public void send(Datagram datagram) {
		//ALTERAR MAC DESTINO
		String MACDestino = myMAC;
		
		Frame item = new Frame(datagram,MACDestino, myMAC);
		
		linkSender.getBufferSend().add(item);
	}
	
	
	public void receive(Frame frame) {
		//chamada da camada fisica
		linkReceiver.getBufferReceive().add(frame);
	}
}