package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import main.Main;
import frontEnd.EditorDialog;
import frontEnd.NewOptionDialog;
import storyElements.options.BranchOption;
import storyElements.options.Option;

@SuppressWarnings("serial")
public class NonRepeatingOptionList<T extends Option> extends OptionList<T> {

	public NonRepeatingOptionList(ArrayList<T> initialOptions)
	{
		super(initialOptions);
	}

	public void generateOption(EditorDialog editorDialog)
	{
		int rnd = Main.getRandomNumberInRange(NonRepeatingOptionList.this.size() + 1);
		if (rnd < NonRepeatingOptionList.this.size())
		{
			editorDialog.setOption(NonRepeatingOptionList.this.get(rnd));
		}
		else
		{
			NewOptionDialog newScenarioDialog = new NewOptionDialog(editorDialog, true, NonRepeatingOptionList.this);
			newScenarioDialog.addWindowListener(new WindowAdapter() {  
	            public void windowClosing(WindowEvent e) {
	            	NewOptionDialog newOptionDialog = (NewOptionDialog) e.getWindow();
	            	BranchOption branchOption = newOptionDialog.getOption();
	            	EditorDialog editorDialog = (EditorDialog) newOptionDialog.getOwner();
	                editorDialog.setOption(branchOption);
	                newOptionDialog.getOptionList().add(branchOption);
	            }  
	        });
			Main.showWindowInCentre(newScenarioDialog);
		}	
	}
	
}
