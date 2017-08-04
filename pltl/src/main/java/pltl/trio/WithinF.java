package pltl.trio;

import pltl.bool.Formula;

public class WithinF implements Formula{

    Formula f;
    int t;

    public WithinF(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinf " + f + " " + t + ")";
    }
}

