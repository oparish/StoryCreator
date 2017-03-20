package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FieldPanel;
import frontEnd.FlavourListPanel;
import frontEnd.NewBranchPanel;
import frontEnd.NewEndingPanel;
import frontEnd.NewOptionPanel;
import frontEnd.NewScenarioPanel;
import frontEnd.OldOrNewPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.StoryElement;

@SuppressWarnings("serial")
public abstract class RepeatingOptionList<T extends Option> extends OptionList<T>
{
	public RepeatingOptionList()
	{
		super();
	}
	
	public RepeatingOptionList(ArrayList<T> initialOptions)
	{
		super(initialOptions);
	}
	
	protected abstract StoryElement getDefaultExitPoint(EditorDialog editorDialog);
	
}
