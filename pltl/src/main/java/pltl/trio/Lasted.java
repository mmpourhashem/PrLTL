package pltl.trio;

import pltl.bool.Formula;

public class Lasted implements Formula{

    Formula f;
    int t;

    public Lasted(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(lasted " + f + " " + t + ")";
    }

}
