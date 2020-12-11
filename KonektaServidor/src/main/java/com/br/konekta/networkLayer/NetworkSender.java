package com.br.konekta.networkLayer;

import java.net.InetAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.br.konekta.linkLayer.Link;

public class NetworkSender extends Thread {

	public ConcurrentLinkedQueue<Datagram> bufferSend;
	public InetAddress myID;
	public Link link;
	
	public NetworkSender(Link link, InetAddress myID) {
		this.bufferSend = new ConcurrentLinkedQueue<Datagram>();
		this.link = link;
		this.myID = myID;
	}

	
	public ConcurrentLinkedQueue<Datagram> getBufferSend() {
		return bufferSend;
	}

	public void setBufferSend(ConcurrentLinkedQueue<Datagram> bufferSend) {
		this.bufferSend = bufferSend;
	}

	public InetAddress getMyID() {
		return myID;
	}

	public void setMyID(InetAddress myID) {
		this.myID = myID;
	}

	@Override
	public void run() {
		while (true) {
			if (!bufferSend.isEmpty()) {

				Datagram datagrama = bufferSend.peek();
				bufferSend.poll();

				link.send(datagrama);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
		}
	}

}
