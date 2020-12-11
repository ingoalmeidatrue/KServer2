package com.br.konekta.transportLayer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.br.konekta.applicationLayer.AppServer;
import com.br.konekta.applicationLayer.Message;
import com.br.konekta.applicationLayer.Prefixes;
import com.br.konekta.networkLayer.Datagram;
import com.br.konekta.networkLayer.Network;

public class Tcp {

	public TcpReceiver tcpReceiver;
	public TcpSender tcpSender;
	public AppServer app;
	private Network network;
	private String myPort;
	public Map call0Sender;
	public Map call0Receiver;
	public Segment lastSegment;
	public int idUserSender = 0;
	public int idUserReceiver = 0;
	
	public Tcp(Network network){
		this.network=network;
		myPort = "1080";
		tcpReceiver = new TcpReceiver(myPort);
		tcpSender = new TcpSender(network,myPort);
		tcpReceiver.start();
		tcpSender.start();
		call0Sender = new HashMap<String, Integer>();
		call0Receiver = new HashMap<String, Integer>();
	}


	public TcpReceiver getTcpReceiver() {
		return tcpReceiver;
	}

	public void setTcpReceiver(TcpReceiver tcpReceiver) {
		this.tcpReceiver = tcpReceiver;
	}

	public TcpSender getTcpSender() {
		return tcpSender;
	}

	public void setTcpSender(TcpSender tcpSender) {
		this.tcpSender = tcpSender;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public AppServer getApp() {
		return app;
	}

	public void setApp(AppServer app) {
		this.app = app;
		this.tcpReceiver.setApp(app);
	}
	
	
	public void send(Message message){
		Segment item = null;
		lastSegment = null;
		
		
		
		if(call0Sender.get(message.getUser().getNome()).equals(0)) {
			item = new Segment(message,0,0, false, "1080", AppServer.socketOut.getDestinationPort());	
		}
		else {
			item = new Segment(message,1,1, false, "1080", AppServer.socketOut.getDestinationPort());
		}
		
		item.setChecksum(message.getPrefix()+message.getMensagem());
		
		lastSegment = item;
		tcpSender.getBufferSend().add(item);
		
		
		//tcpSender.getBufferSend().add(item);
	
	}
	
	public void receive(Segment segment, InetAddress destIp) {
		boolean exists = false;
		
		if(segment.message.getUser().getNome() != null) {
			
			if(call0Sender.size() > 0) {
				Iterator<Map.Entry<String, Integer>> entries = call0Sender.entrySet().iterator();
				
				while (entries.hasNext()) {
				    
					Map.Entry<String, Integer> entry = entries.next();
					
					if(entry.getKey().equals(segment.message.getUser().getNome())) {
						exists = true;
						break;
					}
					
				}
				
				if(!exists) {
				idUserSender = 0;
					call0Sender.put(segment.message.getUser().getNome(), idUserSender);
					
				}
					
			}else {
				idUserSender = 0;
				call0Sender.put(segment.message.getUser().getNome(), idUserSender);
				
			}
			
			exists = false;
			
			if(call0Receiver.size() > 0) {
				Iterator<Map.Entry<String, Integer>> entries = call0Receiver.entrySet().iterator();
				
				while (entries.hasNext()) {
				    
					Map.Entry<String, Integer> entry = entries.next();
					
					if(entry.getKey().equals(segment.message.getUser().getNome())) {
						
						exists = true;
						break;
					}
					
				}
				
				if(!exists) {
					idUserReceiver = 0;
					call0Receiver.put(segment.message.getUser().getNome(), idUserReceiver);
					
				}
					
			}else {
				idUserReceiver = 0;
				call0Receiver.put(segment.message.getUser().getNome(), idUserReceiver);
				
			}

		}
		
		if(segment.isAck) {
			
			AppServer.socketOut = new Socket("1080",destIp);
			
			if(segment.getAckNum() ==  (int) this.call0Sender.get(segment.getMessage().getUser().getNome())
					&& segment.getChecksum() == segment.verifyChecksum(segment.getMessage().getPrefix() 
							+ segment.getMessage().getMensagem())){
			
				if(call0Sender.get(segment.message.getUser().getNome()).equals(0)) {
					call0Sender.put(segment.message.getUser().getNome(), 1);						
				}
				else {
					call0Sender.put(segment.message.getUser().getNome(), 0);
				}
			}
			else {
				tcpSender.getBufferSend().add(lastSegment);
			}
		}
		//coloca no buffer o datagram para ser processado
		else {
			Segment ack = null;
			
			AppServer.socketOut = new Socket("1080",destIp);
			
			if(!segment.getMessage().prefix.equals(Prefixes.getConnect()))
				tcpReceiver.getBufferReceive().add(segment);
			
			String newDestinationPort = segment.getSourcePort();
			String newSourcePort = segment.getDestinationPort();
			if(call0Receiver.get(segment.message.getUser().getNome()).equals(0)) {
				ack = new Segment(segment.getMessage(), 0, 0, true, newSourcePort, newDestinationPort);
			}
			else {
				ack = new Segment(segment.getMessage(), 1, 1, true, newSourcePort, newDestinationPort);
			}
			ack.setChecksum(segment.getMessage().getPrefix() + segment.getMessage().getMensagem());
			tcpSender.getBufferSend().add(ack);
			if(call0Receiver.get(segment.message.getUser().getNome()).equals(0)) {
				call0Receiver.put(segment.message.getUser().getNome(), 1);
			}
			else {
				call0Receiver.put(segment.message.getUser().getNome(), 0);
			}
		}	
		
		//if(!segment.getMessage().prefix.equals(Prefixes.getConnect()))
			//tcpReceiver.getBufferReceive().add(segment);
			
	}

}


