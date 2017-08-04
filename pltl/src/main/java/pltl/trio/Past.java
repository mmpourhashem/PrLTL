package pltl.trio;

import pltl.bool.Formula;

public class Past implements Formula {

    Formula f;
    int t;

    public Past(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(past " + f + " " + t + ")";
    }
}
