package pltl.trio;

import pltl.bool.Formula;

public class SomP implements Formula {

    Formula f;

    public SomP(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(somp " + f + ")";
    }
}
