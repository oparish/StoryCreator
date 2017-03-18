package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.json.JsonObjectBuilder;

import storyElements.options.BranchOption;
import storyElements.options.Option;
import storyElements.options.TwistOption;
import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FieldPanel;
import frontEnd.NewOptionPanel;
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
			NewOptionPanel newOptionPanel = new NewOptionPanel();
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{newOptionPanel});
			newOptionDialog.setTitle("New " + this.type);
			Main.showWindowInCentre(newOptionDialog);
        	TwistOption branchOption = (TwistOption) newOptionPanel.getTwistOption();
        	TwistList.this.add(branchOption);
            return branchOption;
        }  
	}
}
