package gruppobirra4.brewday.gui; //NOSONAR

import java.awt.EventQueue;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.GestoreLotti;
import gruppobirra4.brewday.application.GestoreRicette;
import gruppobirra4.brewday.domain.ricette.Lotto;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.errori.Notifica;

import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class JListaLotti extends FrameVisibile {
	
	private JFrame frmListaLotti;
	private PannelloIngredienti pannelloIngr;
	private JTable table; 
	private DefaultTableModel dtm;
	private JComboBox comboBoxRicette;
	private JTextField textFieldQuantitaBirra;

	public JListaLotti() {
		frmListaLotti = new JFrame();
		menu = JMenu.getIstanza();
		pannelloIngr = new PannelloIngredienti();
		initialize();
		menu.setFrameVisible(frmListaLotti);
	}
	
	public static void esegui() {
		/*EventQueue.invokeLater(() -> {
			try {
				JRicettario window = new JRicettario();
				window.frmRicettario.setVisible(true);
			} catch (Exception e) {
				Notifica.getIstanza().svuotaNotificheErrori();
				Notifica.getIstanza().notificaEccezione(e);
			}
		});*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JListaLotti window = new JListaLotti();
					window.frmListaLotti.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}
	
	@Override
	protected void initialize() {
		frmListaLotti.setTitle("Lotti precedenti - Brew Day!");
		frmListaLotti.setBounds(100, 100, 968, 611);
		frmListaLotti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListaLotti.getContentPane().setLayout(null);
		
		inserisciMenu();
		
		inserisciTabellaLotti();
		
		visualizzaListaLotti();
		
		inserisciCreaLotto();
		
		inserisciBottoni();
		
	}

	private void inserisciMenu() {
		frmListaLotti.getContentPane().add(menu.getMenuBar());
	}
	
	private void inserisciTabellaLotti() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 632, 311);

		table = new JTable();
		String[] header = new String[] {"id", "Data", "Ricetta"};
		dtm = new MyTableModel(new Object[][] {}, header)  {
			boolean[] columnEditables = new boolean[] {
					false, false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		pannelloIngr.setTabella(table, dtm, header, scrollPane);			
		table.setRowHeight(30);
		frmListaLotti.getContentPane().add(scrollPane);
	}
	
	private void visualizzaListaLotti() {
		Collection<Lotto> lotti = GestoreLotti.getIstanza().visualizzaListaLotti();
		if (!lotti.isEmpty()) { //Se la lista dei lotti non e' vuota
			for (Lotto l: lotti) {
				dtm.addRow(new Object[] {l.getId(), l.getData(), l.getNomeRicetta()});
			}
		}		
	}
	
	private void inserisciCreaLotto() {
		JPanel panelCreazioneLotto = new JPanel();
		panelCreazioneLotto.setBounds(10, 388, 517, 95);
		frmListaLotti.getContentPane().add(panelCreazioneLotto);
		panelCreazioneLotto.setLayout(null);
		
		JPanel panelSelezioneRicetta = new JPanel();
		panelSelezioneRicetta.setBounds(10, 11, 329, 84);
		panelCreazioneLotto.add(panelSelezioneRicetta);
		panelSelezioneRicetta.setLayout(new GridLayout(2, 2, 30, 15));
		
		inserisciPannelloSelezioneLotto(panelSelezioneRicetta);
		inserisciBottoneCreaLotto(panelCreazioneLotto);
	}

	private void inserisciPannelloSelezioneLotto(JPanel panelSelezioneRicetta) {
		JLabel lblRicette = new JLabel("Ricette");
		lblRicette.setHorizontalAlignment(SwingConstants.RIGHT);
		panelSelezioneRicetta.add(lblRicette);
		
		comboBoxRicette = new JComboBox();
		Collection<Ricetta> ricettario = GestoreRicette.getIstanza().visualizzaRicettario();
		if (!ricettario.isEmpty()) { //Se ricettario non è vuoto
			for (Ricetta r: ricettario) {
				comboBoxRicette.addItem(r.getNome());
			}
		}
		panelSelezioneRicetta.add(comboBoxRicette);
		
		JLabel lblQuantitaBirraDa = new JLabel("Quantita birra da produrre");
		panelSelezioneRicetta.add(lblQuantitaBirraDa);
		
		textFieldQuantitaBirra = new JTextField();
		panelSelezioneRicetta.add(textFieldQuantitaBirra);
		textFieldQuantitaBirra.setColumns(10);
	}
	
	private void inserisciBottoneCreaLotto(JPanel panelCreazioneLotto) {
		JButton btnCreaLotto = new JButton("Crea lotto");
		btnCreaLotto.setBounds(370, 11, 137, 54);
		panelCreazioneLotto.add(btnCreaLotto);
		btnCreaLotto.addActionListener(event -> {
			if (comboBoxRicette.getItemCount() != 0) {
				String nomeRicetta = (String) comboBoxRicette.getSelectedItem();
				String quantitaBirra = textFieldQuantitaBirra.getText();
				Lotto lotto = GestoreLotti.getIstanza().creaLotto(nomeRicetta, quantitaBirra);
				if (lotto != null ) {
					frmListaLotti.dispose();
					JLotto.esegui(lotto);
				}
			}
		});		
	}

	private void inserisciBottoni() {
		JPanel panelBottoni = new JPanel();
		panelBottoni.setBounds(689, 81, 153, 124);
		frmListaLotti.getContentPane().add(panelBottoni);
		panelBottoni.setLayout(new GridLayout(0, 1, 0, 15));
		
		inserisciApriLotto(panelBottoni);
		inserisciRimuoviLotto(panelBottoni);
		
	}

	private void inserisciApriLotto(JPanel panelBottoni) {
		JButton btnApri = new JButton("Apri");
		panelBottoni.add(btnApri);
		
		btnApri.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String idLotto = null;
			if (riga != -1) {
				idLotto = (String) table.getValueAt(riga, 0);
				Lotto lotto = GestoreLotti.getIstanza().visualizzaLotto(idLotto);
				frmListaLotti.dispose();
				JLotto.esegui(lotto);
			}
		});
	}

	private void inserisciRimuoviLotto(JPanel panelBottoni) {
		JButton btnRimuovi = new JButton("Rimuovi");
		panelBottoni.add(btnRimuovi);
		
		btnRimuovi.addActionListener(event -> {
			int riga = table.getSelectedRow();
			String idLotto = null;
			if (riga != -1) {
				idLotto = (String) table.getValueAt(riga, 0);				
			}
			if (idLotto != null && riga != -1 && GestoreLotti.getIstanza().rimuoviLotto(idLotto)) {
				((DefaultTableModel) table.getModel()).removeRow(riga);
			}
		});	
	}

}