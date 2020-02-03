package gruppobirra4.brewday.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class JMenu {
	
	private static JMenu istanza;
	private JMenuBar menuBar;
	private JFrame frameVisibile; 
	
	private JMenu() {
		this.menuBar = new JMenuBar();
		this.frameVisibile = null;
	}
	
	public static synchronized JMenu getIstanza() {
		if (istanza == null){
			istanza = new JMenu();	
		}
		return istanza;
	}
	
	protected void setFrameVisible(JFrame frm) {
		frameVisibile = frm;
	}
	
	public void inserisciMenu() {
		menuBar.setBounds(0, 0, 595, 25);
		
		JButton btnHome = new JButton("Home");
		btnHome.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnHome);
		
		JButton btnCatalogo = new JButton("Catalogo ingredienti");
		btnCatalogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameVisibile.dispose();
				JCatalogo.esegui();
			}
		});
		btnCatalogo.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnCatalogo);
		
		JButton btnSpesa = new JButton("Lista della spesa");
		btnSpesa.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnSpesa);
		
		JButton btnRicettario = new JButton("Ricettario");
		btnRicettario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameVisibile.dispose();
				JRicettario.esegui();
			}
		});
		btnRicettario.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnRicettario);
		
		JButton btnLotti = new JButton("Produzioni precedenti");
		btnLotti.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnLotti);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
}
