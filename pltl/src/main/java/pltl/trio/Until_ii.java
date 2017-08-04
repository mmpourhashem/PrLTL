package pltl.trio;

import pltl.bool.Formula;

public class Until_ii implements Formula {

    Formula f1;
    Formula f2;

    public Until_ii(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(until_ii" + f1 + " " + f2 + ")";
    }
}
