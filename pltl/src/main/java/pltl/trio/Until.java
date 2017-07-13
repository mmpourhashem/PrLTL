package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Until implements BooleanFormulae {

    public BooleanFormulae f1;
    public BooleanFormulae f2;

    public Until(BooleanFormulae f1, BooleanFormulae f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(until " + f1 + " " + f2 + ")";
    }
}
