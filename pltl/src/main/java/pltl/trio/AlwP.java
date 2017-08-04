package pltl.trio;

import pltl.bool.Formula;

public class AlwP implements Formula {

    Formula f;

    public AlwP(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(alwP " + f + ")";
    }
}
