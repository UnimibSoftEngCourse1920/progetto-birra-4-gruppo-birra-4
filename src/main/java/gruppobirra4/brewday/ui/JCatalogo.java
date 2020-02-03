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

public class JCatalogo extends FrameVisibile{

	private JFrame frmCatalogoIngredienti;
	private String id = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		esegui();
	}

	/**
	 * Create the application.
	 */
	public JCatalogo() {
		frmCatalogoIngredienti = new JFrame();
		menu = JMenu.getIstanza();
		initialize();
		menu.setFrameVisible(frmCatalogoIngredienti);
	}
	
	
	public static void esegui() {
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
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frmCatalogoIngredienti.setTitle("Catalogo ingredienti - Brew Day!");
		frmCatalogoIngredienti.setBounds(100, 100, 968, 611);
		frmCatalogoIngredienti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//MENU
		menu.inserisciMenu();
		frmCatalogoIngredienti.getContentPane().add(menu.getMenuBar());
		
	
	//TABELLA INGREDIENTI
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 932, 274);
		
		JTable table = new JTable();
		String[] header = new String[] {"id", "Categoria", "Nome", "Quantità disponibile"};
		DefaultTableModel dtm = new MyTableModel(new Object[][] {}, header)  {
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
		JPanel panel = new JPanel();
		panel.setBounds(10, 326, 858, 222);
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
		
		JTextField textFieldNome = new JTextField();
		panel1.add(textFieldNome);
		textFieldNome.setDocument(new JTextFieldLimit(30));
		textFieldNome.setColumns(10);
		
		JLabel lblQuantitaDisponibile = new JLabel("Quantita disponibile:");
		panel1.add(lblQuantitaDisponibile);
		
		JTextField textFieldQuantita = new JTextField();
		panel1.add(textFieldQuantita);
		textFieldQuantita.setColumns(10);
		
		//Evento per mettere nei jtextfield i valori della riga selezionata
		ListSelectionListener l = new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            int riga = table.getSelectedRow();
	            if (riga != -1) {
	            	id = (String) table.getValueAt(riga, 0);
	            	comboBoxCategoria.setSelectedItem((String) table.getValueAt(riga, 1));
	            	textFieldNome.setText((String) table.getValueAt(riga, 2));
	            	textFieldQuantita.setText((String) table.getValueAt(riga, 3));
	            }
	        }
		};
		table.getSelectionModel().addListSelectionListener(l);
	
		
		
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
				
				Ingrediente ingr = GestoreIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
				if (ingr != null) { //Se non ci sono stati errori
					dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
												Double.toString(ingr.getQuantita())
											});	
					ingr = null;
					table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				}
			}
		});
		panel2.add(btnAggiungi);
			
		//Modifica ingrediente
		JButton btnModifica = new JButton("Modifica");
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
				if (tempId != null && riga != -1 && GestoreIngredienti.getIstanza().rimuoviIngrediente(tempId)) {
					((DefaultTableModel) table.getModel()).removeRow(riga);
					id = null;
					if (table.getRowCount() != 0)
						table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				}
			}
		});
		panel2.add(btnRimuovi);
		
		
	}
}
