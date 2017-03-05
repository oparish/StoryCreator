package storyElements.optionLists;

import java.util.ArrayList;

import storyElements.options.FlavourOption;
import storyElements.options.Option;

@SuppressWarnings("serial")
public abstract class StorySection<T extends Option> extends RepeatingOptionList<T>
{
	String description;
	FlavourOption flavour;
	
	public StorySection(ArrayList<T> initialOptions, String description)
	{
		super(initialOptions);
		this.description = description;
	}
}
