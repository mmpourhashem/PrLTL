package pltl.trio;

import pltl.bool.Formula;

public class Lasted_ie implements Formula{

    Formula f;
    int t;

    public Lasted_ie(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(lasted_ie " + f + " " + t + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}
