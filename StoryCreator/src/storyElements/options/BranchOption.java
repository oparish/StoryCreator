package storyElements.options;

import storyElements.ExitPoint;

public class BranchOption extends Option
{
	private ExitPoint exitPoint;
	private FlavourOption flavour;
	
	public BranchOption(String description, ExitPoint exitPoint, FlavourOption flavour)
	{
		super(description);
		this.exitPoint = exitPoint;
		this.flavour = flavour;
	}
}
