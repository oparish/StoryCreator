package storyElements;

import main.Main;

public class Chance
{
	Integer prob;
	
	public Integer getProb() {
		return prob;
	}

	public Chance(Integer prob)
	{
		this.prob = prob;
	}
	
	private boolean check()
	{
		if (prob == null)
			return false;
		else
			return Main.getRandomPercent() < this.prob;
	}
}
