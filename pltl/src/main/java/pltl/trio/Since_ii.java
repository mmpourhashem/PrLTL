package pltl.trio;

import pltl.bool.Formula;

public class Since_ii implements Formula {

    Formula f1;
    Formula f2;

    public Since_ii(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(since_ii " + f1 + " " + f2 + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public Formula getProp(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
