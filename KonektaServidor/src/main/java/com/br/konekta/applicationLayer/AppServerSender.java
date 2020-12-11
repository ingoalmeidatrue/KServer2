package com.br.konekta.applicationLayer;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.br.konekta.networkLayer.Datagram;
import com.br.konekta.transportLayer.Tcp;
import com.br.konekta.transportLayer.TcpReceiver;
import com.br.konekta.transportLayer.TcpSender;
import com.br.konekta.util.User;
import com.br.konekta.util.Meeting;
import com.br.konekta.util.MeetingDao;

public class AppServerSender  extends Thread{
	
	public ConcurrentLinkedQueue<Message> bufferSend;
	private Tcp tcp;
	
	public AppServerSender(Tcp tcp) {
		bufferSend = new ConcurrentLinkedQueue<Message>();
		this.tcp= tcp;
		
	}
	
	public ConcurrentLinkedQueue<Message> getBufferSend() {
		return bufferSend;
	}

	public void setBufferSend(ConcurrentLinkedQueue<Message> bufferSend) {
		this.bufferSend = bufferSend;
	}
	
	@Override
	public void run() {
		
		while (true) {
			
			if (!bufferSend.isEmpty()) {

				Message message = bufferSend.peek();
				bufferSend.poll();

				tcp.send(message);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
		}
	}
				

	
}
