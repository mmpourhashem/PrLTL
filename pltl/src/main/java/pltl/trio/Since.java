package pltl.trio;

import pltl.bool.Formula;

public class Since implements Formula {

    Formula f1;
    Formula f2;

    public Since(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(since " + f1 + " " + f2 + ")";
    }
}