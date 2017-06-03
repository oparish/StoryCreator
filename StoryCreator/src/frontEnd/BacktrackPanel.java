package frontEnd;

import java.util.ArrayList;

import frontEnd.EditorDialog.CurrentStatus;
import storyElements.ExitPoint;

public class BacktrackPanel extends MyPanel<CurrentStatus>
{
	StoryElementList<CurrentStatus> exitPointList;
	
	public BacktrackPanel(ArrayList<CurrentStatus> exitPoints)
	{
		super();
		this.exitPointList = StoryElementList.create(exitPoints);
		this.addStoryElementList(this.exitPointList);
	}
	
	@Override
	public CurrentStatus getResult()
	{
		return this.exitPointList.getSelectedElement();
	}

}
