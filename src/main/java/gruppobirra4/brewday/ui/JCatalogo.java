package gruppobirra4.brewday.ui; //NOSONAR

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.util.Collection;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class JCatalogo {

	private JFrame frmCatalogoIngredienti;
	private JTable table;
	private JPanel panel;
	private JButton btnModifica;
	private JTextField textFieldNome;
	private JTextField textFieldQuantita;
	private String id = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCatalogo window = new JCatalogo();
					window.frmCatalogoIngredienti.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JCatalogo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCatalogoIngredienti = new JFrame();
		frmCatalogoIngredienti.setTitle("Catalogo ingredienti - Brew Day");
		frmCatalogoIngredienti.setBounds(100, 100, 968, 611);
		frmCatalogoIngredienti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//TABELLA INGREDIENTI
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 5, 948, 274);
		
		table = new JTable();
		String[] header = new String[] {"id", "Categoria", "Nome", "Quantita disponibile"};
		DefaultTableModel dtm = new MyTableModel(new Object[][] {}, header)  {
				boolean[] columnEditables = new boolean[] {
					true, false, false, false
				};
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
		scrollPane.setViewportView(table);
		frmCatalogoIngredienti.getContentPane().setLayout(null);
		frmCatalogoIngredienti.getContentPane().add(scrollPane);
		
		
		/*PROVA VISUALIZZA CATALOGO
		GestoreIngredienti.getIstanza().creaIngrediente("Tipo1", "Malto", "500");
		GestoreIngredienti.getIstanza().creaIngrediente("Tipo2", "Malto", "900");
		GestoreIngredienti.getIstanza().creaIngrediente("Tipo3", "Malto", "800");
		*/
		
		//Visualizza catalogo
		Collection<Ingrediente> catalogo = GestoreIngredienti.getIstanza().visualizzaCatalogo();
		if (catalogo != null) { //Se il catalogo non è vuoto
			for (Ingrediente ingr: catalogo) {
				dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
							Double.toString(ingr.getQuantita())
							});
			}
			catalogo = null;
		}

		
	//AGGIUNGI, MODIFICA, ELIMINA
		panel = new JPanel();
		panel.setBounds(10, 292, 858, 222);
		frmCatalogoIngredienti.getContentPane().add(panel);
		panel.setLayout(null);
		
	//Campi per inserire/modificare/rimuovere ingredienti
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 390, 222);
		panel.add(panel1);
		panel1.setLayout(new GridLayout(3, 2, 10, 20));
		
		
		JLabel lblCategoria = new JLabel("Categoria:");
		panel1.add(lblCategoria);
		
		JComboBox comboBoxCategoria = new JComboBox();
		comboBoxCategoria.setModel(new DefaultComboBoxModel(new String[] {
					"Malto", "Luppolo", "Lievito", "Zucchero", "Additivo"
					}));
		panel1.add(comboBoxCategoria);
		
		
		JLabel lblNome = new JLabel("Nome:");
		panel1.add(lblNome);
		
		textFieldNome = new JTextField();
		panel1.add(textFieldNome);
		textFieldNome.setDocument(new JTextFieldLimit(30));
		textFieldNome.setColumns(10);
		
		JLabel lblQuantitaDisponibile = new JLabel("Quantita disponibile:");
		panel1.add(lblQuantitaDisponibile);
		
		textFieldQuantita = new JTextField();
		panel1.add(textFieldQuantita);
		textFieldQuantita.setColumns(10);
		
		//Evento per mettere nei jtextfield i valori della riga selezionata
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            int riga = table.getSelectedRow();
	            id = (String) table.getValueAt(riga, 0);
	            comboBoxCategoria.setSelectedItem((String) table.getValueAt(riga, 1));
	            textFieldNome.setText((String) table.getValueAt(riga, 2));
	            textFieldQuantita.setText((String) table.getValueAt(riga, 3));
	        }
	    });
		
		
	//Bottoni	
		JPanel panel2 = new JPanel();
		panel2.setBounds(543, 13, 171, 166);
		panel.add(panel2);
		panel2.setLayout(new GridLayout(3, 0, 5, 15));
		
		//Aggiungi ingrediente
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = textFieldNome.getText();
				String categoria = (String) comboBoxCategoria.getSelectedItem();
				String quantita = textFieldQuantita.getText();
				/*PROVA*/ //dtm.addRow(new Object[] {nomeLW, categoria, quantita});
				
				Ingrediente ingr = GestoreIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
				if (ingr != null) { //Se non ci sono stati errori
					dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
												Double.toString(ingr.getQuantita())
											});	
					ingr = null;
				}
			}
		});
		panel2.add(btnAggiungi);
			
		//Modifica ingrediente
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = textFieldNome.getText();
				String categoria = (String) comboBoxCategoria.getSelectedItem();
				String quantita = textFieldQuantita.getText();
				int riga = table.getSelectedRow();
				String tempId = id;
				if (tempId != null && riga != -1) {
					Ingrediente ingr = GestoreIngredienti.getIstanza().modificaIngrediente(tempId, nome, categoria, quantita);
					if (ingr != null) { //Se non ci sono stati errori
						table.setValueAt(ingr.getCategoria(), riga, 1);
						table.setValueAt(ingr.getNome(), riga, 2);
						table.setValueAt(Double.toString(ingr.getQuantita()), riga, 3);
					}
				}
			}
		});
		panel2.add(btnModifica);
		
		//Rimuovi ingrediente
		JButton btnRimuovi = new JButton("Rimuovi");
		btnRimuovi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int riga = table.getSelectedRow();
				String tempId = id;
				if (tempId != null && riga != -1) {
					GestoreIngredienti.getIstanza().rimuoviIngrediente(tempId);
					((DefaultTableModel) table.getModel()).removeRow(riga);
					id = null;
				}
			}
		});
		panel2.add(btnRimuovi);
		
	}
}
