package storyElements.optionLists;

import java.util.ArrayList;

import storyElements.options.Option;

@SuppressWarnings("serial")
public class NonRepeatingOptionList<T extends Option> extends OptionList<T> {

	public NonRepeatingOptionList(ArrayList<T> initialOptions)
	{
		super(initialOptions);
	}

}
