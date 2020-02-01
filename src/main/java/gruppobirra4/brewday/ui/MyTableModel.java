package gruppobirra4.brewday.ui; //NOSONAR

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{

	public MyTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	@Override
	public boolean isCellEditable(int row, int col){ //Impedisce di modificare una cella dalla tabella
		return false;
	}

}
