package gruppoBirra4.brewDay.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppoBirra4.brewDay.application.gestori.GestoreIngredienti;
import gruppoBirra4.brewDay.application.gestori.GestoreRicette;

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

public class Home {

	private JFrame frmCatalogoIngredienti;
	private JTable table;
	private JPanel panel;
	private JButton btnModifica;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
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
	public Home() {
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 5, 948, 274);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Categoria", "Quantita disponibile"
			}
		));
		scrollPane.setViewportView(table);
		frmCatalogoIngredienti.getContentPane().setLayout(null);
		frmCatalogoIngredienti.getContentPane().add(scrollPane);
		
		panel = new JPanel();
		panel.setBounds(10, 292, 858, 222);
		frmCatalogoIngredienti.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(543, 13, 171, 166);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 0, 5, 15));
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//GestoreIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
			}
		});
		btnAggiungi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_2.add(btnAggiungi);
		
		btnModifica = new JButton("Modifica");
		panel_2.add(btnModifica);
		
		JButton btnCancella = new JButton("Cancella");
		panel_2.add(btnCancella);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 390, 222);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 2, 10, 20));
		
		JLabel lblNome = new JLabel("Nome:");
		panel_1.add(lblNome);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		panel_1.add(lblCategoria);
		
		textField_2 = new JTextField();
		panel_1.add(textField_2);
		
		JLabel lblQuantitaDisponibile = new JLabel("Quantita disponibile:");
		panel_1.add(lblQuantitaDisponibile);
		
		textField_1 = new JTextField();
		panel_1.add(textField_1);
		textField_1.setColumns(10);
	}
}
