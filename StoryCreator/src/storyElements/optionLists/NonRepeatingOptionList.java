package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.json.JsonObject;

import main.Main;
import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FieldPanel;
import frontEnd.NewOptionPanel;
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
		
	public Option generateOption(EditorDialog editorDialog)
	{
		int rnd = Main.getRandomNumberInRange(this.size() + 1);
		if (rnd < this.size())
		{
			return this.get(rnd);
		}
		else
		{
			NewOptionPanel newOptionPanel = new NewOptionPanel();
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{newOptionPanel});
			newOptionDialog.setTitle("New " + this.type);
			Main.showWindowInCentre(newOptionDialog);
        	BranchOption branchOption = newOptionPanel.getOption();
            NonRepeatingOptionList.this.add(branchOption);
            return branchOption;
		}	
	}
	
}
