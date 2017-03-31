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

import frontEnd.StoryElementList;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

public abstract class FieldPanel<T extends StoryElement> extends JPanel
{
	private int yPos = 0;
	protected String heading;
	
	public String getHeading() {
		return heading;
	}

	public FieldPanel()
	{
		super();
		this.setLayout(new GridBagLayout());
	}
	
	protected void addJLabel(String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1, 0));
		this.yPos++;
	}
	
	protected void addTextField(JTextField jTextField, String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1, 0));
		this.add(jTextField, this.setupGridBagConstraints(1, this.yPos, 1, 1, 1));
		this.yPos++;
	}
	
	protected void addSpinner(JSpinner jSpinner, String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1, 0));
		this.add(jSpinner, this.setupGridBagConstraints(1, this.yPos, 1, 1, 1));
		this.yPos++;
	}
	
	protected void addPanel(JPanel panel)
	{		
		this.add(panel, this.setupGridBagConstraints(0, this.yPos, 2, 1, 1));
		this.yPos++;
	}
	
	protected void addStoryElementList(StoryElementList storyElementList)
	{		
		this.add(storyElementList, this.setupGridBagConstraints(0, this.yPos, 2, 1, 1));
		this.yPos++;
	}
	
	protected GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight, int weighty)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = weighty;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		return gridBagConstraints;
	}
	
	public abstract T getResult();
}
