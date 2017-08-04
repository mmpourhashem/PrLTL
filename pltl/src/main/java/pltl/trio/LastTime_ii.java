package pltl.trio;

import pltl.bool.Formula;

public class LastTime_ii implements Formula {

    Formula f;
    int t;

    public LastTime_ii(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(Lasttime_ii " + f + " " + t + ")";
    }
}