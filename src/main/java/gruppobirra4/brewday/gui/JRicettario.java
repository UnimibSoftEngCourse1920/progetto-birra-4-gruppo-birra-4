package gruppobirra4.brewday.gui; //NOSONAR

import java.awt.EventQueue;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreRicette;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.errori.Notifica;

import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.JButton;

public class JRicettario extends FrameVisibile {

	private JFrame frmRicettario;
	private PannelloIngredienti pannelloIngr;
	private JTable table; 
	private DefaultTableModel dtm;
	
	public JRicettario() {
		frmRicettario = new JFrame();
		menu = JMenu.getIstanza();
		pannelloIngr = new PannelloIngredienti();
		initialize();
		menu.setFrameVisible(frmRicettario);
	}
	
	public static void esegui() {
		/*EventQueue.invokeLater(() -> {
			try {
				JRicettario window = new JRicettario();
				window.frmRicettario.setVisible(true);
			} catch (Exception e) {
				Notifica.getIstanza().svuotaNotificheErrori();
				Notifica.getIstanza().notificaEccezione(e);
			}
		});*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JRicettario window = new JRicettario();
					window.frmRicettario.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}
	
	@Override
	protected void initialize() {
		frmRicettario.setTitle("Ricettario - Brew Day!");
		frmRicettario.setBounds(100, 100, 968, 611);
		frmRicettario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRicettario.getContentPane().setLayout(null);
		
		inserisciMenu();
		
		inserisciTabellaRicette();
		
		visualizzaRicettario();
		
		inserisciBottoni();
		
	}
	
	private void inserisciMenu() {
		frmRicettario.getContentPane().add(menu.getMenuBar());
	}
	
	private void inserisciTabellaRicette() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 610, 488);

		table = new JTable();
		String[] header = new String[] {"id", "Ricetta"};
		dtm = new MyTableModel(new Object[][] {}, header)  {
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		pannelloIngr.setTabella(table, dtm, header, scrollPane);	
		table.setRowHeight(30);
		frmRicettario.getContentPane().add(scrollPane);
	}
	
	private void visualizzaRicettario() {
		Collection<Ricetta> ricettario = GestoreRicette.getIstanza().visualizzaRicettario();
		if (!ricettario.isEmpty()) { //Se ricettario non è vuoto
			for (Ricetta r: ricettario) {
				dtm.addRow(new Object[] {r.getId(), r.getNome()});
			}
		}
	}

	private void inserisciBottoni() {
		JPanel panelBottoni = new JPanel();
		panelBottoni.setBounds(660, 98, 191, 224);
		frmRicettario.getContentPane().add(panelBottoni);
		panelBottoni.setLayout(new GridLayout(3, 0, 15, 40));

		inserisciApriRicetta(panelBottoni);
		
		inserisciAggiungiRicetta(panelBottoni);

		inserisciRimuoviRicetta(panelBottoni);
	}

	private void inserisciApriRicetta(JPanel panelBottoni) {
		JButton btnApri = new JButton("Apri ricetta");
		btnApri.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String id = null;
			String nomeRicetta = null;
			if (riga != -1) {
				id = (String) table.getValueAt(riga, 0);
				nomeRicetta = (String) table.getValueAt(riga, 1);
				frmRicettario.dispose();
				JRicetta.esegui(id, nomeRicetta);
			}
		});
		panelBottoni.add(btnApri);
	}
	
	private void inserisciAggiungiRicetta(JPanel panelBottoni) {
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(event -> {
			frmRicettario.dispose();
			JRicetta.esegui(null, null);
		});
		panelBottoni.add(btnAggiungi);
	}
	
	private void inserisciRimuoviRicetta(JPanel panelBottoni) {
		JButton btnRimuovi = new JButton("Rimuovi");
		btnRimuovi.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String id = null;
			if (riga != -1) {
				id = (String) table.getValueAt(riga, 0);
			}
			if (id != null && riga != -1 && GestoreRicette.getIstanza().rimuoviRicetta(id)) {
				((DefaultTableModel) table.getModel()).removeRow(riga);
			}
		});
		panelBottoni.add(btnRimuovi);
	}

}