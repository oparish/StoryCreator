package storyElements.optionLists;

import java.util.ArrayList;

import storyElements.options.Option;

@SuppressWarnings("serial")
public class RepeatingOptionList<T extends Option> extends OptionList<T>
{

	public RepeatingOptionList(ArrayList<T> initialOptions)
	{
		super(initialOptions);
	}

}
