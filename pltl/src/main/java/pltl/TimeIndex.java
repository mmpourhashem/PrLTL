package pltl;

import java.util.ArrayList;

import pltl.bool.BooleanFormulae;

public class TimeIndex implements BooleanFormulae{
	private int time, index;
	private ArrayList<TimeIndex> parents = new ArrayList<TimeIndex>();

	public TimeIndex(int time, int index) {
		this.time = time;
		this.index = index;
	}

	public int getTime() {
		return time;
	}

	public int getIndex() {
		return index;
	}
	
	public ArrayList<TimeIndex> getParents() {
//		return parents;
		for (TimeIndex ti:PltlFormula.bayesNet) {
			if (ti.equals(this))
				return ti.getParents();
		}
		
		return null;
	}

	public void addParent(TimeIndex p) {
		parents.add(p);
	}
	
	public void addParent(ArrayList<TimeIndex> p){
		parents.addAll(p);
	}
	
	public boolean hasParent(TimeIndex p) {
//		return parents.contains(p);
		for (TimeIndex ti:PltlFormula.bayesNet) {
			if (ti.equals(this))
				return ti.getParents().contains(p);
		}

		return false;
	}
	
	public BooleanFormulae getFormula() {
		return PltlFormula.get(index); 
	}
	
	public String toString() {
		return "(zot-p " + time + " " + index + ")";
	}
	
	public String toSeqString() {
		return time + " " + index;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TimeIndex && ((TimeIndex) o).getTime() == time && ((TimeIndex) o).getIndex() == index)
			return true;
		return false;
	}
	
}
