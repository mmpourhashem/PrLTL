package pltl;

import java.util.Comparator;

@SuppressWarnings("hiding")
public class ProbComparator<Prob> implements Comparator<Prob> {

	/**
	 * {@inheritDoc}
	 */
	public int compare(Prob o1, Prob o2) {
		return ((pltl.Prob) o2).getDepth() - ((pltl.Prob) o1).getDepth();
	}

}