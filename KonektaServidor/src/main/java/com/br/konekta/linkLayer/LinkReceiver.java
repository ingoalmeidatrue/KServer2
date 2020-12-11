package com.br.konekta.linkLayer;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.br.konekta.networkLayer.Network;
import com.br.konekta.util.Main;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class LinkReceiver extends Thread {

	public ConcurrentLinkedQueue<Frame> bufferReceive;
	public String myMAC;
	public Network network;
	public byte[] bufferIn;
	public static DatagramPacket packetIn;
	
	public LinkReceiver(String myMAC) {
		this.myMAC=myMAC;
		bufferReceive = new ConcurrentLinkedQueue<Frame>();
		

	}
	
	public ConcurrentLinkedQueue<Frame> getBufferReceive() {
		return bufferReceive;
	}

	public void setBufferReceive(ConcurrentLinkedQueue<Frame> bufferReceive) {
		this.bufferReceive = bufferReceive;
	}

	public String getMyMAC() {
		return myMAC;
	}

	public void setMyMAC(String myMAC) {
		this.myMAC = myMAC;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	@Override
	public void run() {
		
		while (true) {
			
			bufferIn = new byte[1024];
			
			packetIn = new DatagramPacket(bufferIn,bufferIn.length);
			
			try {
				Link.serverSocket.receive(packetIn);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			String dados = new String(packetIn.getData(),Charset.defaultCharset());

			Gson g = new Gson();
			JsonReader reader = new JsonReader(new StringReader(dados));
			reader.setLenient(true);
			
			Frame quadro = g.fromJson(reader,Frame.class);
			
			bufferReceive.add(quadro);
			
			
			if (!bufferReceive.isEmpty()) {
				
				Frame atual = bufferReceive.peek();
				bufferReceive.poll();
				

				if (atual.getDestinationMAC().equals(myMAC) || atual.getDestinationMAC().equals("FF-FF-FF-FF-FF-FF")) { 
					network.receive(atual.datagram);
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
		}
	}
}
