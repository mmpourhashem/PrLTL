package pltl.trio;

import pltl.bool.BooleanFormulae;

public class WithinF implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public WithinF(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinf " + f + " " + t + ")";
    }
}

