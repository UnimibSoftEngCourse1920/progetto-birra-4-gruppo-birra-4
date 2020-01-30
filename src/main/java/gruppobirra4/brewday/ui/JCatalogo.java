package gruppobirra4.brewday.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.gestori.GestoreIngredienti;
import gruppobirra4.brewday.application.gestori.GestoreRicette;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
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
		String[] header = new String[] {"Categoria", "Nome", "Quantita disponibile"};
		DefaultTableModel dtm = new MyTableModel(new Object[][] {}, header);
		//dtm.setColumnIdentifiers(header);
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		frmCatalogoIngredienti.getContentPane().setLayout(null);
		frmCatalogoIngredienti.getContentPane().add(scrollPane);
		
		//Visualizza catalogo
		/*Set<Ingrediente> catalogo = GestoreIngredienti.getIstanza().visualizzaCatalogo();
		for (Ingrediente ingr: catalogo) {
			dtm.addRow(new Object[] {ingr.getCategoria(), ingr.getNome(), ingr.getQuantita()});
		}*/
		
	//AGGIUNGI, MODIFICA, ELIMINA
		panel = new JPanel();
		panel.setBounds(10, 292, 858, 222);
		frmCatalogoIngredienti.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 390, 222);
		panel.add(panel1);
		panel1.setLayout(new GridLayout(3, 2, 10, 20));
		
		JLabel lblNome = new JLabel("Nome:");
		panel1.add(lblNome);
		
		textFieldNome = new JTextField();
		panel1.add(textFieldNome);
		textFieldNome.setDocument(new JTextFieldLimit(30));
		textFieldNome.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		panel1.add(lblCategoria);
		
		JComboBox comboBoxCategoria = new JComboBox();
		comboBoxCategoria.setModel(new DefaultComboBoxModel(new String[] {"Malto", "Luppolo", "Lievito", "Zucchero", "Additivo"}));
		panel1.add(comboBoxCategoria);
		
		JLabel lblQuantitaDisponibile = new JLabel("Quantita disponibile:");
		panel1.add(lblQuantitaDisponibile);
		
		textFieldQuantita = new JTextField();
		panel1.add(textFieldQuantita);
		textFieldQuantita.setColumns(10);
		
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(543, 13, 171, 166);
		panel.add(panel2);
		panel2.setLayout(new GridLayout(3, 0, 5, 15));
		
		//Aggiungi ingrediente
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = textFieldNome.getText();
				String nomeLW = nome.replaceAll("\\s+", " ").trim().toLowerCase();
				String categoria = (String) comboBoxCategoria.getSelectedItem();
				String quantita = textFieldQuantita.getText();
				/*PROVA*/ //dtm.addRow(new Object[] {nomeLW, categoria, quantita});
				
				Ingrediente ingr = GestoreIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
				if (ingr != null) { //Se non ci sono stati errori
					dtm.addRow(new Object[] {ingr.getCategoria(), ingr.getNome(), ingr.getQuantita()});
				}
			}
		});
		
		panel2.add(btnAggiungi);
		
		btnModifica = new JButton("Modifica");
		panel2.add(btnModifica);
		
		JButton btnCancella = new JButton("Cancella");
		panel2.add(btnCancella);
		
	}
}
