package frontEnd.fieldPanel;

import frontEnd.MyPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

public class ExitPointPanel extends NewStoryElementPanel<ExitPoint>
{
	MyPanel<? extends ExitPoint> exitPointPanel;
	
	public ExitPointPanel(int branchLevel)
	{
		super(branchLevel);
		this.suggestion = Main.getMainSpice().getSuggestion();
		if (Main.getMainScenario().checkLastBranch())
		{
			this.heading = "New Ending";
			this.exitPointPanel = new NewEndingPanel(branchLevel);
		}
		else
		{
			this.heading = "New Branch";
			this.exitPointPanel = new NewBranchPanel(branchLevel);
		}
		this.addPanel(this.exitPointPanel);	
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.EXITPOINT;
	}

	@Override
	public ExitPoint getResult()
	{
		return this.exitPointPanel.getResult();
	}
}
