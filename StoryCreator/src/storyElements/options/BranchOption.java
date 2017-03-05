package storyElements.options;

import storyElements.ExitPoint;

public class BranchOption extends Option
{
	private ExitPoint exitPoint;
	private FlavourOption flavour;
	
	public BranchOption(String description)
	{
		super(description);
		this.exitPoint = exitPoint;
		this.flavour = flavour;
	}
	
	public FlavourOption getFlavour() {
		return flavour;
	}

	public void setFlavour(FlavourOption flavour) {
		this.flavour = flavour;
	}
	
	public ExitPoint getExitPoint() {
		return exitPoint;
	}

	public void setExitPoint(ExitPoint exitPoint) {
		this.exitPoint = exitPoint;
	}
}
