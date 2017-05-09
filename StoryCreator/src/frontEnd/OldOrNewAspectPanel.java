package frontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.Main;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewStoryElementPanel;
import storyElements.Aspect;
import storyElements.AspectType;

public class OldOrNewAspectPanel extends MyPanel<Aspect> implements ActionListener
{
	AspectPanel aspectPanel;
	JRadioButton oldButton = new JRadioButton("Old", true);
	JRadioButton newButton = new JRadioButton("New");
	StoryElementList<Aspect> storyElementList;
	
	public OldOrNewAspectPanel(EditorDialog editorDialog, AspectType aspectType)
	{
		super();
		this.aspectPanel = new AspectPanel(editorDialog, aspectType);
		this.heading = this.aspectPanel.getHeading();

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(this.oldButton);
		this.oldButton.addActionListener(this);
		buttonsPanel.add(this.newButton);
		this.newButton.addActionListener(this);
		this.addPanel(buttonsPanel);

		Collection storyElements = aspectType.getAspectCollection();
		this.storyElementList = StoryElementList.create(storyElements);
		this.addStoryElementList(this.storyElementList);
		
		this.addPanel(this.aspectPanel);
		this.aspectPanel.setVisible(false);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(this.oldButton);
		buttonGroup.add(this.newButton);
	}

	@Override
	public Aspect getResult()
	{
		if (this.oldButton.isSelected())
		{
			Aspect storyElement = this.storyElementList.getSelectedElement();
			if (storyElement == null)
				return this.storyElementList.getModel().getElementAt(0);
			else
				return storyElement;
		}
		else
			return this.aspectPanel.getResult();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.oldButton)
		{
			this.aspectPanel.setVisible(false);
		}
		else
		{
			this.aspectPanel.setVisible(true);
		}
	}
}
