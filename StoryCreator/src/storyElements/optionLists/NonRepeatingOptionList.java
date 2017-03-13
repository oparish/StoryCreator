package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.json.JsonObject;

import main.Main;
import frontEnd.EditorDialog;
import frontEnd.NewOptionDialog;
import storyElements.options.BranchOption;
import storyElements.options.Option;

@SuppressWarnings("serial")
public class NonRepeatingOptionList<T extends Option> extends OptionList<T> {

	protected String type = "Option";
	
	public NonRepeatingOptionList(ArrayList<T> initialOptions)
	{
		super(initialOptions);
	}
	
	public NonRepeatingOptionList()
	{
		super();
	}
		
	public void generateOption(EditorDialog editorDialog)
	{
		int rnd = Main.getRandomNumberInRange(this.size() + 1);
		if (rnd < this.size())
		{
			editorDialog.setOption(this.get(rnd));
		}
		else
		{
			NewOptionDialog newOptionDialog = new NewOptionDialog(editorDialog, true, this);
			newOptionDialog.setTitle("New " + this.type);
			newOptionDialog.addWindowListener(new WindowAdapter() {  
	            public void windowClosing(WindowEvent e) {
	            	NewOptionDialog newOptionDialog = (NewOptionDialog) e.getWindow();
	            	BranchOption branchOption = newOptionDialog.getOption();
	            	EditorDialog editorDialog = (EditorDialog) newOptionDialog.getOwner();
	                editorDialog.setOption(branchOption);
	                newOptionDialog.getOptionList().add(branchOption);
	            }  
	        });
			Main.showWindowInCentre(newOptionDialog);
		}	
	}
	
}
