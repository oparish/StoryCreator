package frontEnd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

public abstract class FieldPanel<T extends StoryElement> extends JPanel
{
	private int yPos = 0;
	
	public FieldPanel(String heading)
	{
		super();
		this.setLayout(new GridBagLayout());
		this.addJLabel(heading);
	}
	
	protected void addJLabel(String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1));
		this.yPos++;
	}
	
	protected void addTextField(JTextField jTextField, String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1));
		this.add(jTextField, this.setupGridBagConstraints(1, this.yPos, 1, 1));
		this.yPos++;
	}
	
	protected void addSpinner(JSpinner jSpinner, String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1));
		this.add(jSpinner, this.setupGridBagConstraints(1, this.yPos, 1, 1));
		this.yPos++;
	}
	
	protected void addPanel(JPanel panel)
	{		
		this.add(panel, this.setupGridBagConstraints(0, this.yPos, 1, 1));
		this.yPos++;
	}
	
	protected GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		return gridBagConstraints;
	}
	
	public abstract T getResult();
}
