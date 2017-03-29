package frontEnd;

import java.awt.GridLayout;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import storyElements.options.BranchOption;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;
import main.Main;

public class OldOrNewPanel<T extends StoryElement> extends FieldPanel implements OptionContentPanel
{
	T existingObject;
	FieldPanel<T> fieldPanel;
	JRadioButton oldButton = new JRadioButton("Old", true);
	JRadioButton newButton = new JRadioButton("New");
	
	public OldOrNewPanel(T existingObject, FieldPanel<T> fieldPanel)
	{
		super("Old or New?");
		this.existingObject = existingObject;
		this.fieldPanel = fieldPanel;
		this.addJLabel("Old: " + existingObject.getDescription());
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(this.oldButton);
		buttonsPanel.add(this.newButton);
		this.addPanel(buttonsPanel);

		this.addPanel(fieldPanel);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(this.oldButton);
		buttonGroup.add(this.newButton);
	}
	
	public T getResult()
	{
		if (this.oldButton.isSelected())
			return this.existingObject;
		else
			return this.fieldPanel.getResult();
	}
	
	public static void main(String[] args)
	{
		BranchOption branchOption = new BranchOption("Test");
		OldOrNewPanel<BranchOption> oldOrNewPanel = new OldOrNewPanel<BranchOption>(branchOption, new NewOptionPanel());
		FieldDialog fieldDialog = new FieldDialog(null, true, new FieldPanel[]{oldOrNewPanel});
		Main.showWindowInCentre(fieldDialog);
		System.out.println(oldOrNewPanel.getResult().getDescription());
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return ((OptionContentPanel) this.fieldPanel).getOptionContentType();
	}
}
