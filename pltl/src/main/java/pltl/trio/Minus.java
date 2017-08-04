package pltl.trio;

import pltl.bool.*;

public class Minus implements Formula{

	Formula a;
	Formula b;

    public Minus(Formula a, Formula b) {
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "([-] "+a+" "+b+")";
    }
}
