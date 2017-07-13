package pltl.trio;

import pltl.bool.BooleanFormulae;

public class WithinP_ie implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public WithinP_ie(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinp_ie " + f + " " + t + ")";
    }
}

