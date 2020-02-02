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

import gruppobirra4.brewday.application.gestori.GestoreIngredienti;
import gruppobirra4.brewday.application.gestori.GestoreRicette;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

public class JRicettario {

	private JFrame frmRicettario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	 * Create the application.
	 */
	public JRicettario() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRicettario = new JFrame();
		frmRicettario.setTitle("Ricettario - Brew Day!");
		frmRicettario.setBounds(100, 100, 968, 611);
		frmRicettario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//MENU
		JMenu menu = new JMenu();
		menu.inserisciMenu();
		frmRicettario.getContentPane().add(menu.getMenuBar());

	//TABELLA INGREDIENTI
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 932, 488);

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
		/*table.setModel(dtm
				table.setModel(new DefaultTableModel(new Object[][] {},	
													new String[] {"id", "Categoria", "Nome", "Quantita disponibile"}) {
					boolean[] columnEditables = new boolean[] {
						true, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});*/
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		frmRicettario.getContentPane().setLayout(null);
		frmRicettario.getContentPane().add(scrollPane);
		
		/*PROVA*/ //dtm.addRow(new Object[] {"un numero", "Ricetta A"});
		
	//Visualizza ricettario
		SortedMap<String, String> ricettario = GestoreRicette.getIstanza().visualizzaRicettario();
		if (ricettario != null) { //Se il ricettario non è vuoto
			Iterator <Entry<String, String>> it = ricettario.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = it.next();
				dtm.addRow(new Object[] {entry.getKey().toString(), entry.getValue().toString()});
				//Aggiungere listener a ogni riga
			}

			ricettario = null;
		}
	}

}
