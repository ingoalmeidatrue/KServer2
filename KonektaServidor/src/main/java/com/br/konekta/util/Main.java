package com.br.konekta.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.br.konekta.applicationLayer.AppServer;
import com.br.konekta.linkLayer.Link;
import com.br.konekta.networkLayer.Network;
import com.br.konekta.transportLayer.Socket;
import com.br.konekta.transportLayer.Tcp;

public class Main {
	
	public static AppServer server;
	
	public static void main(String[] args)  {
		
		Link link = new Link();
		Network network = new Network(link);
		Tcp tcp = new Tcp(network);
		server = new AppServer(tcp);
		link.setNetwork(network); 
		network.setTcp(tcp);
		tcp.setApp(server);
		
	}
}
