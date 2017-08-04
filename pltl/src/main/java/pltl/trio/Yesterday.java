package pltl.trio;

import pltl.bool.Formula;

public class Yesterday implements Formula {

    Formula f;

    public Yesterday(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(yesterday " + f + ")";     
    }
}
