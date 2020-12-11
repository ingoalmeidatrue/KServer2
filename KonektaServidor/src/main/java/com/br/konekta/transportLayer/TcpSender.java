package com.br.konekta.transportLayer;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.br.konekta.networkLayer.Network;

public class TcpSender extends Thread {
	public boolean call0;
	private Network network;
	public ConcurrentLinkedQueue<Segment> bufferSend;
	private String myPort;
	
	public TcpSender(Network network, String myPort) {
		this.myPort = myPort;
		this.network=network;
		bufferSend = new ConcurrentLinkedQueue<Segment>();
		call0 = true;
		
	}
	
	public ConcurrentLinkedQueue<Segment> getBufferSend() {
		return bufferSend;
	}
	
	public void setBufferSend(ConcurrentLinkedQueue<Segment> bufferSend) {
		this.bufferSend = bufferSend;
	}
	
	
	@Override
	public void run() {
		while (true) {
			if (!bufferSend.isEmpty()) {

				Segment segment = bufferSend.peek();
				bufferSend.poll();
				
				network.send(segment);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
		}
	}
	
}
