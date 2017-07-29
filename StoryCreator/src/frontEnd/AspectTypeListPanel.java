package frontEnd;

import storyElements.AspectType;
import main.Main;

public class AspectTypeListPanel extends MyPanel<AspectType>
{
	private StoryElementList<AspectType> aspectTypes;
	
	public AspectTypeListPanel()
	{
		super();
		this.aspectTypes = StoryElementList.create(Main.getMainSpice().getAspectTypes().values());
		this.addStoryElementList(this.aspectTypes);
	}
	

	@Override
	public AspectType getResult()
	{
		return this.aspectTypes.getSelectedElement();
	}

}
