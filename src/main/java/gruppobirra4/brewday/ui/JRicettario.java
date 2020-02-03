package gruppobirra4.brewday.ui;

import java.awt.EventQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreRicette;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JRicettario extends FrameVisibile {

	private JFrame frmRicettario;

	/**
	 * Create the application.
	 */
	public JRicettario() {
		frmRicettario = new JFrame();
		menu = JMenu.getIstanza();
		initialize();
		menu.setFrameVisible(frmRicettario);
	}
	
	public static void esegui() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JRicettario window = new JRicettario();
					window.frmRicettario.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frmRicettario.setTitle("Ricettario - Brew Day!");
		frmRicettario.setBounds(100, 100, 968, 611);
		frmRicettario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRicettario.getContentPane().setLayout(null);
		
	//MENU
		menu.inserisciMenu();
		frmRicettario.getContentPane().add(menu.getMenuBar());

	//TABELLA RICETTE
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 610, 488);

		JTable table = new JTable();
		String[] header = new String[] {"id", "Ricetta"};
		DefaultTableModel dtm = new MyTableModel(new Object[][] {}, header)  {
			boolean[] columnEditables = new boolean[] {
					false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		dtm.setColumnIdentifiers(header);
		table.setModel(dtm);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		
		frmRicettario.getContentPane().add(scrollPane);
		
		/*PROVA*/ dtm.addRow(new Object[] {"un numero", "Ricetta A"});
		
		//Visualizza ricettario
			/*SortedMap<String, String> ricettario = GestoreRicette.getIstanza().visualizzaRicettario();
			if (ricettario != null) { //Se il ricettario non è vuoto
				Iterator <Entry<String, String>> it = ricettario.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = it.next();
					dtm.addRow(new Object[] {entry.getKey().toString(), entry.getValue().toString()});
					//Aggiungere listener a ogni riga
				}

				ricettario = null;
			}*/
		
	//AGGIUNGI E RIMUOVI
		JPanel panel = new JPanel();
		panel.setBounds(660, 98, 191, 224);
		frmRicettario.getContentPane().add(panel);
		panel.setLayout(new GridLayout(3, 0, 15, 40));
		
		//Apri ricetta
		JButton btnApri = new JButton("Apri ricetta");
		btnApri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int riga = table.getSelectedRow();
				String id = null;
				String nomeRicetta = null;
				if (riga != -1) {
	            	id = (String) table.getValueAt(riga, 0);
	            	nomeRicetta = (String) table.getValueAt(riga, 1);
				}
				frmRicettario.dispose();
				JRicetta.esegui(id, nomeRicetta);
			}
		});
		panel.add(btnApri);
		
		//Aggiungi
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRicettario.dispose();
				JRicetta.esegui(null, null);
			}
		});
		panel.add(btnAggiungi);
		
		//Rimuovi
		JButton btnRimuovi = new JButton("Rimuovi");
		btnRimuovi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int riga = table.getSelectedRow();
				String id = null;
				if (riga != -1) {
	            	id = (String) table.getValueAt(riga, 0);
				}
				if (id != null && riga != -1 && GestoreRicette.getIstanza().rimuoviRicetta(id)) {
					((DefaultTableModel) table.getModel()).removeRow(riga);
				}
			}
		});
		panel.add(btnRimuovi);
		
		
	}
}
