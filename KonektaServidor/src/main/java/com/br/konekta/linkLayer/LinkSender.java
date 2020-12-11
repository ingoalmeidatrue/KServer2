package com.br.konekta.linkLayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.br.konekta.applicationLayer.AppServer;
import com.br.konekta.networkLayer.Datagram;
import com.google.gson.Gson;

public class LinkSender extends Thread {
	
	public ConcurrentLinkedQueue<Frame> bufferSend;
	public String myMAC; 
	public static DatagramPacket packetOut;

	public LinkSender(String myMAC){
		this.myMAC = myMAC;
		bufferSend = new ConcurrentLinkedQueue<Frame>();
		
	}


	public ConcurrentLinkedQueue getBufferSend() {
		return bufferSend;
	}


	public void setBufferSend(ConcurrentLinkedQueue bufferSend) {
		this.bufferSend = bufferSend;
	}


	public String getMyMAC() {
		return myMAC;
	}


	public void setMyMAC(String myMAC) {
		this.myMAC = myMAC;
	}


	@Override
	public void run() {
		
		while(true) {
			
			if(!bufferSend.isEmpty()) {
				
				Frame frame = bufferSend.peek();
				
				Gson gson = new Gson();
				
				String json = gson.toJson(frame);
				
				byte[] bufferOut = json.getBytes();
				
				packetOut = new DatagramPacket(bufferOut,bufferOut.length,
						frame.datagram.getIpDestination(),Integer.parseInt(frame.datagram.segment.getDestinationPort()));
				try {
					Link.serverSocket.send(packetOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				bufferSend.poll();
				//chama a camada fisica
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
		}
	}

}
