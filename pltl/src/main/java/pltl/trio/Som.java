package pltl.trio;

import pltl.bool.Formula;

public class Som implements Formula {

    Formula f;

    public Som(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(som " + f + ")";
    }
}
