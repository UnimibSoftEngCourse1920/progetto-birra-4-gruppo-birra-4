package gruppobirra4.brewday.ui; //NOSONAR

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreListaSpesa;
import gruppobirra4.brewday.domain.ingredienti.QuantitaListaSpesa;
import gruppobirra4.brewday.errori.Notifica;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.Collection;
import javax.swing.JComboBox;

public class JListaSpesa extends FrameVisibile{

	private JFrame frmListaSpesa;
	private PannelloIngredienti pannelloIngr;
	private String id = null;
	private JTable table;
	private DefaultTableModel dtm;
	private JComboBox comboBoxCategoriaIngr;
	private JTextField textFieldNomeIngr;
	private JTextField textFieldQuantitaIngr;
	

	public JListaSpesa() {
		frmListaSpesa = new JFrame();
		menu = JMenu.getIstanza();
		pannelloIngr = new PannelloIngredienti();
		initialize();
		menu.setFrameVisible(frmListaSpesa);
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
					JListaSpesa window = new JListaSpesa();
					window.frmListaSpesa.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}
	
	@Override
	protected void initialize() {
		frmListaSpesa.setTitle("Lista della spesa - Brew Day!");
		frmListaSpesa.setBounds(100, 100, 968, 611);
		frmListaSpesa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListaSpesa.getContentPane().setLayout(null);
		
		inserisciMenu();
		
		inserisciTabellaIngredienti();
		
		visualizzaListaSpesa();
		
		inserisciPannelloIngredienti();
	}

	
	private void inserisciMenu() {
		frmListaSpesa.getContentPane().add(menu.getMenuBar());	
	}
	
