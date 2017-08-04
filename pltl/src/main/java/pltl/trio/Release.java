package pltl.trio;

import pltl.bool.Formula;

public class Release implements Formula {

    Formula f1;
    Formula f2;

    public Release(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(release " + f1 + " " + f2 + ")";
    }
}