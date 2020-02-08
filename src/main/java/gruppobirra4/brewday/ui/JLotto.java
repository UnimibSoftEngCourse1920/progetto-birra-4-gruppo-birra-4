package gruppobirra4.brewday.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.domain.DecimalUtils;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Lotto;
import gruppobirra4.brewday.errori.Notifica;

public class JLotto extends FrameVisibile{
	
	private JFrame frmLotto;
	private PannelloIngredienti pannelloIngr;
	private Lotto lotto;
	private JTable table;
	private DefaultTableModel dtm;
	
	public JLotto(Lotto lotto) {
		frmLotto = new JFrame();
		menu = JMenu.getIstanza();
		this.lotto = lotto;
		pannelloIngr = new PannelloIngredienti();
		initialize();
		menu.setFrameVisible(frmLotto);
	}
	
	public static void esegui(Lotto lotto) {
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
					JLotto window = new JLotto(lotto);
					window.frmLotto.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}

	@Override
	protected void initialize() {
		frmLotto.setBounds(100, 100, 968, 656);
		frmLotto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLotto.getContentPane().setLayout(null);
	
		inserisciMenu();
		
		inserisciPannelloIngredienti();
		
	}
	
	private void inserisciMenu() {
		frmLotto.getContentPane().add(menu.getMenuBar());
	}
	
	private void inserisciPannelloIngredienti() {
		JLabel lblInserimentoIngredienti = new JLabel("Ingredienti:");
		lblInserimentoIngredienti.setBounds(20, 37, 144, 14);
		frmLotto.getContentPane().add(lblInserimentoIngredienti);
		
		inserisciTabellaIngredienti();
		
		inserisciQuantita();
		
	}

	private void inserisciTabellaIngredienti() {
		/*JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 56, 667, 282);
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
		scrollPane.setViewportView(table); */
		
		JScrollPane scrollPane = pannelloIngr.inserisciTabellaIngredienti("Quantità");
		table = pannelloIngr.getTable();
		dtm = pannelloIngr.getDtm();		
		scrollPane.setBounds(20, 56, 667, 282);
		
		frmLotto.getContentPane().add(scrollPane);	
		
		visualizzaIngredienti();
	}
	
	private void visualizzaIngredienti() {
		Set<Ingrediente> ingredienti = lotto.getRicetta().getIngredienti();
		for (Ingrediente ingr: ingredienti) {
			dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
					Integer.toString((int) Math.round(ingr.getQuantita()))
					});
		}
	}

	private void inserisciQuantita() {
		JPanel panelQuantita = new JPanel();
		panelQuantita.setBounds(20, 375, 241, 92);
		frmLotto.getContentPane().add(panelQuantita);
		panelQuantita.setLayout(new GridLayout(2, 2, 10, 0));
		
		inserisciQuantitaAcqua(panelQuantita);
		inserisciQuantitaBirra(panelQuantita);
	}
	
	private void inserisciQuantitaAcqua(JPanel panelQuantita) {
		JLabel lblQuantitaDiAcqua = new JLabel("Quantità di acqua");
		panelQuantita.add(lblQuantitaDiAcqua);
		
		JLabel lblQuantitaAcqua = new JLabel(Double.toString(DecimalUtils.round(lotto.getRicetta().getQuantitaAcqua(), 1)));
		panelQuantita.add(lblQuantitaAcqua);
	}
	
	private void inserisciQuantitaBirra(JPanel panelQuantita) {
		JLabel lblQuantitaDiBirra = new JLabel("Quantità di birra");
		panelQuantita.add(lblQuantitaDiBirra);
		
		JLabel lblQuantitaBirra = new JLabel(Double.toString(DecimalUtils.round(lotto.getRicetta().getQuantitaBirra(), 1)));
		panelQuantita.add(lblQuantitaBirra);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
