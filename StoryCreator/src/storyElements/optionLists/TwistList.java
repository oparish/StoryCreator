package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.json.JsonObjectBuilder;

import storyElements.options.BranchOption;
import storyElements.options.TwistOption;
import frontEnd.EditorDialog;
import frontEnd.NewOptionDialog;
import main.Main;

public class TwistList extends NonRepeatingOptionList
{
	protected String type = "Twist";
	
	public void generateOption(EditorDialog editorDialog)
	{
		int rnd = Main.getRandomNumberInRange(this.size() + 1);
		if (rnd < this.size())
		{
			editorDialog.setTwistOption((TwistOption) this.get(rnd));
		}
		else
		{
			NewOptionDialog newOptionDialog = new NewOptionDialog(editorDialog, true, this);
			newOptionDialog.setTitle("New " + this.type);
			newOptionDialog.addWindowListener(new WindowAdapter() {  
	            public void windowClosing(WindowEvent e) {
	            	NewOptionDialog newOptionDialog = (NewOptionDialog) e.getWindow();
	            	TwistOption branchOption = (TwistOption) newOptionDialog.getTwistOption();
	            	EditorDialog editorDialog = (EditorDialog) newOptionDialog.getOwner();
	                editorDialog.setTwistOption(branchOption);
	                newOptionDialog.getOptionList().add(branchOption);
	            }  
	        });
			Main.showWindowInCentre(newOptionDialog);
		}	
	}
}
