package storyElements;

import java.util.ArrayList;
import java.util.HashMap;

import storyElements.optionLists.Branch;
import storyElements.optionLists.FlavourList;
import storyElements.optionLists.Subplot;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.SimpleBranchOption;

public class Scenario
{
	HashMap<Integer, Branch> branches = new HashMap<Integer, Branch>();
	HashMap<Integer, Subplot> subplots = new HashMap<Integer, Subplot>();
	HashMap<Integer, FlavourList> flavourLists = new HashMap<Integer, FlavourList>();
	HashMap<Integer, EndingOption> endings = new HashMap<Integer, EndingOption>();
	Branch startingBranch;
	Chance optionBecomesSubplot = null;
	Chance optionBecomesNewBranch = null;
	Chance flavourHasSubFlavour = null;
	int branchLength;
	int subplotLength;

	public Scenario(ArrayList<SimpleBranchOption> initialBranchOptions,	String initialBranchDescription, int branchLength, int subplotLength)
	{
		this.startingBranch = this.setupNewBranch(initialBranchOptions, initialBranchDescription);
		branches.put(0, this.startingBranch);
		this.branchLength = branchLength;
		this.subplotLength = subplotLength;
	}
	
	public int getSubplotLength() {
		return subplotLength;
	}
	
	public int getBranchLength() {
		return branchLength;
	}
	
	public Chance getOptionBecomesNewBranch() {
		return optionBecomesNewBranch;
	}

	public void setOptionBecomesNewBranch(Chance optionBecomesNewBranch) {
		this.optionBecomesNewBranch = optionBecomesNewBranch;
	}

	public Chance getFlavourHasSubFlavour() {
		return flavourHasSubFlavour;
	}

	public void setFlavourHasSubFlavour(Chance flavourHasSubFlavour) {
		this.flavourHasSubFlavour = flavourHasSubFlavour;
	}
	
	public Chance getOptionBecomesSubplot() {
		return optionBecomesSubplot;
	}

	public void setOptionBecomesSubplot(Chance optionBecomesSubplot) {
		this.optionBecomesSubplot = optionBecomesSubplot;
	}
	
	private Branch setupNewBranch(ArrayList<SimpleBranchOption> initialBranchOptions, String initialBranchDescription)
	{
		ArrayList<BranchOption> branchOptions = new ArrayList<BranchOption>();
		for (SimpleBranchOption simpleBranchOption : initialBranchOptions)
		{
			branchOptions.add(simpleBranchOption);
		}
		Branch newBranch = new Branch(branchOptions, initialBranchDescription);
		return newBranch;
	}
}
