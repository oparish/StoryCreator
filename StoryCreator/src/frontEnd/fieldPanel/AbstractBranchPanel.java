package frontEnd.fieldPanel;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import frontEnd.AspectTypeListPanel;
import frontEnd.AspectTypePanel;
import frontEnd.ButtonID;
import frontEnd.ExpandingPanel;
import frontEnd.FieldDialog;
import frontEnd.MyButton;
import frontEnd.MyPanel;
import frontEnd.StoryElementList;
import main.Main;
import storyElements.AspectType;
import storyElements.optionLists.Branch;
import storyElements.options.BranchOption;
import storyElements.options.ExpandingContentType;
import storyElements.options.OptionContentType;

public abstract class AbstractBranchPanel extends MyPanel<Branch> implements ActionListener
{
	private static final String BRANCH_DESCRIPTION = "Branch Description";
	private static final String INITIAL_OPTION = "Initial Option";

	private JTextField branchDescriptionField;
	private ArrayList<JTextField> initialOptionFields;
	private ExpandingPanel<AspectTypeListPanel, AspectType> aspectTypesPanel;
	
	public AbstractBranchPanel(int branchLevel, String suggestion)
	{
		super();
		
		this.addJLabel(Main.getMainScenario().getBranchLevel(branchLevel).getDescription());
		
		this.branchDescriptionField = new JTextField();
		this.addTextField(this.branchDescriptionField, BRANCH_DESCRIPTION);
		
		MyButton myButton = this.addJButton(ButtonID.NEW_ASPECT_TYPE, "New Aspect Type");
		myButton.addActionListener(this);
		
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < Main.INITIALOPTIONS_FOR_BRANCH; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i);
		}
		
		this.aspectTypesPanel = new ExpandingPanel<>(ExpandingContentType.EXISTING_ASPECTTYPE, "Aspect Types");
		this.addPanel(this.aspectTypesPanel);
	}
	
	private ArrayList<Integer> getAspectTypes()
	{
		ArrayList<AspectType> aspectTypes = this.aspectTypesPanel.getResult();
		ArrayList<Integer> aspectTypeIDs = new ArrayList<Integer>();
		for (AspectType aspectType : aspectTypes)
		{
			aspectTypeIDs.add(Main.getMainSpice().getAspectIDByType(aspectType));
		}
		return aspectTypeIDs;
	}
	
	public Branch getResult()
	{
		ArrayList<BranchOption> initialOptions = new ArrayList<BranchOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new BranchOption(textField.getText()));
		}
		return new Branch(initialOptions, this.branchDescriptionField.getText(), Main.getMainScenario().getNextBranch(), 
				this.getAspectTypes());
	}
	
	private void newAspectType()
	{
		AspectTypePanel aspectTypePanel = new AspectTypePanel();
		FieldDialog fieldDialog = new FieldDialog(null, true, aspectTypePanel);
		Main.showWindowInCentre(fieldDialog);
		Main.getMainSpice().addAspectType(Main.getMainSpice().getAspectTypes().size(), aspectTypePanel.getResult());
		Main.setWindow(Main.findWindow(this));
		this.remove(this.aspectTypesPanel);
		this.aspectTypesPanel = new ExpandingPanel<>(ExpandingContentType.EXISTING_ASPECTTYPE, "Aspect Types");
		this.addPanel(this.aspectTypesPanel);
		Main.findWindow(this).validate();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		MyButton button = (MyButton) e.getSource();
		switch(button.getId())
		{
			case NEW_ASPECT_TYPE:
				this.newAspectType();
				break;
			default:
		}
	}
}
