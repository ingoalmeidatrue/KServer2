package com.br.konekta.applicationLayer;
import java.util.Date;

import com.br.konekta.util.User;

import java.util.Date;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Message {

	public User user;
	public String data;
	public String mensagem;
	public String prefix;
	
	public Message(User user, String mensagem){
		this.user = user;
		this.mensagem = mensagem;
		
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
	    Date dataObj = new Date();
	    this.data = df.format(dataObj);
	     
	}


	public String getPrefix() {
		return prefix;
	}



	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}



	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


}
