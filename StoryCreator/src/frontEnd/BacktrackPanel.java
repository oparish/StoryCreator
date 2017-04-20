package frontEnd;

import java.util.ArrayList;

import storyElements.ExitPoint;

public class BacktrackPanel extends MyPanel<ExitPoint>
{
	StoryElementList<ExitPoint> exitPointList;
	
	public BacktrackPanel(ArrayList<ExitPoint> exitPoints)
	{
		super();
		this.exitPointList = StoryElementList.create(exitPoints);
		this.addStoryElementList(this.exitPointList);
	}
	
	@Override
	public ExitPoint getResult()
	{
		return this.exitPointList.getSelectedElement();
	}

}
