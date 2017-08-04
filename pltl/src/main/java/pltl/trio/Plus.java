package pltl.trio;

import pltl.bool.*;

public class Plus implements Formula{

	Formula a;
	Formula b;
	
    public Plus(Formula a, Formula b) {
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "([+] "+a+" "+b+")";
    }
}
