/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.bool;

import java.util.ArrayList;
import pltl.Parser;
import pltl.PltlFormula;

public class PropOr implements Formula{
	ArrayList<Formula> f = new ArrayList<Formula>();

	public PropOr(Formula... formulae) {
		ArrayList<Formula> bfs = new ArrayList<Formula>();
		for (Formula bf:formulae)
			bfs.add(bf);
		setFormulae(bfs);
	}

	public PropOr(ArrayList<Formula> formulae) {
		setFormulae(formulae);
	}

	public void setFormulae(ArrayList<Formula> formulae) {
		f = new ArrayList<Formula>();
		for (Formula bf:formulae)
			addFormula(bf);
	}

	public void addFormula(Formula fIn) {
		for (Formula fma: f)
			if (fma.equals(fIn))
				return;
		if (fIn != null)
			f.add(fIn);
	}

	public void removeFormulae(int index) {
		f.remove(index);
	}

	public void setFormulae(int index, Formula newf) {
		f.set(index, newf);
	}

	public void addAllFormulae(ArrayList<Formula> bfs) {
		for (Formula bf:bfs)
			if (bf != null)
				addFormula(bf);
	}

	public ArrayList<Formula> getFormulae() {
		return f;
	}

	public int size() {
		return f.size();
	}

	public Formula get(int offset) {
		return null; // Not required.
	}
	
	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
		
		PropOr pOr = new PropOr();
		for (Formula fma: f)
			pOr.addFormula(fma.get(offset));
		
		return pOr;
	}

	@Override
	public String toString() {
		String s = "(or";
		for (int i = 0; i < f.size(); i++)
			s = s + " " + f.get(i).toString();

		return s + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PropOr) {
			ArrayList<Formula> a1 = this.getFormulae();
			ArrayList<Formula> a2 = ((PropOr) o).getFormulae();
			return Parser.bfArrayEqual(a1, a2);
		}

		return false;
	}

}
