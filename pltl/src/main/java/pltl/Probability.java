package pltl;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Alw;
import pltl.trio.Futr;
import pltl.trio.Next;
import pltl.trio.Predicate;

public class Probability {

	public static String getProbFormulae() {
		String s = "";
		int mainFSize = 0;
		int mainCSize = 0;
		int nextFToProcess = 0;
		int nextCToProcess = 0;
		while (mainFSize < PltlFormula.instances.size() || mainCSize < PltlFormula.cProbsTBP.size()) {
			mainFSize = PltlFormula.instances.size();
			mainCSize = PltlFormula.cProbsTBP.size();
			s += getMainSemantics(nextFToProcess, PltlFormula.instances.size() - 1, nextCToProcess, PltlFormula.cProbsTBP.size() - 1);
			nextFToProcess = mainFSize;
			nextCToProcess = mainCSize;
		}

		return s;
	}

	public static String getMainSemantics(int fStartI, int fEndI, int cStartI, int cEndI) {
		String s = "";
		for (int formulaIndex = fStartI; formulaIndex <= fEndI; formulaIndex++) {
			Formula f = PltlFormula.instances.get(formulaIndex);

			if (f instanceof And) {
				boolean hasProbExp = false;
				for (Formula fma: ((And) f).getFormulae())
					if (fma instanceof ProbExp)
						hasProbExp = true;
				// PorbExps (like (P((-p- a)) = 0.3) must be at the first level. 
				assertFalse(hasProbExp);
				s += ((And) f).getSemantics();
			}
			else if (f instanceof Or) {
				boolean hasProbExp = false;
				for (Formula fma: ((Or) f).getFormulae())
					if (fma instanceof ProbExp)
						hasProbExp = true;
				// PorbExps (like (P((-p- a)) = 0.3) must be at the first level. 
				assertFalse(hasProbExp);
				s += ((Or) f).getSemantics();
			}
			else if (f instanceof ProbExp)
				s += ((ProbExp) f).getSemantics();
			else if (f instanceof Not)
				s += ((Not) f).getSemantics();
			else if (f instanceof Next)
				s += ((Next) f).getSemantics();
			else if (f instanceof Predicate)
				s += ((Predicate) f).getSemantics();
			else if (f instanceof PltlFormula.True)
				s += ((PltlFormula.True) f).getSemantics();
			else if (f instanceof PltlFormula.False)
				s += ((PltlFormula.False) f).getSemantics();
			else
				s += f.toString() + " <Not Recognized!>";

			if (! (f instanceof ProbExp))
				s += Smt2Formula.getRangeConstraints(formulaIndex) + "\n";
		}
		//		s += "\n";
		for (int formulaIndex = cStartI; formulaIndex <= cEndI; formulaIndex++) {
			CProb c = PltlFormula.cProbsTBP.get(formulaIndex);
			s += c.getSemantics() + "\n";
		}

		return s;
	}

	//	public static String getMainSemantics(int index) {
	//		return getMainSemantics(index, index);
	//	}

	//	public static String getTheNegInferredSemantics() {
	//		String s = "";
	//		//If fma is a formula then simplified (!! fma) is added to the formula instances.
	//		int n = PltlFormula.instances.size();
	//		for (int i=0; i < n; i++) {
	//			Formula f = PltlFormula.instances.get(i);
	//			s += new Not(f).getTheNegSemantics();
	//		}
	//
	//		return s;
	//	}

	public static void processDeps(int left, int right, Formula f) {
		if (f instanceof And)
			for (Formula bf: ((And) f).getFormulae())
				processDeps(left, right, bf);
		else if (f instanceof Alw)
			processDeps(0, PltlFormula.bound, ((Alw) f).getFormula());
		else if (f instanceof Next)
			processDeps(left + 1, right + 1, ((Next) f).getFormula());
		else if (f instanceof Futr)
			processDeps(left + ((Futr) f).getInt(), right + ((Futr) f).getInt(), ((Futr) f).getFormula());
		else if (f instanceof Dep) {
			Dep d = (Dep) f;
			for (int time = left; time <= right; time++) {
				int depFI = PltlFormula.add(d.getDepF());
				Prob depTI = PltlFormula.getProb(time, depFI);
				for (int i = 0; i < d.getParentsF().size(); i++) {
					int parentFI = PltlFormula.add(d.getParentsF().get(i));
					depTI.addParent(PltlFormula.getProb(time, parentFI));
				}
				PltlFormula.addBayes(depTI);
			}
		}

		//TODO yesterday, past, lasts, lasted, alwf, futr, past, ...		

	}

	/**
	 * @param formulae
	 * An atomic formula followed by a subset of its parents.
	 * @return
	 * Breaks down to analyzable formulae.
	 * For example, if (-p- a) depends on (-p- b) and (-p- c) and formulae = {(-p- a) (-p- b)} it returns
	 * (&& (-p- a) (-p- b) (-p- c)) (&& (-p- a) (-p- b) (!! (-p- c)))
	 * It essentially there to infer the following:
	 * P((&& (-p- a) (-p- b))) = P((&& (-p- a) (-p- b) (-p- c))) + P((&& (-p- a) (-p- b) (!! (-p- c))))
	 */
	public static ArrayList<Formula> populateFormulae(ArrayList<Formula> formulae) {
		Formula f = formulae.get(0);
		Formula notF = null;

		if (formulae.get(0) instanceof Not)
			notF = ((Not)formulae.get(0)).getFormula();
		else
			notF = new Not(f);

		if (formulae.size() == 1) {
			formulae.add(notF);

			return formulae;
		}
		else {
			formulae.remove(0);
			ArrayList<Formula> populatedCdr = populateFormulae(formulae);
			ArrayList<Formula> populatedMain = new ArrayList<Formula>();
			for (int i = 0; i < populatedCdr.size(); i++) {
				populatedMain.add(new And(f, populatedCdr.get(i)).getFlatAnd());
				populatedMain.add(new And(notF, populatedCdr.get(i)).getFlatAnd());
			}

			return populatedMain;
		}
	}

	public static ArrayList<Prob> populateProbs(ArrayList<Prob> probs) {
		ArrayList<Prob> result = new ArrayList<Prob>();
		int baseTime = probs.get(0).getTime();
		ArrayList<Formula> formulae = new ArrayList<Formula>();
		for (Prob p: probs)
			formulae.add(p.getFormula(p.getTime()));
		formulae = populateFormulae(formulae);
		for (Formula f: formulae)
			result.add(new Prob(baseTime, PltlFormula.add(f)));
		//TODO test with Ya -> a, Xa -> a

		return result;
	}

	public static Prob processMainF(Formula f) {
		if (f instanceof And) {
			And newAnd = new And();
			for (Formula fma: ((And) f).getFormulae())
				if (! (fma instanceof ProbExp))
					newAnd.addFormula(fma);
				else
					PltlFormula.add(fma);
			if (newAnd.size() > 0) {
				Formula dnf = PltlFormula.getDNF(newAnd);
				return new Prob(0, PltlFormula.add(dnf));
				}
			else
				return null;
		}
		else if (f instanceof Or) {
			Or newOr = new Or();
			for (Formula bf:((Or) f).getFormulae()) {
				assertFalse(bf instanceof ProbExp);
				newOr.addFormula(PltlFormula.getDNF(bf));
			}

			Formula dnf = PltlFormula.getDNF(newOr);
			return new Prob(0, PltlFormula.add(dnf));
		}
		else
			return processMainF(new And(f));
	}
}
