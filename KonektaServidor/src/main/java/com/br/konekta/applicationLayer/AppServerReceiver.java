package com.br.konekta.applicationLayer;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.br.konekta.util.Types;
	
	public class AppServerReceiver extends Thread {
		
		public ConcurrentLinkedQueue<Message> bufferReceive;
		public boolean end = false;
		public static boolean connected = false;
		public static boolean full = false;
			
		public AppServerReceiver() {
			this.bufferReceive = new ConcurrentLinkedQueue<Message>();
			
		}
		
		public ConcurrentLinkedQueue<Message> getBufferReceive() {
			return bufferReceive;
		}

		public void setBufferReceive(ConcurrentLinkedQueue<Message> bufferReceive) {
			this.bufferReceive = bufferReceive;
		}
		
		
		public void run() {
			
			while (true) {

				if (!bufferReceive.isEmpty()) {
					
					Message atual = bufferReceive.peek();
					
					bufferReceive.poll(); // descarte
					
					receive(atual);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}
				

			}
			
		}
		
		
		public void receive(Message message) {
			
			if(message.getPrefix().equals(Prefixes.getConnect())) {
				
				//Server is not full
				
				if (!AppServer.full) {
					AppServer.send(message, Types.getConnect());
					
				}
				
				//Server is full
				else {
					AppServer.send(message, Types.getFull());
				
				}
			}
			
			//Client wants to disconnect
			else if(message.getMensagem().indexOf(Prefixes.getDisconnect()) != -1) {
		
				AppServer.send(message, Types.getDisconnect());
				
				//AppServer.removeUser(message.getPort());
			}
			
			//Client wants to mark a meeting
			else if(message.getMensagem().indexOf(Prefixes.getMarkMeeting()) != -1) {

				//message = message.replaceAll(Prefixes.getMarkMeeting(), "");
				
				//List<Meeting> reunioes = MeetingDao.loadListObjects("REUNIOES.json");
				
				//message.replaceAll("\\u0000", "");
				
				//Meeting reuniao = new Meeting();
				
				//reuniao.setReuniao(message);
				
				//reunioes.add(reuniao);
				
				//MeetingDao.writerFile("REUNIOES", reunioes);
				
			}
			
			//Client wntas to see all meetings
			else if(message.getMensagem().indexOf(Prefixes.getSeeMeetings()) != -1) {
				
				
				//message = message.replaceAll(Prefixes.getSeeMeetings(), "");
				
				//String conjuntoReunioes = "";
				
				//int lugar = Integer.parseInt(message.trim());
				
				//List<Meeting> reunioes = MeetingDao.loadListObjects("REUNIOES.json");
				
				//Organiza todas reunioes em uma grande string
				//for(int i=lugar ; i <reunioes.size() ; i++) {
					
					//Meeting reuniao = reunioes.get(i);
					
					//conjuntoReunioes += reuniao.getReuniao();
				//}
				
				
				//conjuntoReunioes.replaceAll("\\u0000", "");
				
				//AppServer.send(Prefixes.getSeeMeetings()+conjuntoReunioes, currentUser);
		

			}
			
			//Client wants to send a message
			else if(message.getPrefix().equals(Prefixes.getMessage())) {
				
				AppServer.sendToAll(message);
			}
		}
	}

