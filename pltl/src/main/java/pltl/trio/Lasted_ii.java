package pltl.trio;

import pltl.bool.Formula;

public class Lasted_ii implements Formula{

    Formula f;
    int t;

    public Lasted_ii(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(lasted_ii " + f + " " + t + ")";
    }

}