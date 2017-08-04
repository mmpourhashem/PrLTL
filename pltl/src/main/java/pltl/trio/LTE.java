package pltl.trio;

import pltl.bool.Formula;

public class LTE implements Formula {

    Formula a;
    Formula b;
    
    public LTE(Formula a, Formula b){
        this.a=a;
        this.b=b;
    }  
    
    @Override
    public String toString() {
        return "([<=] " + a + " " + b + ")";
    }
}
