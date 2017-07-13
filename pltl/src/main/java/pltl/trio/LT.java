package pltl.trio;

import pltl.bool.BooleanFormulae;

public class LT implements BooleanFormulae {

    BooleanFormulae a;
    BooleanFormulae b;
    
    public LT(BooleanFormulae a, BooleanFormulae b){
        this.a=a;
        this.b=b;
    }  
    
    @Override
    public String toString() {
        return "([<] " + a + " " + b + ")";
    }
}
