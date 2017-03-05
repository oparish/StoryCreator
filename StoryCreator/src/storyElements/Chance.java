package storyElements;

import main.Main;

public class Chance
{
	int prob;
	
	public Chance(int prob)
	{
		this.prob = prob;
	}
	
	private boolean check()
	{
		return Main.getRandomPercent() < this.prob;
	}
}