	private void inserisciTabellaIngredienti() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 932, 274);
		table = new JTable();
		String[] header = new String[] {"id", "Categoria", "Nome", "Quantità disponibile (g)", "Quantità da acquistare (g)"};
		dtm = new MyTableModel(new Object[][] {}, header)  {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
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
		frmListaSpesa.getContentPane().add(scrollPane);	
	}
	
	private void visualizzaListaSpesa() {
		Collection<QuantitaListaSpesa> listaSpesa = GestoreListaSpesa.getIstanza().visualizzaListaSpesa();
		if (!listaSpesa.isEmpty()) { //Se il catalogo non è vuoto
			for (QuantitaListaSpesa ingr: listaSpesa) {
				dtm.addRow(new Object[] {ingr.getIngrediente().getId(), ingr.getIngrediente().getCategoria(), ingr.getIngrediente().getNome(), 
							Integer.toString((int) Math.round(ingr.getIngrediente().getQuantita())), Integer.toString((int) Math.round(ingr.getQuantitaDaAcquistare()))});
			}
		}
	}
	
	private void inserisciPannelloIngredienti() {
		JPanel panelIngr = new JPanel();
		panelIngr.setBounds(10, 326, 858, 222);
		frmListaSpesa.getContentPane().add(panelIngr);
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
		pannelloIngr.inserisciCategoriaIngr(panelGestioneIngr);
		comboBoxCategoriaIngr = pannelloIngr.getComboBoxCategoriaIngr();
	}
	
	private void inserisciNomeIngr(JPanel panelGestioneIngr) {
		pannelloIngr.inserisciNomeIngr(panelGestioneIngr);
		textFieldNomeIngr = pannelloIngr.getTextFieldNomeIngr();
	}
	
	private void inserisciQuantitaIngr(JPanel panelGestioneIngr) {
		pannelloIngr.inserisciQuantitaIngr(panelGestioneIngr, "Quantità da acquistare");
		textFieldQuantitaIngr = pannelloIngr.getTextFieldQuantitaIngr();
	}
	
	private void addListenerSelezioneRiga() {
		table.getSelectionModel().addListSelectionListener(event -> {
			int riga = table.getSelectedRow();
            if (riga != -1) {
            	id = (String) table.getValueAt(riga, 0);
            	comboBoxCategoriaIngr.setSelectedItem((String) table.getValueAt(riga, 1));
            	textFieldNomeIngr.setText((String) table.getValueAt(riga, 2));
            	textFieldQuantitaIngr.setText((String) table.getValueAt(riga, 4));
            }
		});
	}
	
	private void inserisciBottoni(JPanel panelIngr) {
		JPanel panelBottoni = new JPanel();
		panelBottoni.setBounds(423, 16, 390, 166);
		panelIngr.add(panelBottoni);
		panelBottoni.setLayout(new GridLayout(3, 2, 5, 15));
		
		inserisciBottoneAggiungi(panelBottoni);
		inserisciBottoneAcquista(panelBottoni);
		inserisciBottoneModifica(panelBottoni);
		inserisciBottoneAcquistaTutto(panelBottoni);
		inserisciBottoneRimuovi(panelBottoni);
		inserisciBottoneSvuotaLista(panelBottoni);
	}
	
	private void inserisciBottoneAggiungi(JPanel panelBottoni) {
		JButton btnAggiungiIngr = new JButton("Aggiungi");
		btnAggiungiIngr.addActionListener(event -> {
			String nome = textFieldNomeIngr.getText();
			String categoria = (String) comboBoxCategoriaIngr.getSelectedItem();
			String quantita = textFieldQuantitaIngr.getText();
			
			QuantitaListaSpesa qtls = GestoreListaSpesa.getIstanza().aggiungiIngrediente(nome, categoria, quantita);
			if (qtls != null) { //Se non ci sono stati errori
				dtm.addRow(new Object[] {qtls.getIngrediente().getId(), qtls.getIngrediente().getCategoria(), qtls.getIngrediente().getNome(), 
						Integer.toString((int) Math.round(qtls.getIngrediente().getQuantita())),
						Math.round(qtls.getQuantitaDaAcquistare())
				});	
				table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
			}
		});
		panelBottoni.add(btnAggiungiIngr);			
	}
	
	private void inserisciBottoneAcquista(JPanel panelBottoni) {
		JButton btnAcquistaIngr = new JButton("Acquista");
		btnAcquistaIngr.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String tempId = id;
			if (tempId != null && riga != -1 && GestoreListaSpesa.getIstanza().acquistaIngrediente(tempId)) {
				((DefaultTableModel) table.getModel()).removeRow(riga);
				if (table.getRowCount() != 0) {
					table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				}
				id = null;
			}
		});
		panelBottoni.add(btnAcquistaIngr);
	}
	
	private void inserisciBottoneModifica(JPanel panelBottoni) {
		JButton btnModificaIngr = new JButton("Modifica");
		btnModificaIngr.addActionListener(event -> {
			String quantitaDaAcquistare = textFieldQuantitaIngr.getText();
			int riga = table.getSelectedRow();
			String tempId = id;
			if (tempId != null && riga != -1) {
				QuantitaListaSpesa qtls = GestoreListaSpesa.getIstanza().modificaQuantita(tempId, quantitaDaAcquistare);
				if (qtls != null) { //Se non ci sono stati errori
					table.setValueAt(qtls.getIngrediente().getCategoria(), riga, 1);
					table.setValueAt(qtls.getIngrediente().getNome(), riga, 2);
					table.setValueAt(Integer.toString((int) Math.round(qtls.getIngrediente().getQuantita())), riga, 3);
					table.setValueAt(Math.round(qtls.getQuantitaDaAcquistare()), riga, 4);
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
			if (tempId != null && riga != -1 && GestoreListaSpesa.getIstanza().rimuoviIngrediente(tempId)) {
				((DefaultTableModel) table.getModel()).removeRow(riga);
				//if (table.getRowCount() != 0) {
					//table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				//}
				id = null;
			}
		});
		panelBottoni.add(btnRimuoviIngr);
	}
	
	private void inserisciBottoneAcquistaTutto(JPanel panelBottoni) {
		JButton btnAcquistaTutto = new JButton("Acquista tutto");
		btnAcquistaTutto.addActionListener(event -> {
			if (table.getRowCount() != 0) {
				GestoreListaSpesa.getIstanza().acquistaTutto();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
			    int rowCount = model.getRowCount();
			    for (int i = rowCount; i > 0 ; i--){
			        model.removeRow(i-1);
			    } 
			}
		});
		panelBottoni.add(btnAcquistaTutto);
	}
	
	private void inserisciBottoneSvuotaLista(JPanel panelBottoni) {
		JButton btnSvuotaLista = new JButton("Svuota lista");
		btnSvuotaLista.addActionListener(event -> {
			if (table.getRowCount() != 0) {
				GestoreListaSpesa.getIstanza().svuotaLista();
				svuotaTabella();
			}
		});
		panelBottoni.add(btnSvuotaLista);
	}
	
	private void svuotaTabella() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int rowCount = model.getRowCount();
	    for (int i = rowCount; i > 0 ; i--){
	        model.removeRow(i-1);
	    }
	}
	
}

