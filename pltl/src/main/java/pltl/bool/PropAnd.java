/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.bool;

import java.util.ArrayList;
import pltl.Parser;
import pltl.PltlFormula;

public class PropAnd implements Formula{
	ArrayList<Formula> f = new ArrayList<Formula>();

	public PropAnd(Formula... formulae) {
		ArrayList<Formula> bfs = new ArrayList<Formula>();
		for (Formula bf:formulae)
			bfs.add(bf);
		setFormulae(bfs);
	}

	public PropAnd(ArrayList<Formula> formulae) {
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

//	public PropAnd getFlatAnd() {
//		return new PropAnd(getFlatFormula(this));
//	}
//
//	private ArrayList<Formula> getFlatFormula(PropAnd and) {
//		ArrayList<Formula> flatF = new ArrayList<Formula>();
//
//		for (Formula fma: and.getFormulae())
//			if (fma instanceof PropAnd)
//				flatF.addAll(getFlatFormula((PropAnd) fma));
//			else if (fma instanceof Or)
//				flatF.add(((Prop?Or) fma).getFlatOr());
//			else
//				flatF.add(fma);
//
//		return flatF;
//	}

	public Formula get(int offset) {
		return null;
	}
	
	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
		
		PropAnd pAnd = new PropAnd();
		for (Formula fma: f)
			pAnd.addFormula(fma.get(offset));
		
		return pAnd;
	}

	@Override
	public String toString() {
		String s = "(and";
		for (int i = 0; i < f.size(); i++)
			s = s + " " + f.get(i).toString();

		return s + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PropAnd) {
			ArrayList<Formula> a1 = this.getFormulae();
			ArrayList<Formula> a2 = ((PropAnd) o).getFormulae();
			return Parser.bfArrayEqual(a1, a2);
		}

		return false;
	}

}
