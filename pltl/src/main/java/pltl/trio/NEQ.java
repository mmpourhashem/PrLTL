package pltl.trio;

import pltl.bool.Formula;

public class NEQ implements Formula{

    Formula a;
    Formula b;
    
    public NEQ(Formula a, Formula b){
        this.a=a;
        this.b=b;
    }
    
    public Formula getLeftSide(){
        return a;
    }
    
    public Formula getRightSide(){
        return b;
    }
    
    @Override
    public String toString() {
        return "([!=] " + a + " " + b + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public Formula getProp(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}