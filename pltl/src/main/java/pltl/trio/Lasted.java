package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Lasted implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public Lasted(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(lasted " + f + " " + t + ")";
    }

}
