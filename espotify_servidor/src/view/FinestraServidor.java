package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;


/**
 * Creem la interficie del seervido composada per la "Gestio dels usuaris" i 
 * la "Gestió de música"
 * @author jorgemelguizo
 *
 *
 * 
 **/
public class FinestraServidor extends JFrame {
	
	private JPanel jpUsuari;
	
	private JPanel jpMusica;
	private Estadistica estadistica;

	
	
	public FinestraServidor() {
		JFrame jfServidor = new JFrame("SPOTYFAI - Servidor");
		
		jfServidor.setSize(900,500);
		jfServidor.setResizable(true);
		jfServidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfServidor.setLocationRelativeTo(null);;
		jfServidor.setVisible(true);
		
		
		JTabbedPane jtpServidor = new JTabbedPane();
		//tab user
		jpUsuari = new JPanel();
		jpUsuari = FinestraUsuari();
		//tab music
		jpMusica = new JPanel();
		jpMusica = FinestraMusica();
		
		jtpServidor.addTab("Usuari", jpUsuari);
		jtpServidor.addTab("Musica", jpMusica);	
		
		jfServidor.add(jtpServidor);
	}
	/**
	 * La finestra de l'usuari et mostra els usuaris de la BBDD
	 * 
	 * @author jorgemelguizo
	 * 
	 **/
	
	public JPanel FinestraUsuari(){
		jpUsuari = new JPanel(new BorderLayout());
		//connectar amb base de dades 
		
		/*
		 * temporal, vinda de la bbdd
		 */
		Object info[][] = { { "Carla medio metro", "28/03/1998", "29/06/2015", "0", "1", "0", "9", "0" },
				{ "Jorge te queremos", "14/06/1994", "29/06/2015", "2", "1", "200983248", "6", "0" },
				{ "Problem manager", "05/08/95", "05/08/95", "4", "7", "3", "89", "4"},
				{ "Gabri hombre socket", "05/00/95", "05/08/95", "4", "7", "2", "69", "4"},
				{ "Ivan casper", "05/00000/95", "05/08/95", "4", "7", "2", "69", "4"}
		};
		
		
		/*
		 * fiTemporal
		 */
		
		Object titol[] = { "Nom usuari", "Data registre", "Ultim accès", "Numero de llistes", "Numero de cançons",
				"Numero followers", "Numero following"};
	    JTable taulaUsuari = new JTable(info, titol);
	   

		JScrollPane jspUsuari = new JScrollPane(taulaUsuari);
		
		jpUsuari.add(jspUsuari, BorderLayout.CENTER);
		
		return jpUsuari;
	}
	
	public JPanel FinestraMusica(){
		
		jpMusica = new JPanel(new BorderLayout());
		JTabbedPane jtpMusica = new JTabbedPane();
		
		JPanel jpLlistat = new JPanel(new BorderLayout());
		JPanel jpAddicio = new JPanel();
		JPanel jpEstadistiques = new JPanel(new BorderLayout());
		
		
		
		//****** Opcio Llistat ******
		jpLlistat.add(LlistarMusica(), BorderLayout.CENTER);
		
	
		//****** Opcio Addicio ******
		jpAddicio = AddicioMusica();
		
		//****** Opcio Estadistiques ******
		jpEstadistiques.add(generaEstadistica(), BorderLayout.CENTER);
		
		
		
		//Afegim al JTabbedPane i finalment al JPanel
		jtpMusica.add("Llistat", jpLlistat);
		jtpMusica.add("Addicio", jpAddicio);
		jtpMusica.add("Estadistiques", jpEstadistiques);
		
		
		jpMusica.add(jtpMusica, BorderLayout.CENTER);
		
		
			
		return jpMusica;
	}
	
	/**
	 * Genera una taula de tota la musica al sistema i la seva informació
	 * @return JPanel 
	 */
	
