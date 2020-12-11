package com.br.konekta.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//import br.ufba.ufbasim.ufbasimtestes.sigadmin.model.Programa;
//import static br.ufba.ufbasim.ufbasimtestes.util.FileUtil.readFile;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;



public abstract class MeetingDao {

	   public static List<Meeting> loadListObjects(String fileName) {
		    List<Meeting> aux= null;
		    List<Meeting> reuniao = new ArrayList<Meeting>();

	        try {

	        	aux = Arrays.asList(new Gson().fromJson(readFile(fileName, Charset.defaultCharset()),Meeting[].class));
	        } catch (Exception ex) {
	          
	        }
	        
	        for(Meeting r : aux ) {
	        	reuniao.add(r);
	        }

	        return reuniao;
	    }
	 

	public static <E> void writerFile(String nomeArquivo, Collection<E> lista) throws IOException {
		Gson gson = new Gson();
		FileWriter writeFile = null;
		String json = gson.toJson(lista);
		try {
			writeFile = new FileWriter(nomeArquivo + ".json");
			// Escreve no arquivo conteudo do Objeto JSON
			writeFile.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeFile.close();
	}

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	

}
