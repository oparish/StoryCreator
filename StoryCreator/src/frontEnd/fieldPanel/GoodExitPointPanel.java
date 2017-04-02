package frontEnd.fieldPanel;

import frontEnd.MyPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

public class GoodExitPointPanel extends NewStoryElementPanel<ExitPoint>
{
	MyPanel<? extends ExitPoint> exitPointPanel;
	
	public GoodExitPointPanel(int branchLevel)
	{
		super(branchLevel);
		this.suggestion = Main.getMainSpice().getGoodSuggestion();
		if (Main.getMainScenario().checkLastBranch())
		{
			this.heading = "New Good Ending";
			this.exitPointPanel = new NewGoodEndingPanel(branchLevel);
		}
		else
		{
			this.heading = "New Good Branch";
			this.exitPointPanel = new NewGoodBranchPanel(branchLevel);
		}
		this.addPanel(this.exitPointPanel);	
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.GOODEXITPOINT;
	}

	@Override
	public ExitPoint getResult()
	{
		return this.exitPointPanel.getResult();
	}
}
