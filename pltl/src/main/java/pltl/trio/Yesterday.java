package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Yesterday implements BooleanFormulae {

    BooleanFormulae f;

    public Yesterday(BooleanFormulae f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(yesterday " + f + ")";     
    }
}
