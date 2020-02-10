package gruppobirra4.brewday.ui; //NOSONAR

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreRicette;
import gruppobirra4.brewday.domain.DecimalUtils;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.errori.Notifica;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class JRicetta extends FrameVisibile{

	private JFrame frmRicetta;
	private PannelloIngredienti pannelloIngr;
	private String idIngrediente = null;
	private String idRicetta;
	private String nomeRicetta;
	private JTable table;
	private DefaultTableModel dtm;
	
	private JComboBox comboBoxCategoriaIngr;
	private JTextField textFieldNomeIngr;
	private JTextField textFieldQuantitaIngr;
	
	private JTextField textFieldNomeRicetta;
	private JTextField textQuantitaAcqua;
	private JTextField textQuantitaBirra;
	private JTextArea textAreaDescrizione;
	

	public JRicetta(String idRicetta, String nomeRicetta) {
		frmRicetta = new JFrame();
		menu = JMenu.getIstanza();
		this.idRicetta = idRicetta;
		this.nomeRicetta = nomeRicetta;
		pannelloIngr = new PannelloIngredienti();
		initialize();
		menu.setFrameVisible(frmRicetta);
	}
	
	public static void esegui(String idRicetta, String nomeRicetta) {
		/*EventQueue.invokeLater(() -> {
			try {
				JRicetta window = new JRicetta(idRicetta, nomeRicetta);
				window.frmRicetta.setVisible(true);
			} catch (Exception e) {
				Notifica.getIstanza().svuotaNotificheErrori();
				Notifica.getIstanza().notificaEccezione(e);
			}
		});*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JRicetta window = new JRicetta(idRicetta, nomeRicetta);
					window.frmRicetta.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}
	
	@Override
	protected void initialize() {
		if (idRicetta == null) {
			frmRicetta.setTitle("Creazione nuova ricetta - Brew Day!");
		} else {
			frmRicetta.setTitle("Ricetta: " + nomeRicetta + " - Brew Day!");
		}
		frmRicetta.setBounds(100, 100, 968, 656);
		frmRicetta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRicetta.getContentPane().setLayout(null);
	
		inserisciMenu();
		
		inserisciNomeRicetta();
		
		inserisciPannelloIngredienti();
		
		inserisciQuantita();
	
		inserisciDescrizione();
		
		inserisciBottoneRicetta();
		
		visualizzaRicetta();
	}
	
	private void inserisciMenu() {
		frmRicetta.getContentPane().add(menu.getMenuBar());
	}
	
	private void inserisciNomeRicetta() {
		JPanel paneNomeRicetta = new JPanel();
		paneNomeRicetta.setBounds(10, 34, 311, 38);
		frmRicetta.getContentPane().add(paneNomeRicetta);
		paneNomeRicetta.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNomeRicetta = new JLabel("Nome ricetta");
		paneNomeRicetta.add(lblNomeRicetta);
		
		textFieldNomeRicetta = new JTextField();
		paneNomeRicetta.add(textFieldNomeRicetta);
		textFieldNomeRicetta.setDocument(new JTextFieldLimit(30));
		textFieldNomeRicetta.setColumns(10);
	}
	
	private void inserisciPannelloIngredienti() {
		JLabel lblInserimentoIngredienti = new JLabel("Inserimento ingredienti");
		lblInserimentoIngredienti.setBounds(10, 77, 144, 14);
		frmRicetta.getContentPane().add(lblInserimentoIngredienti);
		
		JPanel panelInserimentoIngr = new JPanel();
		panelInserimentoIngr.setBorder(new LineBorder(new Color(192, 192, 192)));
		panelInserimentoIngr.setBounds(10, 96, 932, 281);
		frmRicetta.getContentPane().add(panelInserimentoIngr);
		panelInserimentoIngr.setLayout(null);
		
		inserisciTabellaIngredienti(panelInserimentoIngr);
		
		JPanel panelIngr = new JPanel();
		panelIngr.setBounds(579, 11, 343, 265);
		panelInserimentoIngr.add(panelIngr);
		panelIngr.setLayout(null);
		
		inserisciGestioneIngrediente(panelIngr);
		addListenerSelezioneRiga();
		inserisciBottoniIngr(panelIngr);
		
	}
	
	private void inserisciTabellaIngredienti(JPanel panelInserimentoIngr) {
		/*JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 561, 281);
		table = new JTable();
		String[] header = new String[] {"id", "Categoria", "Nome", "Quantità"};
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
		panelInserimentoIngr.add(scrollPane);*/
		
		JScrollPane scrollPane = pannelloIngr.inserisciTabellaIngredienti("Quantità");
		table = pannelloIngr.getTable();
		dtm = pannelloIngr.getDtm();		
		scrollPane.setBounds(0, 0, 561, 281);
		panelInserimentoIngr.add(scrollPane);		
	}
	
	private void inserisciGestioneIngrediente(JPanel panelIngr) {
		JPanel panelGestioneIngr = new JPanel();
		panelGestioneIngr.setBounds(0, 0, 223, 265);
		panelIngr.add(panelGestioneIngr);
		panelGestioneIngr.setLayout(new GridLayout(6, 1, 10, 0));

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
		pannelloIngr.inserisciQuantitaIngr(panelGestioneIngr, "Quantità");
		textFieldQuantitaIngr = pannelloIngr.getTextFieldQuantitaIngr();
	}
	
	private void addListenerSelezioneRiga() {
		table.getSelectionModel().addListSelectionListener(event -> {
			int riga = table.getSelectedRow();
            if (riga != -1) {
            	idIngrediente = (String) table.getValueAt(riga, 0);
            	comboBoxCategoriaIngr.setSelectedItem((String) table.getValueAt(riga, 1));
            	textFieldNomeIngr.setText((String) table.getValueAt(riga, 2));
            	textFieldQuantitaIngr.setText((String) table.getValueAt(riga, 3));
            }
		});
	}
	
	private void inserisciBottoniIngr(JPanel panelIngr) {
		JPanel panelBottoniIngr = new JPanel();
		panelBottoniIngr.setBounds(244, 26, 99, 166);
		panelIngr.add(panelBottoniIngr);
		panelBottoniIngr.setLayout(new GridLayout(3, 0, 5, 15));
		
		inserisciBottoneAggiungi(panelBottoniIngr);
		inserisciBottoneModifica(panelBottoniIngr);
		inserisciBottoneRimuovi(panelBottoniIngr);
	}
	
	private void inserisciBottoneAggiungi(JPanel panelBottoniIngr) {
		JButton btnInserisciIngr = new JButton("Inserisci");
		btnInserisciIngr.addActionListener(event -> {
			String nome = textFieldNomeIngr.getText();
			String categoria = (String) comboBoxCategoriaIngr.getSelectedItem();
			String quantita = textFieldQuantitaIngr.getText();

			Ingrediente ingr = GestoreRicette.getIstanza().inserisciIngrediente("", nome, categoria, quantita);
			if (ingr != null) { //Se non ci sono stati errori
				pannelloIngr.aggiungiRigaIngr(ingr);
			}
		});
		panelBottoniIngr.add(btnInserisciIngr);
	}

	private void inserisciBottoneModifica(JPanel panelBottoniIngr) {
		JButton btnModificaIngr = new JButton("Modifica");
		btnModificaIngr.addActionListener(event -> {
			String nome = textFieldNomeIngr.getText();
			String categoria = (String) comboBoxCategoriaIngr.getSelectedItem();
			String quantita = textFieldQuantitaIngr.getText();
			int riga = table.getSelectedRow();
			String tempId = idIngrediente;

			if (tempId != null && riga != -1) {
				Ingrediente ingr = GestoreRicette.getIstanza().inserisciIngrediente(tempId, nome, categoria, quantita);
				if (ingr != null) { //Se non ci sono stati errori
					pannelloIngr.modificaRigaIngr(ingr, riga);
				}
			}	
		});
		panelBottoniIngr.add(btnModificaIngr);
	}

	private void inserisciBottoneRimuovi(JPanel panelBottoniIngr) {
		JButton btnRimuoviIngr = new JButton("Rimuovi");
		btnRimuoviIngr.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String tempId = idIngrediente;
			if (tempId != null && riga != -1) {
				pannelloIngr.rimuoviRigaIngr(riga);
				idIngrediente = null;
			}
		});
		panelBottoniIngr.add(btnRimuoviIngr);
	}
	
	private void inserisciQuantita() {
		JPanel panelQuantita = new JPanel();
		panelQuantita.setBounds(10, 413, 275, 148);
		frmRicetta.getContentPane().add(panelQuantita);
		panelQuantita.setLayout(new GridLayout(2, 2, 10, 30));
		
		inserisciQuantitaAcqua(panelQuantita);
		inserisciQuantitaBirra(panelQuantita);
	}

	private void inserisciQuantitaAcqua(JPanel panelQuantita) {
		JLabel lblQuantitaDiAcqua = new JLabel("Quantità di acqua (litri):");
		panelQuantita.add(lblQuantitaDiAcqua);
		
		textQuantitaAcqua = new JTextField();
		panelQuantita.add(textQuantitaAcqua);
		textQuantitaAcqua.setColumns(10);
	}
	
	private void inserisciQuantitaBirra(JPanel panelQuantita) {
		JLabel lblQuantitaDiBirra = new JLabel("Quantità di birra (litri):");
		panelQuantita.add(lblQuantitaDiBirra);
		
		textQuantitaBirra = new JTextField();
		panelQuantita.add(textQuantitaBirra);
		textQuantitaBirra.setColumns(10);
	}

	private void inserisciDescrizione() {
		JPanel panelDescrizione = new JPanel();
		panelDescrizione.setBounds(325, 399, 382, 207);
		frmRicetta.getContentPane().add(panelDescrizione);
		panelDescrizione.setLayout(null);
		
		JLabel lblDescrizione = new JLabel("Descrizione");
		lblDescrizione.setBounds(0, 0, 88, 14);
		panelDescrizione.add(lblDescrizione);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(0, 21, 382, 186);
		panelDescrizione.add(scrollPane1);
		
		textAreaDescrizione = new JTextArea();
		scrollPane1.setViewportView(textAreaDescrizione);
	}
	
	private void inserisciBottoneRicetta() {
		JPanel panelBottoneRicetta = new JPanel();
		panelBottoneRicetta.setBounds(758, 510, 184, 51);
		frmRicetta.getContentPane().add(panelBottoneRicetta);
		panelBottoneRicetta.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btn = new JButton();
		if (idRicetta == null) {
			btn.setText("Crea");
		} else {
			btn.setText("Salva modifiche");
		}
		
		btn.addActionListener(event -> {
			String nome = textFieldNomeRicetta.getText();
			String quantitaAcqua = textQuantitaAcqua.getText();
			String quantitaBirra = textQuantitaBirra.getText();
			String descrizione = textAreaDescrizione.getText();

			Set<Ingrediente> ingredienti = new HashSet<>();
			for(int i=0; i < table.getRowCount(); i++) {
				ingredienti.add(new Ingrediente(
						(String) table.getValueAt(i, 2),
						(String) table.getValueAt(i, 1),
						(String) table.getValueAt(i, 3)));
			}				
			Ricetta r = null;
			if (idRicetta == null) {
				r = GestoreRicette.getIstanza().creaRicetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
			} else {
				r = GestoreRicette.getIstanza().modificaRicetta(idRicetta, nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
			}
			if (r != null) {
				frmRicetta.dispose();
				JRicettario.esegui();
			}
		});
		panelBottoneRicetta.add(btn);
	}
	
	private void visualizzaRicetta() {
		if (idRicetta != null) {
			Ricetta ricetta= GestoreRicette.getIstanza().visualizzaRicetta(idRicetta);
			if (ricetta != null) { //Se il ricettario non è vuoto
				textFieldNomeRicetta.setText(ricetta.getNome());
				textQuantitaAcqua.setText(Double.toString(DecimalUtils.round(ricetta.getQuantitaAcqua(), 1)));
				textQuantitaBirra.setText(Double.toString(DecimalUtils.round(ricetta.getQuantitaBirra(), 1)));
				textAreaDescrizione.setText(ricetta.getDescrizione());
				
				Set<Ingrediente> ingredienti = ricetta.getIngredienti();
				for(Ingrediente ingr: ingredienti) {
					dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
											Integer.toString((int) Math.round(ingr.getQuantita()))
											});
				}
			}
		}			
	}
	
	
}
