/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl;

import java.util.HashSet;

/*
 * I avoided using Prob.java as Bayesian network node, because a Prob may appear in various Bayesian network and may have different parents/children sets.
 */
public class BayesNode {

	private Prob prob;
	public HashSet<BayesNode> parents = new HashSet<BayesNode>();
	public HashSet<BayesNode> children = new HashSet<BayesNode>();
	public HashSet<Prob> parentsProbs = new HashSet<Prob>();
	public HashSet<Prob> childrenProbs = new HashSet<Prob>();

	public BayesNode(Prob prob) {
		this.prob = prob;
	}

	public HashSet<BayesNode> getParents() {
		return parents;
	}

	public HashSet<Prob> getParentsProbs() {
		return parentsProbs;
	}
	
	public HashSet<Prob> getChildrenProbs() {
		return childrenProbs;
	}

	public HashSet<BayesNode> getChildren() {
		return children;
	}

	public void addParent(BayesNode pbn) {
		if (! parents.contains(pbn)) {
			parents.add(pbn);
			parentsProbs.add(pbn.getProb());
			pbn.addChild(this);
		}
	}

	public void addChild(BayesNode cbn) {
		if (! children.contains(cbn)) {
			children.add(cbn);
			childrenProbs.add(cbn.getProb());
			cbn.addParent(this);
		}
	}

	public boolean hasParentProb(Prob parent) {
		return parentsProbs.contains(parent);
	}
	
	public Prob getProb() {
		return prob;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BayesNode) 
			return ((BayesNode) o).getProb().equals(prob);

		return false;
	}
}
