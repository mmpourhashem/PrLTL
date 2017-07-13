package pltl.trio;

import pltl.bool.BooleanFormulae;

public class WithinP implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public WithinP(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinp " + f + " " + t + ")";
    }
}

