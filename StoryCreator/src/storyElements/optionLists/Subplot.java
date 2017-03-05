package storyElements.optionLists;

import java.util.ArrayList;

import storyElements.options.SubplotOption;

@SuppressWarnings("serial")
public class Subplot extends StorySection<SubplotOption>
{
	public Subplot(ArrayList<SubplotOption> initialOptions, String description)
	{
		super(initialOptions, description);
	}

}
