package pltl.trio;

import pltl.bool.Formula;

public class Since implements Formula {

	private Formula f1;
	private Formula f2;

	public Since(Formula f1, Formula f2) {
		this.f1 = f1;
		this.f2 = f2;
	}

	public Formula getFormula2() {
		return f2;
	}

	public Formula getFormula1() {
		return f1;
	}

	@Override
	public String toString() {
		return "(since " + f1 + " " + f2 + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Since)
			return f1.equals(((Since) o).getFormula1()) && f2.equals(((Since) o).getFormula2());

		return false;
	}

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}