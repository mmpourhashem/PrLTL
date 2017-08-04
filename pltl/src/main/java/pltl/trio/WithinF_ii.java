package pltl.trio;

import pltl.bool.Formula;

public class WithinF_ii implements Formula{

    Formula f;
    int t;

    public WithinF_ii(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinf_ii " + f + " " + t + ")";
    }
}

