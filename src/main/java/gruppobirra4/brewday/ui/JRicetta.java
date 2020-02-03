package gruppobirra4.brewday.ui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreRicette;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class JRicetta extends FrameVisibile{

	private JFrame frmRicetta;
	private String idIngrediente = null;
	private String idRicetta;
	private String nomeRicetta;
	private JTextField textField;
	private JTextField textQuantitaAcqua;
	private JTextField textQuantitaBirra;
	

	/**
	 * Create the application.
	 */
	public JRicetta(String idRicetta, String nomeRicetta) {
		frmRicetta = new JFrame();
		menu = JMenu.getIstanza();
		this.idRicetta = idRicetta;
		this.nomeRicetta = nomeRicetta;
		initialize();
		menu.setFrameVisible(frmRicetta);
	}
	
	public static void esegui(String idRicetta, String nomeRicetta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JRicetta window = new JRicetta(idRicetta, nomeRicetta);
					window.frmRicetta.setVisible(true);
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
		if (idRicetta == null) {
			frmRicetta.setTitle("Creazione nuova ricetta - Brew Day!");
		} else {
			frmRicetta.setTitle("Ricetta: " + nomeRicetta + " - Brew Day!");
		}
		frmRicetta.setBounds(100, 100, 968, 656);
		frmRicetta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRicetta.getContentPane().setLayout(null);
		
	//MENU
		menu.inserisciMenu();
		frmRicetta.getContentPane().add(menu.getMenuBar());
		
	//NOME RICETTA
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 34, 186, 38);
		frmRicetta.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNomeRicetta = new JLabel("Nome ricetta");
		panel_1.add(lblNomeRicetta);
		
		
	//INGREDIENTI
		JLabel lblInserimentoIngredienti = new JLabel("Inserimento ingredienti");
		lblInserimentoIngredienti.setBounds(10, 83, 144, 14);
		frmRicetta.getContentPane().add(lblInserimentoIngredienti);
		
		JPanel panelIngr = new JPanel();
		panelIngr.setBorder(new LineBorder(new Color(192, 192, 192)));
		panelIngr.setBounds(10, 96, 932, 292);
		frmRicetta.getContentPane().add(panelIngr);
		panelIngr.setLayout(null);
		
	//Tabella ingredienti
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 11, 561, 281);
		
		JTable table = new JTable();
		String[] header = new String[] {"id", "Categoria", "Nome", "Quantita disponibile"};
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
		
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		scrollPane.setViewportView(table);
		panelIngr.add(scrollPane);
		
	//Inserimento Ingredienti
		JPanel panel = new JPanel();
		panel.setBounds(579, 11, 343, 265);
		panelIngr.add(panel);
		panel.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 223, 265);
		panel.add(panel1);
		panel1.setLayout(new GridLayout(6, 1, 10, 0));
		
		
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
		
		JLabel lblQuantita = new JLabel("Quantita:");
		panel1.add(lblQuantita);
		
		JTextField textFieldQuantita = new JTextField();
		panel1.add(textFieldQuantita);
		textFieldQuantita.setColumns(10);
		
		
		//Evento per mettere nei jtextfield i valori della riga selezionata
		ListSelectionListener l = new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            int riga = table.getSelectedRow();
	            if (riga != -1) {
	            	idIngrediente = (String) table.getValueAt(riga, 0);
	            	comboBoxCategoria.setSelectedItem((String) table.getValueAt(riga, 1));
	            	textFieldNome.setText((String) table.getValueAt(riga, 2));
	            	textFieldQuantita.setText((String) table.getValueAt(riga, 3));
	            }
	        }
		};
		table.getSelectionModel().addListSelectionListener(l);
	
		
		//Bottoni ingredienti
		JPanel panel2 = new JPanel();
		panel2.setBounds(244, 26, 99, 166);
		panel.add(panel2);
		panel2.setLayout(new GridLayout(3, 0, 5, 15));
		
		//Aggiungi ingrediente
		JButton btnInserisciIngr = new JButton("Inserisci");
		btnInserisciIngr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = textFieldNome.getText();
				String categoria = (String) comboBoxCategoria.getSelectedItem();
				String quantita = textFieldQuantita.getText();
				
				/*Ingrediente ingr = GestoreRicette.getIstanza().inserisciIngrediente(nome, categoria, quantita);
				if (ingr != null) { //Se non ci sono stati errori
					dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
												Double.toString(ingr.getQuantita())
											});	
					ingr = null;
					table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				}*/
			}
		});
		panel2.add(btnInserisciIngr);
			
		//Modifica ingrediente
		JButton btnModificaIngr = new JButton("Modifica");
		btnModificaIngr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = textFieldNome.getText();
				String categoria = (String) comboBoxCategoria.getSelectedItem();
				String quantita = textFieldQuantita.getText();
				int riga = table.getSelectedRow();
				String tempId = idIngrediente;
				
				if (tempId != null && riga != -1) {
					/*Ingrediente ingr = GestoreRicette.getIstanza().inserisciIngrediente(tempId, nome, categoria, quantita);
					if (ingr != null) { //Se non ci sono stati errori
						table.setValueAt(ingr.getCategoria(), riga, 1);
						table.setValueAt(ingr.getNome(), riga, 2);
						table.setValueAt(Double.toString(ingr.getQuantita()), riga, 3);
					}*/
				}
			}
		});
		panel2.add(btnModificaIngr);
		
		//Rimuovi ingrediente
		JButton btnRimuoviIngr = new JButton("Rimuovi");
		btnRimuoviIngr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int riga = table.getSelectedRow();
				String tempId = idIngrediente;
				if (tempId != null && riga != -1) {
					((DefaultTableModel) table.getModel()).removeRow(riga);
					idIngrediente = null;
					if (table.getRowCount() != 0)
						table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				}
			}
		});
		panel2.add(btnRimuoviIngr);
		
	//QUANTITA 
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(23, 413, 241, 148);
		frmRicetta.getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(2, 2, 10, 20));
		
		JLabel lblQuantitaDiAcqua = new JLabel("Quantita di acqua");
		panel_2.add(lblQuantitaDiAcqua);
		
		textQuantitaAcqua = new JTextField();
		panel_2.add(textQuantitaAcqua);
		textQuantitaAcqua.setColumns(10);
		
		JLabel lblQuantotaDiBirra = new JLabel("Quantota di birra");
		panel_2.add(lblQuantotaDiBirra);
		
		textQuantitaBirra = new JTextField();
		panel_2.add(textQuantitaBirra);
		textQuantitaBirra.setColumns(10);
		
	//DESCRIZIONE
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(325, 399, 382, 207);
		frmRicetta.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblDescrizione = new JLabel("Descrizione");
		lblDescrizione.setBounds(0, 0, 88, 14);
		panel_3.add(lblDescrizione);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 21, 382, 186);
		panel_3.add(scrollPane_1);
		
		JTextArea textAreaDescrizione = new JTextArea();
		scrollPane_1.setViewportView(textAreaDescrizione);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(758, 510, 184, 51);
		frmRicetta.getContentPane().add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
	//MODIFICA E CREA
		JButton btnModifica = new JButton("Modifica");
		panel_4.add(btnModifica);
		
		JButton btnCrea = new JButton("Crea");
		panel_4.add(btnCrea);
		
	
	//Visualizza ricetta
		if (idRicetta != null) {
			/*Ricetta ricetta = GestoreRicette.getIstanza().visualizzaRicetta(idRicetta);
			if (ricetta != null) { //Se il catalogo non è vuoto
				for (Ingrediente ingr: catalogo) {
					dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
								Double.toString(ingr.getQuantita())
								});
				}
				catalogo = null;
			}*/
		}
	}
}
