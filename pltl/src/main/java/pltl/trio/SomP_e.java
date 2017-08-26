package pltl.trio;

import pltl.bool.Formula;

public class SomP_e implements Formula {

    Formula f;

    public SomP_e(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(somp_e " + f + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
