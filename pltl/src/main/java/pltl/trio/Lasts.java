package pltl.trio;

import pltl.bool.Formula;

public class Lasts implements Formula{

    Formula f;
    int t;

    public Lasts(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(lasts " + f + " " + t + ")";
    }

}
