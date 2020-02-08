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
import javax.swing.JComboBox;

public class JCatalogo extends FrameVisibile{

	private JFrame frmCatalogoIngredienti;
	private PannelloIngredienti pannelloIngr;
	private String id = null;
	private JTable table;
	private DefaultTableModel dtm;
	private JComboBox comboBoxCategoriaIngr;
	private JTextField textFieldNomeIngr;
	private JTextField textFieldQuantitaIngr;
	
	
	public static void main(String[] args) {
		esegui();
	}

	public JCatalogo() {
		frmCatalogoIngredienti = new JFrame();
		menu = JMenu.getIstanza();
		pannelloIngr = new PannelloIngredienti();
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
	
	@Override
	protected void initialize() {
		frmCatalogoIngredienti.setTitle("Catalogo ingredienti - Brew Day!");
		frmCatalogoIngredienti.setBounds(100, 100, 968, 611);
		frmCatalogoIngredienti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCatalogoIngredienti.getContentPane().setLayout(null);
		
		inserisciMenu();
		
		inserisciTabellaIngredienti();
		
		visualizzaCatalogo();
		
		inserisciPannelloIngredienti();
		
	}


	private void inserisciMenu() {
		frmCatalogoIngredienti.getContentPane().add(menu.getMenuBar());	
	}
	
	private void inserisciTabellaIngredienti() {
		/*JScrollPane scrollPane = new JScrollPane();
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
		frmCatalogoIngredienti.getContentPane().add(scrollPane);*/
		JScrollPane scrollPane = pannelloIngr.inserisciTabellaIngredienti("Quantità disponibile");
		table = pannelloIngr.getTable();
		dtm = pannelloIngr.getDtm();		
		scrollPane.setBounds(10, 36, 932, 274);
		frmCatalogoIngredienti.getContentPane().add(scrollPane);		
	}	
	
	private void visualizzaCatalogo() {
		Collection<Ingrediente> catalogo = GestoreIngredienti.getIstanza().visualizzaCatalogo();
		if (!catalogo.isEmpty()) { //Se il catalogo non è vuoto
			for (Ingrediente ingr: catalogo) {
				dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
							Integer.toString((int) Math.round(ingr.getQuantita()))
							});
			}
		}
	}
	
	private void inserisciPannelloIngredienti() {
		JPanel panelIngr = new JPanel();
		panelIngr.setBounds(10, 326, 858, 222);
		frmCatalogoIngredienti.getContentPane().add(panelIngr);
		panelIngr.setLayout(null);
		
		inserisciGestioneIngrediente(panelIngr);
		addListenerSelezioneRiga();
		inserisciBottoni(panelIngr);
	}
	
	private void inserisciGestioneIngrediente(JPanel panelIngr) {
		JPanel panelGestioneIngr = new JPanel();
		panelGestioneIngr.setBounds(0, 0, 390, 222);
		panelIngr.add(panelGestioneIngr);
		panelGestioneIngr.setLayout(new GridLayout(3, 2, 10, 20));
		
		inserisciCategoriaIngr(panelGestioneIngr);
		inserisciNomeIngr(panelGestioneIngr);
		inserisciQuantitaIngr(panelGestioneIngr);
	}
	

	private void inserisciCategoriaIngr(JPanel panelGestioneIngr) {
		/*JLabel lblCategoriaIngr = new JLabel("Categoria:");
		panelGestioneIngr.add(lblCategoriaIngr);
		
		comboBoxCategoriaIngr = new JComboBox();
		comboBoxCategoriaIngr.setModel(new DefaultComboBoxModel(new String[] {
					"Malto", "Luppolo", "Lievito", "Zucchero", "Additivo"
					}));
		panelGestioneIngr.add(comboBoxCategoriaIngr);*/
		pannelloIngr.inserisciCategoriaIngr(panelGestioneIngr);
		comboBoxCategoriaIngr = pannelloIngr.getComboBoxCategoriaIngr();
	}
	
	private void inserisciNomeIngr(JPanel panelGestioneIngr) {
		/*JLabel lblNomeIngr = new JLabel("Nome:");
		panelGestioneIngr.add(lblNomeIngr);
		
		textFieldNomeIngr = new JTextField();
		panelGestioneIngr.add(textFieldNomeIngr);
		textFieldNomeIngr.setDocument(new JTextFieldLimit(30));
		textFieldNomeIngr.setColumns(10);*/
		pannelloIngr.inserisciNomeIngr(panelGestioneIngr);
		textFieldNomeIngr = pannelloIngr.getTextFieldNomeIngr();
	}
	
	private void inserisciQuantitaIngr(JPanel panelGestioneIngr) {
		/*JLabel lblQuantitaDisponibileIngr = new JLabel("Quantità disponibile:");
		panelGestioneIngr.add(lblQuantitaDisponibileIngr);
		
		textFieldQuantitaIngr = new JTextField();
		panelGestioneIngr.add(textFieldQuantitaIngr);
		textFieldQuantitaIngr.setColumns(10);*/
		pannelloIngr.inserisciQuantitaIngr(panelGestioneIngr, "Quantità disponibile");
		textFieldQuantitaIngr = pannelloIngr.getTextFieldQuantitaIngr();
	}
	
	private void addListenerSelezioneRiga() {
		table.getSelectionModel().addListSelectionListener(event -> {
			int riga = table.getSelectedRow();
            if (riga != -1) {
            	id = (String) table.getValueAt(riga, 0);
            	comboBoxCategoriaIngr.setSelectedItem((String) table.getValueAt(riga, 1));
            	textFieldNomeIngr.setText((String) table.getValueAt(riga, 2));
            	textFieldQuantitaIngr.setText((String) table.getValueAt(riga, 3));
            }
		});
	}
	
	private void inserisciBottoni(JPanel panelIngr) {
		JPanel panelBottoni = new JPanel();
		panelBottoni.setBounds(543, 13, 171, 166);
		panelIngr.add(panelBottoni);
		panelBottoni.setLayout(new GridLayout(3, 0, 5, 15));
		
		inserisciBottoneAggiungi(panelBottoni);
		inserisciBottoneModifica(panelBottoni);
		inserisciBottoneRimuovi(panelBottoni);
	}
	
	private void inserisciBottoneAggiungi(JPanel panelBottoni) {
		JButton btnAggiungiIngr = new JButton("Aggiungi");
		btnAggiungiIngr.addActionListener(event -> {
			String nome = textFieldNomeIngr.getText();
			String categoria = (String) comboBoxCategoriaIngr.getSelectedItem();
			String quantita = textFieldQuantitaIngr.getText();
			
			Ingrediente ingr = GestoreIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
			if (ingr != null) { //Se non ci sono stati errori
				pannelloIngr.aggiungiRigaIngr(ingr);
			}
		});
		panelBottoni.add(btnAggiungiIngr);		
	}

	private void inserisciBottoneModifica(JPanel panelBottoni) {
		JButton btnModificaIngr = new JButton("Modifica");
		btnModificaIngr.addActionListener(event -> {
			String nome = textFieldNomeIngr.getText();
			String categoria = (String) comboBoxCategoriaIngr.getSelectedItem();
			String quantita = textFieldQuantitaIngr.getText();
			int riga = table.getSelectedRow();
			String tempId = id;
			if (tempId != null && riga != -1) {
				Ingrediente ingr = GestoreIngredienti.getIstanza().modificaIngrediente(tempId, nome, categoria, quantita);
				if (ingr != null) { //Se non ci sono stati errori
					pannelloIngr.modificaRigaIngr(ingr, riga);
				}
			}
		});
		panelBottoni.add(btnModificaIngr);
		
	}

	private void inserisciBottoneRimuovi(JPanel panelBottoni) {
		JButton btnRimuoviIngr = new JButton("Rimuovi");
		btnRimuoviIngr.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String tempId = id;
			if (tempId != null && riga != -1 && GestoreIngredienti.getIstanza().rimuoviIngrediente(tempId)) {
				pannelloIngr.rimuoviRigaIngr(riga);
				id = null;
			}
			
		});
		panelBottoni.add(btnRimuoviIngr);
	}
	
}
