package com.br.konekta.transportLayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.br.konekta.applicationLayer.AppServer;


public class TcpReceiver extends Thread {
	
	public ConcurrentLinkedQueue<Segment> bufferReceive;
	public AppServer app;
	private String myPort;
	
	public TcpReceiver(String myPort) {
		bufferReceive = new ConcurrentLinkedQueue<Segment>();
		this.myPort = myPort;
	}
	
	
	public AppServer getApp() {
		return app;
	}

	public void setApp(AppServer app) {
		this.app = app;
	}

	public ConcurrentLinkedQueue<Segment> getBufferReceive() {
		return bufferReceive;
	}

	public void setBufferReceive(ConcurrentLinkedQueue<Segment> bufferReceive) {
		this.bufferReceive = bufferReceive;
	}
	
	@Override
	public void run() {
		while (true) {
			if (!bufferReceive.isEmpty()) {

				Segment atual = bufferReceive.peek();
				bufferReceive.poll();
				
				if(atual.getDestinationPort().equals(myPort)) {
					app.receive(atual.message);
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
