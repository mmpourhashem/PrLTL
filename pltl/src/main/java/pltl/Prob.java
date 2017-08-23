package pltl;

import java.util.ArrayList;

import com.sun.scenario.effect.Offset;

import arith.ArithFormula;
import arith.Op;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.trio.Dist;
import pltl.trio.Predicate;

public class Prob implements Formula {
	private int time, index, depth = -1;
	private ArrayList<Prob> parents = new ArrayList<Prob>();

	public Prob(int time, int index) {
		this.time = time;
		this.index = index;
	}

	public int getTime() {
		return time;
	}

	public int getIndex() {
		return index;
	}

	public ArrayList<Prob> getParents() {
		ArrayList<Prob> parents = new ArrayList<Prob>();
		for (Prob p:PltlFormula.bayesNet) {
			if (p.equals(getPredicateProb()))
				parents = p.getThisParents();
		}

		return parents;
	}

	public ArrayList<Prob> getThisParents() {
		return parents;
	}

	public Formula getFormula(int time) {
		if (this.time == time)
			return PltlFormula.get(index);
//		if (this.time > time)
//			return new Futr(PltlFormula.get(index), this.time - time);
//		return new Past(PltlFormula.get(index), time - this.time);
		return new Dist(PltlFormula.get(index), this.time - time);
	}

	public void addParent(Prob p) {
		if (!parents.contains(p))
			parents.add(p);
	}

	public void addParent(ArrayList<Prob> p){
		parents.addAll(p);
	}

	public boolean hasParent(Prob prob) {
		for (Prob p:PltlFormula.bayesNet) {
			if (p.equals(getPredicateProb())) // If (dep (-p- a) (-p- b)), this line considers also (!! (-p- a)) as a child of (-p- b).
				return p.getParents().contains(prob.getPredicateProb()); // If (dep (-p- a) (-p- b)), this line considers also (!! (-p- b)) as a parent of (-p- a).
		}

		return false;
	}

	public Formula getPlainFormula() {
		return PltlFormula.get(index); 
	}

	public String getSemantics() {
		String s = "";
		for (int time = 0; time <= PltlFormula.bound; time++)
			s += getSemantics(time) + "\n";

		return s;
	}

	public String getSemantics(int time) {
		ArrayList<Formula> parentsF = new ArrayList<Formula>();

		if (this.getParents().size() == 0)
			return "";

		for (Prob p: this.getParents()) {
			parentsF.add(p.getFormula(time));
		}

		ArrayList<Formula> sumProb = new ArrayList<Formula>();
		for (Formula f: Probability.populateFormulae(parentsF)) {
			int index = PltlFormula.add(new And(getFormula(time), f).getFlatAnd());
			sumProb.add(new Prob(time, index));
		}
		return new ArithFormula(Op.EQ, this, new ArithFormula(Op.PLUS, sumProb)).toString() + "\n";
	}

	/**
	 * @param parentsSubset
	 * A subset of parents.
	 * @return
	 * Breaks down the conjunction of this and the subset to an analyzable formulae.
	 * For example, if (-p- a) depends on (-p- b) and (-p- c) and parentsSubset = {(-p- b)} it returns
	 * {(&& (-p- a) (-p- b) (-p- c)), (&& (-p- a) (-p- b) (!! (-p- c)))}
	 * It essentially there to infer the following:
	 * P((&& (-p- a) (-p- b))) = P((&& (-p- a) (-p- b) (-p- c))) + P((&& (-p- a) (-p- b) (!! (-p- c))))
	 */
	public ArrayList<Prob> populateMissingParents(ArrayList<Prob> parentsSubset) {
//		ArrayList<Prob> parentsSubset = new ArrayList<Prob>();
//		for (Prob p: parentsSubset1) {
//			Prob
//			if (p.getPlainFormula() instanceof Not)
//				
//		}
		ArrayList<Prob> missingParents = new ArrayList<Prob>();
		for (Prob p: getParents()) {
			if (parentsSubset.contains(p) || parentsSubset.contains(new Prob(p.getTime(), PltlFormula.add(new Not(p.getPlainFormula())))))
				continue;
			missingParents.add(p);
		}
		
		ArrayList<Prob> result = new ArrayList<Prob>();
		for (Prob p: Probability.populateProbs(missingParents)) {
			And and = new And();
			and.addFormula(this);
			and.addAllProbs(parentsSubset);
			and.addProb(p);
			result.add(and.getProb());
		}
		
		return result;
	}
	
	/**
	 * @return
	 * Returns the distance between the prob and the farthest root.
	 */
	public int getDepth() {
		if (depth != -1)
			return depth;

		Prob predicateProb = this.getPredicateProb();
		if (PltlFormula.bayesNet.size() > 0)
			for (Prob p: PltlFormula.bayesNet)
				if (p.equals(predicateProb)) {
					int max = 0;
					for (Prob parent:p.getParents())
						if (parent.getDepth() > max)
							max = parent.getDepth();

					depth = max + 1;
					return depth;
				}

		// If this does not have any parent.
		depth = 0;
		return depth;
	}

	/**
	 * 
	 * @return The actual predicate.
	 * For example, the actual predicate of (!! (-p- a)) is considered as (-p- a)
	 */
	public Prob getPredicateProb() {
		if (getFormula(time) instanceof Predicate)
			return this;
		if (getFormula(time) instanceof Not && ((Not) getFormula(time)).getFormula() instanceof Predicate)
			return new Prob(time, PltlFormula.add(((Not) getFormula(time)).getFormula()));
		if (getFormula(time) instanceof Dist)
			return new Prob(time + ((Dist) getFormula(time)).getOffset(), PltlFormula.add(((Dist) getFormula(time)).getFormula()));
		if (getFormula(time) instanceof Not && ((Not) getFormula(time)).getFormula() instanceof Dist)
			return new Prob(time + ((Dist) ((Not) getFormula(time)).getFormula()).getOffset(), PltlFormula.add(((Dist) ((Not) getFormula(time)).getFormula()).getFormula()));
			
		return null;//The dependent formula can be either a predicate, a (dist predicate), or its neg.
	}

	public boolean allAreParents(Prob p) {
		if (p.getPlainFormula() instanceof And) {
			for (Formula f: ((And) p.getPlainFormula()).getFormulae())
				if (! hasParent(new Prob(p.getTime(), PltlFormula.add(f))))
					return false;
		}
		else
			return hasParent(p);

		return true;
	}
	
	public boolean allAreParents(ArrayList<Prob> probs) {
		for (Prob p: probs)
			if (! allAreParents(p))
				return false;
			
		return true;
	}
	
	public ArrayList<Formula> getToSumWithParentSubset(ArrayList<Prob> parentsSubset) {
		ArrayList<Prob> pss = populateMissingParents(parentsSubset);
		ArrayList<Formula> result = new ArrayList<Formula>();
		for (Prob p: pss)
			result.add(new Prob(time, PltlFormula.add(new And(this.getFormula(time), p.getFormula(time)).getFlatAnd())));
		return result;
	}
	
	public String toSeqString() {
		return time + " " + index;
	}

	@Override
	public String toString() {
		return "(zot-p " + time + " " + index + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Prob && ((Prob) o).getTime() == time && ((Prob) o).getIndex() == index)
			return true;
		return false;
	}
}
