package gruppobirra4.brewday.gui; //NOSONAR

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import gruppobirra4.brewday.application.GestoreLotti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Lotto;
import gruppobirra4.brewday.errori.Notifica;
import gruppobirra4.brewday.foundation.utility.DecimalUtils;

import javax.swing.JTextArea;

public class JLotto extends FrameVisibile{
	
	private JFrame frmLotto;
	private PannelloIngredienti pannelloIngr;
	private Lotto lotto;
	private DefaultTableModel dtm;
	private JTextArea textAreaNoteGusto;
	private JTextArea textAreaNoteProblemi;
	
	public JLotto(Lotto lotto) {
		frmLotto = new JFrame();
		menu = JMenu.getIstanza();
		this.lotto = lotto;
		pannelloIngr = new PannelloIngredienti();
		initialize();
		menu.setFrameVisible(frmLotto);
	}
	
	public static void esegui(Lotto lotto) {
		EventQueue.invokeLater(() -> {
			try {
				JLotto window = new JLotto(lotto);
				window.frmLotto.setVisible(true);
			} catch (Exception e) {
				Notifica.getIstanza().svuotaNotificheErrori();
				Notifica.getIstanza().notificaEccezione(e);
			}
		});
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLotto window = new JLotto(lotto);
					window.frmLotto.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});*/
	}

	@Override
	protected void initialize() {
		frmLotto.setTitle("Lotto ricetta: " + lotto.getNomeRicetta() + " - Brew Day!");
		frmLotto.setBounds(100, 100, 968, 682);
		frmLotto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLotto.getContentPane().setLayout(null);
	
		inserisciMenu();
		
		inserisciPannelloIngredienti();
		
		inserisciQuantita();
		
		inserisciNote();
		
		inserisciBottoneSalva();
		
		visualizzaLotto();
	}

	private void inserisciMenu() {
		frmLotto.getContentPane().add(menu.getMenuBar());
	}
	
	private void inserisciPannelloIngredienti() {
		JLabel lblInserimentoIngredienti = new JLabel("Ingredienti:");
		lblInserimentoIngredienti.setBounds(20, 37, 144, 14);
		frmLotto.getContentPane().add(lblInserimentoIngredienti);
		
		inserisciTabellaIngredienti();
	}

	private void inserisciTabellaIngredienti() {
		JScrollPane scrollPane = pannelloIngr.inserisciTabellaIngredienti("Quantità");
		pannelloIngr.getTable();
		dtm = pannelloIngr.getDtm();		
		scrollPane.setBounds(20, 56, 652, 282);
		
		frmLotto.getContentPane().add(scrollPane);	
	}

	private void inserisciQuantita() {
		JPanel panelQuantita = new JPanel();
		panelQuantita.setBounds(678, 89, 274, 92);
		frmLotto.getContentPane().add(panelQuantita);
		panelQuantita.setLayout(new GridLayout(2, 2, 2, 0));
		
		inserisciQuantitaAcqua(panelQuantita);
		inserisciQuantitaBirra(panelQuantita);
		
	}
	
	private void inserisciQuantitaAcqua(JPanel panelQuantita) {
		JLabel lblQuantitaDiAcqua = new JLabel("Quantità di acqua (litri):");
		panelQuantita.add(lblQuantitaDiAcqua);
		
		JLabel lblQuantitaAcqua = new JLabel(Double.toString(DecimalUtils.round(lotto.getRicetta().getQuantitaAcqua(), 1)));
		panelQuantita.add(lblQuantitaAcqua);
	}
	
	private void inserisciQuantitaBirra(JPanel panelQuantita) {
		JLabel lblQuantitaDiBirra = new JLabel("Quantità di birra (litri):");
		panelQuantita.add(lblQuantitaDiBirra);
		
		JLabel lblQuantitaBirra = new JLabel(Double.toString(DecimalUtils.round(lotto.getRicetta().getQuantitaBirra(), 1)));
		panelQuantita.add(lblQuantitaBirra);
	}
	
	private void inserisciNote() {
		JPanel panelNote = new JPanel();
		panelNote.setBounds(20, 375, 711, 257);
		frmLotto.getContentPane().add(panelNote);
		panelNote.setLayout(new GridLayout(0, 2, 15, 0));
		
		JLabel lblNoteGusto = new JLabel("Note gusto");
		lblNoteGusto.setBounds(147, 350, 89, 14);
		frmLotto.getContentPane().add(lblNoteGusto);
		
		textAreaNoteGusto = new JTextArea();
		inserisciTestoNote(panelNote, textAreaNoteGusto);
		
		JLabel lblNoteSuiProblemi = new JLabel("Note problemi riscontrati");
		lblNoteSuiProblemi.setBounds(502, 350, 151, 14);
		frmLotto.getContentPane().add(lblNoteSuiProblemi);
		
		textAreaNoteProblemi = new JTextArea();
		inserisciTestoNote(panelNote, textAreaNoteProblemi);
	}

	private void inserisciTestoNote(JPanel panelNote, JTextArea textAreaNote) {
		JScrollPane scrollPaneNote = new JScrollPane();
		panelNote.add(scrollPaneNote);
		scrollPaneNote.setViewportView(textAreaNote);
	}
	
	private void inserisciBottoneSalva() {
		JPanel panelBottoneSalva = new JPanel();
		panelBottoneSalva.setBounds(798, 581, 144, 51);
		frmLotto.getContentPane().add(panelBottoneSalva);
		panelBottoneSalva.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnSalva = new JButton();
		btnSalva.setText("Salva");
		
		btnSalva.addActionListener(event -> {
			String noteGusto = textAreaNoteGusto.getText();
			String noteProblemi = textAreaNoteProblemi.getText();
			if(GestoreLotti.getIstanza().modificaNote(lotto.getId(), noteGusto, noteProblemi)) {
				frmLotto.dispose();
				JListaLotti.esegui();
			}
		});
		panelBottoneSalva.add(btnSalva);
	}
	
	private void visualizzaLotto() {
		Set<Ingrediente> ingredienti = lotto.getRicetta().getIngredienti();
		for (Ingrediente ingr: ingredienti) {
			dtm.addRow(new Object[] {ingr.getId(), ingr.getCategoria(), ingr.getNome(), 
					Integer.toString((int) Math.round(ingr.getQuantita()))
					});
		}
		
		textAreaNoteGusto.setText(lotto.getNoteGusto());
		textAreaNoteProblemi.setText(lotto.getNoteProblemi());
	}

}