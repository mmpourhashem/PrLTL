package pltl.trio;

import pltl.bool.BooleanFormulae;

public class WithinF_ii implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public WithinF_ii(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinf_ii " + f + " " + t + ")";
    }
}

