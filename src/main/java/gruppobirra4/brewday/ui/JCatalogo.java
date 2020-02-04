package gruppobirra4.brewday.ui; //NOSONAR

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class JCatalogo extends FrameVisibile{

	private JFrame frmCatalogoIngredienti;
	private String id = null;
	private JTable table;
	private DefaultTableModel dtm;
	private JPanel panelIngr;
	private JPanel panelGestisciIngr;
	private JComboBox comboBoxCategoria;
	private JTextField textFieldNome;
	private JTextField textFieldQuantita;
	private JPanel panelBottoni;
	
	
	public static void main(String[] args) {
		esegui();
	}

	public JCatalogo() {
		frmCatalogoIngredienti = new JFrame();
		menu = JMenu.getIstanza();
		initialize();
		menu.setFrameVisible(frmCatalogoIngredienti);
	}
	
	public static void esegui() {
		/*EventQueue.invokeLater(() -> {
			try {
				JCatalogo window = new JCatalogo();
				window.frmCatalogoIngredienti.setVisible(true);
			} catch (Exception e) {
				Notifica.getIstanza().svuotaNotificheErrori();
				Notifica.getIstanza().notificaEccezione(e);
			}
		});*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCatalogo window = new JCatalogo();
					window.frmCatalogoIngredienti.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}
	
	protected void initialize() {
		frmCatalogoIngredienti.setTitle("Catalogo ingredienti - Brew Day!");
		frmCatalogoIngredienti.setBounds(100, 100, 968, 611);
		frmCatalogoIngredienti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCatalogoIngredienti.getContentPane().setLayout(null);
		
		inserisciMenu();
		
		inserisciTabellaIngredienti();
		
		visualizzaCatalogo();
		
		inserisciGestioneIngrediente();
		
		addListenerSelezioneRiga();
			
		inserisciBottoni();
		
	}

	
	private void inserisciMenu() {
		menu.inserisciMenu();
		frmCatalogoIngredienti.getContentPane().add(menu.getMenuBar());
	}
	
	private void inserisciTabellaIngredienti() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 932, 274);
		table = new JTable();
		String[] header = new String[] {"id", "Categoria", "Nome", "Quantità disponibile"};
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
		frmCatalogoIngredienti.getContentPane().add(scrollPane);
	}
	
	private void visualizzaCatalogo() {
		Collection<Ingrediente> catalogo = GestoreIngredienti.getIstanza().visualizzaCatalogo();
		if (!catalogo.isEmpty()) { //Se il catalogo non è vuoto
			for (Ingrediente ingr: catalogo) {
				dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
							Double.toString(ingr.getQuantita())
							});
			}
		}
	}
	
	private void inserisciGestioneIngrediente() {
		panelIngr = new JPanel();
		panelIngr.setBounds(10, 326, 858, 222);
		frmCatalogoIngredienti.getContentPane().add(panelIngr);
		panelIngr.setLayout(null);
		
		panelGestisciIngr = new JPanel();
		panelGestisciIngr.setBounds(0, 0, 390, 222);
		panelIngr.add(panelGestisciIngr);
		panelGestisciIngr.setLayout(new GridLayout(3, 2, 10, 20));
		
		inserisciCategoriaIngr();
		inserisciNomeIngr();
		inserisciQuantitaIngr();
	}
	

	private void inserisciCategoriaIngr() {
		JLabel lblCategoria = new JLabel("Categoria:");
		panelGestisciIngr.add(lblCategoria);
		
		comboBoxCategoria = new JComboBox();
		comboBoxCategoria.setModel(new DefaultComboBoxModel(new String[] {
					"Malto", "Luppolo", "Lievito", "Zucchero", "Additivo"
					}));
		panelGestisciIngr.add(comboBoxCategoria);
	}
	
	private void inserisciNomeIngr() {
		JLabel lblNome = new JLabel("Nome:");
		panelGestisciIngr.add(lblNome);
		
		textFieldNome = new JTextField();
		panelGestisciIngr.add(textFieldNome);
		textFieldNome.setDocument(new JTextFieldLimit(30));
		textFieldNome.setColumns(10);
	}
	
	private void inserisciQuantitaIngr() {
		JLabel lblQuantitaDisponibile = new JLabel("Quantita disponibile:");
		panelGestisciIngr.add(lblQuantitaDisponibile);
		
		textFieldQuantita = new JTextField();
		panelGestisciIngr.add(textFieldQuantita);
		textFieldQuantita.setColumns(10);
	}
	
	private void addListenerSelezioneRiga() {
		table.getSelectionModel().addListSelectionListener(event -> {
			int riga = table.getSelectedRow();
            if (riga != -1) {
            	id = (String) table.getValueAt(riga, 0);
            	comboBoxCategoria.setSelectedItem((String) table.getValueAt(riga, 1));
            	textFieldNome.setText((String) table.getValueAt(riga, 2));
            	textFieldQuantita.setText((String) table.getValueAt(riga, 3));
            }
		});
	}
	
	private void inserisciBottoni() {
		panelBottoni = new JPanel();
		panelBottoni.setBounds(543, 13, 171, 166);
		panelIngr.add(panelBottoni);
		panelBottoni.setLayout(new GridLayout(3, 0, 5, 15));
		
		inserisciBottoneAggiungi();
		inserisciBottoneModifica();
		inserisciBottoneRimuovi();
	}
	
	private void inserisciBottoneAggiungi() {
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(event -> {
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
		});
		panelBottoni.add(btnAggiungi);		
	}

	private void inserisciBottoneModifica() {
		JButton btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(event -> {
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
		});
		panelBottoni.add(btnModifica);
		
	}

	private void inserisciBottoneRimuovi() {
		JButton btnRimuovi = new JButton("Rimuovi");
		btnRimuovi.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String tempId = id;
			if (tempId != null && riga != -1 && GestoreIngredienti.getIstanza().rimuoviIngrediente(tempId)) {
				((DefaultTableModel) table.getModel()).removeRow(riga);
				id = null;
				if (table.getRowCount() != 0)
					table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
			}
			
		});
		panelBottoni.add(btnRimuovi);
	}

	
}
