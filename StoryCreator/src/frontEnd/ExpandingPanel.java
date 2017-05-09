package frontEnd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import main.Main;
import storyElements.options.AspectOption;
import storyElements.options.ExpandingContentType;
import storyElements.options.StoryElement;

public class ExpandingPanel<T extends MyPanel, U extends StoryElement> extends JPanel implements ActionListener
{
	ArrayList<T> panels = new ArrayList<T>();
	ExpandingContentType expandingContentType;
	int yPos = 0;
	
	public ExpandingPanel(ExpandingContentType expandingContentType)
	{
		super();
		this.expandingContentType = expandingContentType;
		this.setLayout(new GridBagLayout());
		JButton expandButton = new JButton(this.expandingContentType.getText());
		expandButton.addActionListener(this);
		this.add(expandButton, this.setupGridBagConstraints(0, 0, 1, 1, 0));
		yPos++;
	}
	
	public ArrayList<U> getResult()
	{
		ArrayList<U> results = new ArrayList<U>();
		for (MyPanel panel : this.panels)
		{
			results.add((U) panel.getResult());
		}
		return results;
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

	private void addPanel()
	{
		T newPanel = (T) this.expandingContentType.getNewPanel();
		this.panels.add(newPanel);
		this.add(newPanel, this.setupGridBagConstraints(0, yPos, 1, 1, 1));
		this.yPos++;
		Main.getWindow().validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.addPanel();
	}
	
	public static void main(String[] args)
	{
		JDialog window = new JDialog(new JDialog(), true);
		window.setSize(500, 500);
		ExpandingPanel<AspectOptionPanel, AspectOption> expandingPanel = 
				new ExpandingPanel<AspectOptionPanel, AspectOption>(ExpandingContentType.ASPECTOPTION);
		window.add(expandingPanel);
		Main.showWindowInCentre(window);
		for (AspectOption aspectOption : expandingPanel.getResult())
		{
			System.out.println(aspectOption.getJsonObjectBuilder().build());
		}
	}
}
