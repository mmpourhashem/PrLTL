/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl;

import java.util.HashSet;

public class BayesNet {

	public HashSet<BayesNode> nodes = new HashSet<BayesNode>();

	public HashSet<BayesNode> getNodes() {
		return nodes;
	}

	public void setNodes(HashSet<BayesNode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @param prob
	 * @return added/existing @BayesNode that corresponds to the given @Prob
	 */
	public BayesNode addNode(Prob prob) {
		for (BayesNode bn: nodes)
			if (bn.getProb().equals(prob))
				return bn;
		BayesNode bn = new BayesNode(prob);
		this.nodes.add(bn);
		
		return bn;
	}

	public HashSet<BayesNode> getAncestors(BayesNode bn) {
		HashSet<BayesNode> ancestors = new HashSet<BayesNode>();
		for (BayesNode parent: bn.getParents()) {
			ancestors.add(parent);
			ancestors.addAll(getAncestors(parent));
		}
		
		return ancestors;
	}
}
