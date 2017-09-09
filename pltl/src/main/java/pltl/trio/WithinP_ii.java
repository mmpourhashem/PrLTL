package pltl.trio;

import pltl.bool.Formula;

public class WithinP_ii implements Formula{

    Formula f;
    int t;

    public WithinP_ii(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinp_ii " + f + " " + t + ")";
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

