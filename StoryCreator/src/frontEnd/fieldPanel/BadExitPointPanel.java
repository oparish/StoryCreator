package frontEnd.fieldPanel;

import frontEnd.MyPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

public class BadExitPointPanel extends NewStoryElementPanel<ExitPoint>
{
	MyPanel<? extends ExitPoint> exitPointPanel;
	
	public BadExitPointPanel(int branchLevel)
	{
		super(branchLevel);
		this.suggestion = Main.getMainSpice().getBadSuggestion();
		if (Main.getMainScenario().checkLastBranch())
		{
			this.heading = "New Bad Ending";
			this.exitPointPanel = new NewBadEndingPanel(branchLevel);
		}
		else
		{
			this.heading = "New Bad Branch";
			this.exitPointPanel = new NewBadBranchPanel(branchLevel);
		}
		this.addPanel(this.exitPointPanel);	
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.BADEXITPOINT;
	}

	@Override
	public ExitPoint getResult()
	{
		return this.exitPointPanel.getResult();
	}
}
