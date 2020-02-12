package gruppobirra4.brewday.ui; //NOSONAR

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class JTextFieldLimit extends PlainDocument {
	
	private int limit;
	
	public JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}

}