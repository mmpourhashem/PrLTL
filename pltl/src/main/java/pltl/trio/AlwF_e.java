/*
 * Author: Alfredo Motta
 * Mail: motta@elet.polimi.it
 * Website: http://www.alfredo.motta.name
 */
package pltl.trio;

import pltl.bool.Formula;

public class AlwF_e implements Formula {

    Formula f;

    public AlwF_e(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(alwf_e " + f + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
