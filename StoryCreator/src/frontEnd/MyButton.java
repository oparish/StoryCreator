package frontEnd;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MyButton extends JButton
{
	private ButtonID id;
	
	public ButtonID getId() {
		return id;
	}

	public MyButton(String text, ButtonID id )
	{
		super(text);
		this.id = id;
	}
}
