package view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.xswingx.PromptSupport;

import controller.ControladorFinestres;
import net.miginfocom.swing.MigLayout;
/**
 * Gestio de la finestra encarregada de la introduccio de dades referents al registre. 
 * @author carlaurrea
 *
 */

public class Finestra_Registre {
	
	private JFrame jfRegistre;
	private JButton jbRegistre;
	private JPasswordField jtfPassword;
	private JTextField jtfUsuari;
	
	private ControladorFinestres controladorf;
	
	public Finestra_Registre() {
		
		jfRegistre = new JFrame("eSpotyfai - Registre");
		jfRegistre.setVisible(true);
		jfRegistre.setSize(350, 350);
		jfRegistre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfRegistre.setLocationRelativeTo(null);
		
		
		JPanel jpRegistre = new JPanel();
		//centrar a la pantalla
		jpRegistre.setLayout(new MigLayout("al center center, wrap, gapy 10"));
		//color negre del fons
		jpRegistre.setBackground(new Color(29,29,23));
		
				
		jtfUsuari = new JTextField(15);
		jtfUsuari.setForeground(Color.WHITE);
		jtfUsuari.setBackground(new Color(51,51,51));
		PromptSupport.setPrompt("Nom d'Usuari", jtfUsuari);
		

		
		
		jtfPassword = new JPasswordField(15);
		jtfPassword.setBackground(new Color(51,51,51));
		jtfPassword.setForeground(Color.WHITE);
		PromptSupport.setPrompt("Contrasenya", jtfPassword);
		
		//Logo
		ImageIcon logo = new ImageIcon("espotify_Client/Images/logoSpotyfai.png");
		JLabel jlLogo = new JLabel();
		ImageIcon icono = new ImageIcon(logo.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT));
		jlLogo.setIcon(icono);
		
		//afegim els jtarea al jpanel log
		jpRegistre.add(jlLogo, "span 2, grow, wrap ");
		jpRegistre.add(jtfUsuari, "span 2, grow, wrap");
		jpRegistre.add(jtfPassword,  "span 2, grow, wrap");
		
		//boto
		jbRegistre = new JButton ();
		
		jpRegistre.add(jbRegistre, "span 2, grow, wrap");
		jbRegistre.setText("Completar registre");

		jfRegistre.add(jpRegistre);
		
		
		
		jbRegistre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Finestra_Registre fReg = new FinestraRegistre();
				controladorf.Reproduccio();
				
			}
		});
		
	}
	
	public void tancaFinestraRegistre() {
		jfRegistre.dispose();
	}
	
	public JButton getjbRegistre() { 
		return jbRegistre;
	}
	
	public JPasswordField getjtfPassword() {
		return jtfPassword;
	}
	
	public JTextField getjtfUsuari() {
		return jtfUsuari;
	}

}
