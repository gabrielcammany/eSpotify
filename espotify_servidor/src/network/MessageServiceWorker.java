package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MessageServiceWorker implements Runnable{

	private MessageService mService;
	private ServerSocket sServer;
	private Socket sClient;
	private DataInputStream diStream;
	private boolean active;

	public MessageServiceWorker(MessageService mService, ServerSocket sServer) {
		this.mService = mService;
		this.sServer = sServer;
		active = true;
	}

	// Escolta peticions de connexio i llegeix els missatjges dels clients
	public void run() {
		String[] aux;
		String password;
		String[] data;
		String user;
		while (active) {
			try {
				// Esperem peticions de connexio
				sClient = sServer.accept();
				// Atenem les connexions
				diStream = new DataInputStream(sClient.getInputStream());
				String newMessage = diStream.readUTF();
				aux = newMessage.split("/");
				data = aux[0].split(":");
				user = data[1];
				password = desencripta(data[1].getBytes());
				// Informem a MessageService que sha rebut un nou missatge
				// ell informara al controlador i el controlador actualitzara la vista.
				mService.messageReceived("[" + getCurrentTime()+ "] " + "Usuari:" + user + "Contrasenya" + password);
				// Tanquem el socket del client
				sClient.close();
			} catch (IOException e) { }
		}
	}

	// Operacio privada per generar la data de recepcio dels missatges
	private String getCurrentTime() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public void stopListening() {
		active = false;
	}
	
	public String desencripta(byte[] s){
		String keyString = "KA839KJsdDa4sdJSNsdjasid!@$@#$#@$#*&(*&}{234hjuk32432432dsfsdf";
		String desencriptat = null;
        MessageDigest digest;
		try {
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        byte[] iv = new byte[cipher.getBlockSize()];
	        new SecureRandom().nextBytes(iv);
	        IvParameterSpec ivSpec = new IvParameterSpec(iv);
			digest = MessageDigest.getInstance("SHA-256");
	        digest.update(keyString.getBytes());
	        byte[] key = new byte[16];
	        System.arraycopy(digest.digest(), 0, key, 0, key.length);
	        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
	        
	        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
	        byte[] decrypted = cipher.doFinal(s);
	        desencriptat = new String(decrypted, "UTF-8");
	        System.out.println("decrypted: " + s);
	        
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return desencriptat;
	}

}
