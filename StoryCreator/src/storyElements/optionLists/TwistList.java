package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.json.JsonObjectBuilder;

import storyElements.options.BranchOption;
import storyElements.options.Option;
import storyElements.options.TwistOption;
import frontEnd.EditorDialog;
import frontEnd.NewOptionDialog;
import main.Main;

public class TwistList extends NonRepeatingOptionList
{
	protected String type = "Twist";
	
	public Option generateOption(EditorDialog editorDialog)
	{
		int rnd = Main.getRandomNumberInRange(this.size() + 1);
		if (rnd < this.size())
		{
			return (Option) this.get(rnd);
		}
		else
		{
			NewOptionDialog newOptionDialog = new NewOptionDialog(editorDialog, true);
			newOptionDialog.setTitle("New " + this.type);
			Main.showWindowInCentre(newOptionDialog);
        	TwistOption branchOption = (TwistOption) newOptionDialog.getTwistOption();
        	TwistList.this.add(branchOption);
            return branchOption;
        }  
	}
}
