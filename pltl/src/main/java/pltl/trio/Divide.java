package pltl.trio;

import pltl.bool.*;

public class Divide implements Formula{

    Formula a;
    Formula b;

    public Divide(Formula a, Formula b) {
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "([/] "+a+" "+b+")";
    }

	public Formula get(int offset) {
		return null;
	}
}
