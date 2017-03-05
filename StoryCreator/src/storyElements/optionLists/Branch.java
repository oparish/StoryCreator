package storyElements.optionLists;

import java.util.ArrayList;

import storyElements.ExitPoint;
import storyElements.options.BranchOption;

@SuppressWarnings("serial")
public class Branch extends StorySection<BranchOption> implements ExitPoint
{
	private ExitPoint defaultExitPoint = null;
	
	public Branch(ArrayList<BranchOption> initialOptions, String description)
	{
		super(initialOptions, description);
	}

}
