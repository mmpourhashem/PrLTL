package pltl.trio;

import pltl.PltlFormula;
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

	public Formula getProp(int offset) {
//		if (PltlFormula.outOfBound(offset))
//    		return new PltlFormula.PropFalse();
//		
		// TODO Auto-generated method stub
		return null;
	}
}
