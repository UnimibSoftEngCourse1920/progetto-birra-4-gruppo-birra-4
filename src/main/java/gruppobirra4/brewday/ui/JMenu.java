package gruppobirra4.brewday.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JMenuBar;

public class JMenu {
	
	private JMenuBar menuBar;
	
	public JMenu() {
		this.menuBar = new JMenuBar();
	}

	public void inserisciMenu() {
		menuBar.setBounds(0, 0, 595, 25);
		
		JButton btnHome = new JButton("Home");
		btnHome.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnHome);
		
		JButton btnCatalogo = new JButton("Catalogo ingredienti");
		btnCatalogo.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnCatalogo);
		
		JButton btnSpesa = new JButton("Lista della spesa");
		btnSpesa.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnSpesa);
		
		JButton btnRicettario = new JButton("Ricettario");
		btnRicettario.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnRicettario);
		
		JButton btnLotti = new JButton("Produzioni precedenti");
		btnLotti.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnLotti);
		
		/*JMenuItem mntmHome = new JMenuItem("Home");
		mntmHome.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mntmHome);
		
		JMenuItem mntmCatalogo = new JMenuItem("Catalogo");
		mntmCatalogo.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mntmCatalogo);
		
		JMenuItem mntmSpesa = new JMenuItem("Lista della spesa");
		mntmSpesa.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mntmSpesa);
		
		JMenuItem mntmRicettario = new JMenuItem("Ricettario");
		mntmRicettario.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mntmRicettario);
		
		JMenuItem mntmProduzioniPrecedenti = new JMenuItem("Produzioni precedenti");
		mntmProduzioniPrecedenti.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mntmProduzioniPrecedenti);*/
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
}
