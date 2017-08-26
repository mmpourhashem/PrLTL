package pltl.trio;

import pltl.bool.Formula;

public class Until_ii implements Formula {

	private Formula f1;
	private Formula f2;

    public Until_ii(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(until_ii" + f1 + " " + f2 + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
