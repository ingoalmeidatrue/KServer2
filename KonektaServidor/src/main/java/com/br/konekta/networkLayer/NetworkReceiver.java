package com.br.konekta.networkLayer;

import java.net.InetAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.br.konekta.applicationLayer.AppServer;
import com.br.konekta.transportLayer.Tcp;
import com.br.konekta.util.Main;

/*
 * 111 id de broadcast
 * 000 id padrÃ£o ( sem id)
 */
public class NetworkReceiver extends Thread {

	public ConcurrentLinkedQueue<Datagram> bufferReceive;
	public InetAddress myIP;
	public Tcp tcp;

	public Tcp getTcp() {
		return tcp;
	}

	public void setTcp(Tcp tcp) {
		this.tcp = tcp;
	}

	public NetworkReceiver(InetAddress myIP) {
		this.bufferReceive = new ConcurrentLinkedQueue<Datagram>();
		this.myIP = myIP;
	}

	public ConcurrentLinkedQueue<Datagram> getBufferReceive() {
		return bufferReceive;
	}

	public void setBufferReceive(ConcurrentLinkedQueue<Datagram> bufferReceive) {
		this.bufferReceive = bufferReceive;
	}

	public InetAddress getMyID() {
		return myIP;
	}

	public void setMyID(InetAddress myID) {
		this.myIP = myID;
	}

	@Override
	public void run() {
		while (true) {

			if (!bufferReceive.isEmpty()) {
				Datagram atual = bufferReceive.peek();
				bufferReceive.poll();
				if (atual.ipDestination.equals(this.myIP) || atual.ipDestination.equals("255.255.255.255")) {
					
					if(!atual.segment.isAck)
						AppServer.addUser(atual.ipSource, atual.getSegment().getSourcePort(), myIP);
					
					
					tcp.receive(atual.segment, atual.ipSource);

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

