package pltl.trio;

import pltl.bool.Formula;

public class AlwF implements Formula {

    Formula f;

    public AlwF(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(alwf " + f + ")";
    }
}
