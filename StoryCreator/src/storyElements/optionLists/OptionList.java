package storyElements.optionLists;

import java.util.ArrayList;

import storyElements.options.Option;

@SuppressWarnings("serial")
public abstract class OptionList<T extends Option> extends ArrayList<Option>
{
	
	public OptionList(ArrayList<T> initialOptions)
	{
		super();
		for (T option : initialOptions)
		{
			this.add(option);
		}
	}
	
}
