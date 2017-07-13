package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Lasted_ie implements BooleanFormulae{

    BooleanFormulae f;
    int t;

    public Lasted_ie(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(lasted_ie " + f + " " + t + ")";
    }

}
