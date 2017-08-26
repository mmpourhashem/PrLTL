package pltl.trio;

import pltl.bool.Formula;

public class Until_ei implements Formula {

	private Formula f1;
	private Formula f2;

    public Until_ei(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(until_ei " + f1 + " " + f2 + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
