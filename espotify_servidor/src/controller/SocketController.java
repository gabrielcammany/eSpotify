package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ConnectionPropertiesTransform;

import model.Canco;
import model.Query;
import model.User;
import network.ConectorDB;
import network.MessageServiceWorker;


public class SocketController {
	
	public ConectorDB conn = new ConectorDB("dpo_root", "sinminus", "bd_espotifi", 3306);
	
	public SocketController() {
		
	}
	
	public String verifyUser(User user){
		String response;
		Query q =new Query();
		
		String select = q.queryList(2, user);
		System.out.println("## "+select+" ##");
		response= selectUser(select);
		return response;
	}
	
	

	public void registroUsuario(String usuario, String password){
		User user =new User(usuario,password);
		
		//Comprovamos el nombre de usuario pero no validamos la contrase�a
		String result = verifyUser(user);
		if(result.equals("error")){
			Query q = new Query();
			String response;
			String cad = q.queryList(0, user);
			response = q.queryList(1, user);

			conn.insertQuery(cad);
			
			System.out.println("User: '"+user.getNickname()+"' Inserit correctament.");
		}else{
			System.out.println("[Servidor] L'usari '"+user.getNickname()+"' ja es troba registrat.");
		}
			
			//System.out.println(user.verifyUser(user));
		
	}
	
	public void loginUser(String usuario, String password){
		User user =new User(usuario,password);

		String result = verifyUser(user);
		if(result.equals("null"))System.out.println("[Servidor] L'usuari no es troba registrat");
		//si user no es troba registrat cal notificar al client.

	}
	
	
	public String selectUser(String query){
		ResultSet responseServer = conn.selectQuery(query);
		String nickname= null;
		int i = 0 ;
		try {
			while (responseServer.next()) {
				i++;
				nickname = responseServer.getString("nickname");
				System.out.println("[Servidor] Response server: '"+nickname+"'." );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(i>1)return nickname;
		return "error";
	}
	
	
	public ArrayList<Canco> selectSongs(){
		
		Query q = new Query();
		ResultSet responseServer = conn.selectQuery(q.queryList(4, null));
		ArrayList<Canco> alMusica = new ArrayList<Canco>();
		try {
			
			while (responseServer.next()) {
				Canco c = new Canco();
				c.setNom(responseServer.getString("nom"));
				c.setAlbum(responseServer.getString("album"));
				c.setArtista(responseServer.getString("artista"));
				c.setPath(responseServer.getString("ubicacio"));
				c.setEstrelles(responseServer.getString("num_estrelles"));
				System.out.println("[Servidor] "+c.getNom()+" amb path: "+c.getPath());
				alMusica.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int size = alMusica.size();
		for(int i = 0;i<size;i++)System.out.println("Canco numero "+i+" -->"+alMusica.get(i).getNom());
		
		return alMusica;
	}
	
}