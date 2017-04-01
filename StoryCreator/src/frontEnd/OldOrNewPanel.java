package frontEnd;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewOptionPanel;
import frontEnd.fieldPanel.OptionContentPanel;
import storyElements.Token;
import storyElements.options.BranchOption;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;
import main.Main;

public class OldOrNewPanel<T extends StoryElement> extends FieldPanel<T> implements OptionContentPanel, ActionListener
{
	FieldPanel<T> fieldPanel;
	JRadioButton oldButton = new JRadioButton("Old", true);
	JRadioButton newButton = new JRadioButton("New");
	StoryElementList<T> storyElementList;
	
	public OldOrNewPanel(OptionContentType optionContentType, int branchLevel)
	{
		super(branchLevel);
		try {
			this.fieldPanel = optionContentType.getFieldPanelClass().getConstructor(int.class).newInstance(branchLevel);
			this.heading = this.fieldPanel.getHeading();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(this.oldButton);
		this.oldButton.addActionListener(this);
		buttonsPanel.add(this.newButton);
		this.newButton.addActionListener(this);
		this.addPanel(buttonsPanel);

		Collection storyElements = Main.getMainScenario().getStoryElementList(optionContentType, branchLevel);
		this.storyElementList = StoryElementList.create(storyElements);
		this.addStoryElementList(this.storyElementList);
		
		this.addPanel(fieldPanel);
		this.fieldPanel.setVisible(false);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(this.oldButton);
		buttonGroup.add(this.newButton);
	}
	
	public T getResult()
	{
		if (this.oldButton.isSelected())
		{
			T storyElement = this.storyElementList.getSelectedElement();
			if (storyElement == null)
				return this.storyElementList.getModel().getElementAt(0);
			else
				return storyElement;
		}
		else
			return this.fieldPanel.getResult();
	}
	
	public static void main(String[] args)
	{
//		BranchOption branchOption = new BranchOption("Test");
//		OldOrNewPanel<Token> oldOrNewPanel = new OldOrNewPanel<Token>(OptionContentType.TOKEN, 0);
//		FieldDialog fieldDialog = new FieldDialog(null, true, new FieldPanel[]{oldOrNewPanel});
//		Main.showWindowInCentre(fieldDialog);
//		System.out.println(oldOrNewPanel.getResult().getDescription());
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return ((OptionContentPanel) this.fieldPanel).getOptionContentType();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.oldButton)
		{
			this.fieldPanel.setVisible(false);
		}
		else
		{
			this.fieldPanel.setVisible(true);
		}
	}
}
