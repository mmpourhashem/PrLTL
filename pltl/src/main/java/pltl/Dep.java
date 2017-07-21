package pltl;

import java.util.ArrayList;

import pltl.bool.BooleanFormulae;

public class Dep implements BooleanFormulae{
	private BooleanFormulae depF;
	private ArrayList<BooleanFormulae> parentsF;
	private int left, right;
	
	public Dep(int left, int right, BooleanFormulae depF, ArrayList<BooleanFormulae> parentsF) {
		this.left = left;
		this.right = right;
		DepSetter(depF, parentsF);
	}
	
	public Dep(int time, BooleanFormulae depF, ArrayList<BooleanFormulae> parentsF) {
		this.left = time;
		this.right = time;
		DepSetter(depF, parentsF);
	}
	
	private void DepSetter(BooleanFormulae depF, ArrayList<BooleanFormulae> parentsF) {
		this.depF = depF;
		this.parentsF = parentsF;		
	}

	
	public BooleanFormulae getDepF() {
		return depF;
	}
	
	public ArrayList<BooleanFormulae> getParentsF() {
		return parentsF;
	}
	
	@Override
	public String toString() {
		String s = "(dep " + depF.toString();
		for (int i = 0; i < parentsF.size(); i++) {
			s = s + " " + parentsF.get(i).toString();
		}
		
		return s + ")";
	}

}