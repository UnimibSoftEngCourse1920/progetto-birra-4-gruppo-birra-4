package gruppobirra4.brewday.ui; //NOSONAR

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

public class PannelloIngredienti {
	
	private JTable table;
	private DefaultTableModel dtm;
	
	private JComboBox comboBoxCategoriaIngr;
	private JTextField textFieldNomeIngr;
	private JTextField textFieldQuantitaIngr;

	public PannelloIngredienti() {
		super();
	}
	
	public JScrollPane inserisciTabellaIngredienti(String quantita) {
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable();
		String[] header = new String[] {"id", "Categoria", "Nome", quantita};
		dtm = new MyTableModel(new Object[][] {}, header)  {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
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
		scrollPane.setViewportView(table);
		return scrollPane;
	}
	
	public void inserisciCategoriaIngr(JPanel panelGestioneIngr) {
		JLabel lblCategoriaIngr = new JLabel("Categoria:");
		panelGestioneIngr.add(lblCategoriaIngr);
		
		comboBoxCategoriaIngr = new JComboBox();
		comboBoxCategoriaIngr.setModel(new DefaultComboBoxModel(new String[] {
					"Malto", "Luppolo", "Lievito", "Zucchero", "Additivo"
					}));
		panelGestioneIngr.add(comboBoxCategoriaIngr);
	}
	
	public void inserisciNomeIngr(JPanel panelGestioneIngr) {
		JLabel lblNomeIngr = new JLabel("Nome:");
		panelGestioneIngr.add(lblNomeIngr);
		
		textFieldNomeIngr = new JTextField();
		panelGestioneIngr.add(textFieldNomeIngr);
		textFieldNomeIngr.setDocument(new JTextFieldLimit(30));
		textFieldNomeIngr.setColumns(10);
	}
	
	public void inserisciQuantitaIngr(JPanel panelGestioneIngr, String quantita) {
		JLabel lblQuantitaDisponibileIngr = new JLabel(quantita);
		panelGestioneIngr.add(lblQuantitaDisponibileIngr);
		
		textFieldQuantitaIngr = new JTextField();
		panelGestioneIngr.add(textFieldQuantitaIngr);
		textFieldQuantitaIngr.setColumns(10);
	}
	
	public void aggiungiRigaIngr(Ingrediente ingr) {
		dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
				Integer.toString((int) Math.round(ingr.getQuantita()))
		});	
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}
	
	public void modificaRigaIngr(Ingrediente ingr, int riga) {
		table.setValueAt(ingr.getCategoria(), riga, 1);
		table.setValueAt(ingr.getNome(), riga, 2);
		table.setValueAt(Integer.toString((int) Math.round(ingr.getQuantita())), riga, 3);
	}
	
	public void rimuoviRigaIngr(int riga) {
		((DefaultTableModel) table.getModel()).removeRow(riga);
		if (table.getRowCount() != 0) {
			table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
		}
	}

	public JTable getTable() {
		return table;
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public JComboBox getComboBoxCategoriaIngr() {
		return comboBoxCategoriaIngr;
	}

	public JTextField getTextFieldNomeIngr() {
		return textFieldNomeIngr;
	}

	public JTextField getTextFieldQuantitaIngr() {
		return textFieldQuantitaIngr;
	}

}