	public JPanel LlistarMusica(){
		JPanel jpLlistat = new JPanel(new BorderLayout());
		
		/*
		 * temporal, vinda de la bbdd
		 */
		Object info[][] = { { "Cargol", "Infantil", "Party", "Nuse", "Jorge el puto", "a ti que te importa"},
				{ "Sol solet", "Infantil", "JorgePrivateParty", "Ves tu a saber", "Jorge", "es mia"}
		};
		
		
		/*
		 * fiTemporal  FALTA POSAR UN BOTO D'ESBORRAR A LA DRETA DE CADA FILA
		 */
		
		Object titol[] = { "Nom canço", "Genere", "Àlbum", "Artistes", "Ubicació"};
		
	    JTable taulaMusica = new JTable(info, titol);

		JScrollPane jspLlistat = new JScrollPane(taulaMusica);
		
		jpLlistat.add(jspLlistat, BorderLayout.CENTER);
		return jpLlistat;
	}
	
	/**
	 * Permet l'addició de música en la base de dades
	 * @return JPanel 
	 */
	
	public JPanel AddicioMusica(){
		
		/*
		 * 
		 * FALTA MIGLAYOUT
		 */
		JPanel jpAddicio = new JPanel();
	
		JTextField jtfcanco = new JTextField("Nom canço", 15);
		
		JTextField jtfGenere = new JTextField("Genere", 15);
		
		JTextField jtfAlbum = new JTextField("Nom album", 15);
		
		JTextField jtfArtista = new JTextField("Nom artista", 15);
		
		JTextField jtfUbicacio = new JTextField("Ubicació ó path", 15);
		
		JButton jbAddicio = new JButton();
		jbAddicio.setText("Afegir canço");
		
		jpAddicio.add(jtfcanco);
		jpAddicio.add(jtfGenere);
		jpAddicio.add(jtfAlbum);
		jpAddicio.add(jtfArtista);
		jpAddicio.add(jtfUbicacio);
		jpAddicio.add(jbAddicio);
	
		
		return jpAddicio;
	}
	
	
	/**
	 * Genera les estadistiques de les 10 cançons mes escoltades del sistema
	 * @return JPanel 
	 */
	
	public JPanel generaEstadistica() {

		JPanel jpGrafica = new JPanel(new BorderLayout() );
		JPanel jpNoms = new JPanel (new GridLayout(10, 1));
		JPanel jpBarres = new JPanel (new GridLayout(10,1));
		
		//!!!!!Hardcodeo nombres y valores para hacer pruebas
		ArrayList<String> nomsCancons = new ArrayList<String>();
		nomsCancons.add("pepe");nomsCancons.add("jeje");
		nomsCancons.add("fulanito");nomsCancons.add("menganito");
		nomsCancons.add("juan");nomsCancons.add("prova");
		nomsCancons.add("salle");nomsCancons.add("silla");
		nomsCancons.add("adeu");nomsCancons.add("sdfdsf");
		
		//!!!!!Los introduzco ordenados, faltara un SORT
		ArrayList <String>valors = new ArrayList<String>();
		valors.add("133");valors.add("96");
		valors.add("78");valors.add("75");
		valors.add("66");valors.add("60");
		valors.add("45");valors.add("34");
		valors.add("23");valors.add("8");
		
		
		/*for (int i = 0; i < 10; i++) {
			jpNoms.add(new JLabel(nomsCancons.get(i)));
		}
		
		//Procedim a dibuixar les barres
		for (int i = 0; i < 10; i++) {
			
		}*/
		estadistica = new Estadistica(valors, nomsCancons);
		
		//Agafem el valor mes gran (CUIDADO HARA FALTA ORDENAR CUANDO RECIBAMOS DE LA BDD)
		//mesGran = Integer.parseInt(valors.get(0)); //i = sort de la array (nombre + reproducciones) ordenada de mayor a menos
		
		//jpGrafica.add(jpNoms, BorderLayout.LINE_START);
		//jpGrafica.add(jpBarres, BorderLayout.CENTER);
		jpGrafica.add(estadistica, BorderLayout.CENTER);
		
		return jpGrafica;
	}
	
	

}

