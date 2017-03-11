package frontEnd;

import java.text.ParseException;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class NumberSpinner extends JSpinner
{
	public NumberSpinner()
	{
		super(new SpinnerNumberModel(0, 0, 100, 1));
	}
	
	public int getInt()
	{
		try {
			this.commitEdit();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.getValue() == null)
			return 0;
		else
			return (Integer) this.getValue();
	}
}
