package pltl.trio;

import pltl.bool.Formula;

public class WithinP implements Formula{

    Formula f;
    int t;

    public WithinP(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
       return "(withinp " + f + " " + t + ")";
    }
}

