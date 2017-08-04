package pltl.trio;

import pltl.bool.Formula;

public class Trigger implements Formula {

    Formula f1;
    Formula f2;

    public Trigger(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(trigger " + f1 + " " + f2 + ")";
    }
}