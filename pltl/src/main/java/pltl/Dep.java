package pltl;

import java.util.ArrayList;

import pltl.bool.Formula;

public class Dep implements Formula{
	private Formula depF;
	private ArrayList<Formula> parentsF;
	private int left, right;
	
	public Dep(int left, int right, Formula depF, ArrayList<Formula> parentsF) {
		this.left = left;
		this.right = right;
		DepSetter(depF, parentsF);
	}
	
	public Dep(int time, Formula depF, ArrayList<Formula> parentsF) {
		this.left = time;
		this.right = time;
		DepSetter(depF, parentsF);
	}
	
	private void DepSetter(Formula depF, ArrayList<Formula> parentsF) {
		this.depF = depF;
		this.parentsF = parentsF;		
	}
	
	public Formula getDepF() {
		return depF;
	}
	
	public ArrayList<Formula> getParentsF() {
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

	public Formula get(int offset) {
		return null;
	}

	public Formula getProp(int offset) {
		return null;// Not required.
	}

}