package frontEnd.fieldPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import frontEnd.MyPanel;
import frontEnd.StoryElementList;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

public abstract class FieldPanel<T extends StoryElement> extends MyPanel<T>
{
	protected int branchLevel;

	public FieldPanel(int branchLevel)
	{
		super();
		this.branchLevel = branchLevel;
	}
	
	public abstract T getResult();
	
	public abstract OptionContentType getOptionContentType();
}
