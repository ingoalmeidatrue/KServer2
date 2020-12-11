package com.br.konekta.applicationLayer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.br.konekta.transportLayer.Socket;
import com.br.konekta.transportLayer.Tcp;
import com.br.konekta.util.Types;
import com.br.konekta.util.User;


public class AppServer {
		public static List<Socket> sockets;
		public static AppServerReceiver appReceiver;
		public static AppServerSender appSender;
		private Tcp tcp;
		public static Socket socketIn;
		public static Socket socketOut;
		public static String destinationPort;
		public static boolean full = false;
		
		public AppServer(Tcp tcp){
			
			sockets = new ArrayList<Socket>();
			this.tcp = tcp;
			appReceiver = new AppServerReceiver();
			appSender = new AppServerSender(tcp);
			appReceiver.start();
			appSender.start();
			
		}
		

		public static void send(Message m, String type){
			
			
			
		   switch (type) {
			
			case "connect":
				
				m.setPrefix(Prefixes.getConnect());
				socketOut = socketIn;
				break;
			
			case "sendMessage":
				
				m.setPrefix(Prefixes.getMessage());
				
				break;

			case "markMeetings":
				
				m.setPrefix(Prefixes.getMarkMeeting());
				
				break;

			/*case "seeMeetings":
			 * 
			 * message = message.replaceAll(Prefixes.getSeeMeetings(), "");
				
				String conjuntoReunioes = "";
				
				int lugar = Integer.parseInt(message.trim());
				
				List<Meeting> reunioes = MeetingDao.loadListObjects("REUNIOES.json");
				
				//Organiza todas reunioes em uma grande string
				for(int i=lugar ; i <reunioes.size() ; i++) {
					
					Meeting reuniao = reunioes.get(i);
					
					conjuntoReunioes += reuniao.getReuniao();
				}
				
				
				conjuntoReunioes.replaceAll("\\u0000", "");
				
				server.send(Prefixes.getSeeMeetings()+conjuntoReunioes, currentUser);*/
				
			case "full":
			
				m.setPrefix(Prefixes.getFull());
				socketOut = socketIn;
				break;
				
			case "disconnect":
				m.setPrefix(Prefixes.getDisconnect());
			
				break;
			}
		  
			appSender.getBufferSend().add(m);
		}
		
		
		public void receive(Message message) {
			//coloca no buffer o datagram para ser processado
			appReceiver.getBufferReceive().add(message);
				
		}
		
		public static void sendToAll(Message message) {
			
			for(Socket s : sockets) {
				
				if(!s.getDestinationPort().equals(socketIn.getDestinationPort())) {
			
					socketOut = new Socket(s.getDestinationPort(), s.getDestinationIp());
					send(message,Types.getMessage());
				}
					
			}
		}
		
		public static boolean userExists(String userPort, InetAddress ipUser) {
			
			if(sockets != null) {
				
				for(Socket s : sockets) {
		
					if(s.getDestinationPort().equals(userPort) && s.getDestinationIp().equals(ipUser)) {
	
						return true;
					}
				}
			}
			
			return false;
		}
		
		public static void addUser(InetAddress ipUser, String userPort, InetAddress ipServer) {
			
			socketIn = new Socket(userPort, ipUser);
			
			Socket s = new Socket(userPort, ipUser);
			
			if(sockets.size() < 2 && !userExists(userPort, ipUser)) { 		
				sockets.add(s);
			}
			else {
				full = true;
			}
		}
		
		public static void removeUser(int userPort) {
			/*Iterator<User> iter = users.iterator();
			
			while (iter.hasNext()) {
			    User u = iter.next();

			    if (u.getPort() == userPort) {
			        iter.remove();
			        break;
			    }
			}*/
		}
		
	}
