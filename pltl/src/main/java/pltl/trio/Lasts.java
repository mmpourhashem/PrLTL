package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Lasts implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public Lasts(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(lasts " + f + " " + t + ")";
    }

}
