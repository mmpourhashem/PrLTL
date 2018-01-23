/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.trio;

import pltl.bool.Formula;

public class GTE implements Formula {

    Formula a;
    Formula b;
    
    public GTE(Formula a, Formula b){
        this.a=a;
        this.b=b;
    }  
    
    @Override
    public String toString() {
        return "([>=] " + a + " " + b + ")";
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
