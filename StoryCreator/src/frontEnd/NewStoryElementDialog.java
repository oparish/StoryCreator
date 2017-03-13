package frontEnd;

import java.awt.Frame;

import main.Main;

public class NewStoryElementDialog extends FieldDialog
{
	public NewStoryElementDialog(Frame owner, boolean modal)
	{
		super(owner, modal);
		this.addJLabel(Main.getMainSpice().getSuggestion());
	}
}
