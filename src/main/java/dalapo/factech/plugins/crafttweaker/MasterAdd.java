package dalapo.factech.plugins.crafttweaker;

import java.util.List;

import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;

import crafttweaker.IAction;

abstract class MasterAdd<I, O> implements IAction
{
	protected I input;
	protected O output;
	protected boolean worksWithBad;
	protected List<MachineRecipe<I, O>> list;
	
	public MasterAdd(I in, O out, boolean bad, List<MachineRecipe<I, O>> list)
	{
		input = in;
		output = out;
		worksWithBad = bad;
		this.list = list;
	}
	
	@Override
	public void apply()
	{
		list.add(new MachineRecipe<I,O>(input, output, worksWithBad));
	}
}