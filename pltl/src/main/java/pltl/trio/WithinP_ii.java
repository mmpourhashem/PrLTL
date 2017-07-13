package pltl.trio;

import pltl.bool.BooleanFormulae;

public class WithinP_ii implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public WithinP_ii(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinp_ii " + f + " " + t + ")";
    }
}

