package pltl.trio;

import pltl.bool.BooleanFormulae;

public class GTE implements BooleanFormulae {

    BooleanFormulae a;
    BooleanFormulae b;
    
    public GTE(BooleanFormulae a, BooleanFormulae b){
        this.a=a;
        this.b=b;
    }  
    
    @Override
    public String toString() {
        return "([>=] " + a + " " + b + ")";
    }
}
