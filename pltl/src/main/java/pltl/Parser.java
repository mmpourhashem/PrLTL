package pltl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.zot.syntax.ZotStandaloneSetup;
import org.zot.syntax.zot.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;

import org.eclipse.emf.common.util.URI;
import com.google.inject.Injector;

import arith.*;
import pltl.bool.*;
import pltl.trio.*;

public class Parser {
	private Model model;
	private Formula mainFormula;
	public boolean Parse(String script) {
		String fileName = "z3.input.smt2";
		Injector injector = new ZotStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		Resource resource = resourceSet.createResource(URI.createURI("file:/example.zot"));
		InputStream in = new ByteArrayInputStream(script.getBytes());
		try {
			resource.load(in, resourceSet.getLoadOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}
		model = (org.zot.syntax.zot.Model) resource.getContents().get(0);
		if ((model == null) || (model.getFma() == null && model.getTempDep() == null))
			throw new IllegalArgumentException("Empty model.");
		mainFormula = getFormula(model.getFma());
		if (model.getTempDep() != null)
			Probability.processDeps(0, 0, getTempDep(model.getTempDep()));
		System.out.println(mainFormula.toString());
		String probExpsAtT0 = assertProbExps(mainFormula);
		Prob mainProb = Probability.processMainF(mainFormula);

		String trueFalseSemantics = "";
		for (int time = 0; time <= PltlFormula.bound; time++)
			trueFalseSemantics += new PltlFormula.True().toString(time) + " " + new ArithFormula(Op.EQ, new Prob(time, -1), new Constant((float) 1)).toString() + " (not " + new PltlFormula.False().toString(time) + ") " + new ArithFormula(Op.EQ, new Prob(time, -2), new Constant((float) 0)).toString() + " ";
		String semantics = "(assert (and " + trueFalseSemantics + probExpsAtT0;
		if (mainProb != null) {
			PltlFormula.mainF = mainProb.getIndex();
			semantics += Smt2Formula.getzot(0, mainProb.getIndex()) + " " + new ArithFormula(Op.EQ, mainProb,new Constant((float) 1.0)) + "\n";
		}

		semantics += Probability.getProbFormulae();
		String fTable = ";Formula table:\n";
		for (int i = 0; i < PltlFormula.instances.size(); i++)
			fTable += ";" + i+"\t"+PltlFormula.instances.get(i).toString() + "\n";
		fTable += ";Conditional probability table:\n";
		for (int i = 0; i < PltlFormula.cProbsTBP.size(); i++)
			fTable += ";" + i+"\t"+PltlFormula.cProbsTBP.get(i).toString() + "\n";
		String z3Commands = "))\n(check-sat-using qfufnra)\n(get-model)\n";
		String smt2Model = "(set-option :smtlib2-compliant true)\n" + fTable + "\n" + getIntDec() + "\n" + semantics + z3Commands;
		System.out.println(smt2Model);

		try {
			FileWriter smt2 = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(smt2);

			out.write(smt2Model);
			out.close();
			try {
				ProcessBuilder processBuilder = new ProcessBuilder("./runZ3.sh");
				processBuilder.inheritIO();
				Process process = processBuilder.start();
				int exitValue = process.waitFor();
				if (exitValue != 0) {
					new BufferedInputStream(process.getErrorStream());
					throw new RuntimeException("execution of script failed!");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parseOutput();
	}

	public static boolean bfArraySubset(ArrayList<Formula> a1, ArrayList<Formula> a2) {
		for (int i = 0; i < a1.size(); i++)
			for (int j = 0; j < a2.size(); j++) {
				if (a1.get(i).equals(a2.get(j)))
					break;
				if (j == a2.size() - 1)
					return false;
			}
		return true;
	}

	public static boolean bfArrayEqual(ArrayList<Formula> a1, ArrayList<Formula> a2) {
		return (bfArraySubset(a1, a2) && bfArraySubset(a2, a1));
	}

	private String getIntDec() {
		return "(declare-fun zot (Int Int) Bool)\n"
				+ "(declare-fun zot-p (Int Int) Real)\n"
				+ "(declare-fun zot-cp (Int Int Int Int) Real)\n"
				+ "(define-fun range ((probability Real)) Bool\n"
				+ "\t(and (>= probability 0.0) (<= probability 1.0)))\n";
	}

	private class Row{
		int time;
		int fNumber;
		float value;
		public Row(int time, int fNumber, float value) {
			this.time = time;
			this.fNumber = fNumber;
			this.value = value;
		}
	}

	private String assertProbExps(Formula f) {
		String s = "";
		if (f instanceof And) {
			for (Formula fma: ((And) f).getFormulae())
				if (Probability.hasProbExp(fma))
					s += Smt2Formula.getzot(0, PltlFormula.add(fma)) + " ";
		}
		else if (Probability.hasProbExp(f))
			s += Smt2Formula.getzot(0, PltlFormula.add(f)) + " ";

		return s;
	}

	public boolean parseOutput() {
		boolean unsat = false;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./z3.output.txt"), "Cp1252"));         
			String line;
			boolean prob = false;

			ArrayList<Row> rows = new ArrayList<Row>();
			while ((line = br.readLine()) != null) {
				if (line.equals("unsat")) {
					unsat = true;break;
				}
				if (line.equals("  (define-fun zot-p ((x!0 Int) (x!1 Int)) Real")) {
					prob = true;continue;
				}
				else if (! prob)
					continue;
				if (prob && line.indexOf("ite (and") > 0) {
					String [] ss= line.split("!0");
					int time = Integer.parseInt(ss[1].substring(0, ss[1].indexOf(")")).trim());
					ss = line.split("!1");
					String sfNumber = ss[1].substring(0, ss[1].indexOf(")")).trim();
					//					if (sfNumber.indexOf("(") > -1)
					//						sfNumber = sfNumber.substring(1, sfNumber.length());
					//					int fNumber = Integer.parseInt(ss[1].substring(0, ss[1].indexOf(")")).trim());
					int sI = 1;
					if (sfNumber.indexOf("-") != -1) {
						sfNumber = sfNumber.substring(2, sfNumber.length());
						sI = -1;
					}

					int fNumber = Integer.parseInt(sfNumber.trim()) * sI;

					String floatNumber = "";
					if (ss[1].indexOf(")))") > -1)
						floatNumber = ss[1].substring(ss[1].indexOf(")))") + 3, ss[1].length());
					else
						floatNumber = ss[1].substring(ss[1].indexOf("))") + 2, ss[1].length());

					int s = 1;

					if (floatNumber.indexOf("-") != -1) {
						floatNumber = floatNumber.substring(3, floatNumber.length() - 1);
						s = -1;
					}
					if (floatNumber.substring(floatNumber.length() - 1).equals("?"))
						floatNumber = floatNumber.substring(0, floatNumber.length() -1);
					float value = Float.parseFloat(floatNumber) * s;
					rows.add(new Row(time, fNumber, value));
				}

				if (prob && line.indexOf("ite (and") == -1)
					prob = false;
			}
			br.close();
			try{
				PrintWriter writer = new PrintWriter("pltl.output.txt", "UTF-8");
				writer.println(mainFormula.toString());
				if (unsat)
					writer.println("UNSAT");
				else {
					for (int time = 0; time <= PltlFormula.bound; time++) {
						writer.println("--- time " + time + " ---");
						for (Row r: rows)
							if (r.time == time && r.fNumber >= 0)
								writer.println(r.value + " = P(" + getFormulaString(r.fNumber) + ")");
					}
				}
				writer.close();
			} catch (IOException e) {
				// do something
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unsat;
	}
	
	private String getFormulaString(int index) {
		String s = PltlFormula.getFormulaString(index);
		if (s.length() > 0)
			return s;
		return PltlFormula.get(index).toString();
	}

	//<!-- Grammar Parser
	private Formula getTempDep(TempDep tempDep) {// L0 completed
		if (tempDep == null)
			throw new IllegalArgumentException("Invalid temporal dependencies.");

		if (tempDep.getFd() != null) {
			ArrayList<Formula> bfs = new ArrayList<Formula>();
			for (TPFormula f: tempDep.getFps())
				bfs.add(getFormula(f));
			//			PltlFormula.addDep(new Dep(0, getFormula(tempDep.getFd()), bfs));
			return new Dep(0, getFormula(tempDep.getFd()), bfs);
		}
		else if (tempDep.getFa().size() > 0) {
			ArrayList<Formula> deps = new ArrayList<Formula>();
			for (TempDep td: tempDep.getFa())
				deps.add(getTempDep(td));
			return new And(deps);
		}
		else if (tempDep.getOpf() != null) {
			return getOpF(tempDep.getOpf(), tempDep.getF());
		}
		else if (tempDep.getOpfn() != null) {
			return getOpFN(tempDep.getOpfn(), tempDep.getF1(), tempDep.getI());
		}

		throw new IllegalArgumentException("Invalid temporal dependencies.");
	}

	/*
	 * 	'futr' | 'past'
	 * Dependencies does not make sense for ('withinf' | 'withinp' | 'lasts' | 'lasted').
	 */
	private Formula getOpFN(String opfn, TempDep f1, int i) {// LC
		if (opfn.equals("futr"))
			return new Dist(getTempDep(f1), i);

		if (opfn.equals("past"))
			return new Dist(getTempDep(f1), -i);

		throw new IllegalArgumentException("Invalid temporal operator over dependencies.");
	}


	/*
	'next' | 'yesterday' | 'alw' | 'som' | 'alwf' | 'somf' | 'alwp' | 'somp'
	With respect to dependencies only deterministic operators (i.e. next, yesterday, alw, alwf for OpF category) are allowed.
	 */
	private Formula getOpF(String opf, TempDep f) {
		if (opf.equals("next"))
			return new Dist(getTempDep(f), 1);
		if (opf.equals("yesterday"))
			return new Dist(getTempDep(f), -1);
		if (opf.equals("alw"))
			return new Alw(getTempDep(f));
		if (opf.equals("alwf"))
			return new AlwF(getTempDep(f));
		if (opf.equals("alwp"))
			return new AlwP(getTempDep(f));

		throw new IllegalArgumentException("Invalid temporal operator. next, yesterday, alw, alwf, or alwp is expected to be seen here.");
	}

	private Formula getFormula(TPFormula fma) {
		if (fma.getProbF() != null) {
			return getProbF(fma.getProbF());
		}
		else if (fma.getTemp() != null) {
			return getTemp(fma.getTemp());
		}

		throw new IllegalArgumentException("Invalid temporal formula/probability expression format.");
	}

	// <Simplification rationale> In ProbF we avoid TRIO to LTL simplification (only in parsing), in order to be able to show the original formula when user is interested in knowing its probability.
	private Formula getProbF(ProbF probF) {
		if (probF == null)
			throw new IllegalArgumentException("Invalid probability formula.");
		Op pOP = PltlFormula.getProbOp(probF.getProbOp());
		Formula f1 = null, f11 = null, f12 = null, f2 = null, f21 = null, f22 = null;
		float r1 = -1, r2 = -1;
		if (probF.getTempF1() != null)
			f1 = getTemp(probF.getTempF1());
		if (probF.getTempF11() != null)
			f11 = getTemp(probF.getTempF11());
		if (probF.getTempF12() != null)
			f12 = getTemp(probF.getTempF12());
		if (probF.getTempF2() != null)
			f2 = getTemp(probF.getTempF2());
		if (probF.getTempF21() != null)
			f21 = getTemp(probF.getTempF21());
		if (probF.getTempF22() != null)
			f22 = getTemp(probF.getTempF22());
		if (probF.getReal1() != null)
			r1 = getFloat(probF.getReal1());
		if (probF.getReal2() != null)
			r2 = getFloat(probF.getReal2());

		if (f1 == null && f11 == null && f12 == null && f2 == null && f21 == null && f22 == null)
			throw new IllegalArgumentException("Invalid probability formula.");

		ProbExp pa = new ProbExp(pOP, f1, f11, f12, f2, f21, f22, r1, r2);
		return pa;

	}

	private Float getFloat(DataType f) {
		if (f == null)
			throw new IllegalArgumentException("Wrong format for float number. int.int is expected here.");
		return f.getI() + Float.parseFloat("0" + f.getFloat()) ;
	}

	private Formula getTemp(TempF tempF) {
		if (tempF == null)
			throw new IllegalArgumentException("Invalid temporal formula.");

		if (tempF.getAp() != null) {
			Predicate ap = new Predicate(tempF.getAp().getName());
			return ap;
		}

		if (tempF.getFa().size() > 0) {
			pltl.bool.And and = new pltl.bool.And(); 
			for (TPFormula fma: tempF.getFa())
				and.addFormula(getFormula(fma));
			if (tempF.getFa().size() == 1)
				return getFormula(tempF.getFa().get(0));
			else
				return and;
		}

		if (tempF.getFo().size() > 0) {
			pltl.bool.Or or = new pltl.bool.Or(); 
			for (TPFormula fma: tempF.getFo())
				or.addFormula(getFormula(fma));
			if (tempF.getFo().size() == 1)
				return getFormula(tempF.getFo().get(0));
			else
				return or;
		}

		if (tempF.getFnot() != null) {
			Not not = new Not(getFormula(tempF.getFnot()));
			return not;
		}

		if (tempF.getOp2() != null)
			return getOp2(tempF.getOp2(), tempF.getF1(), tempF.getF2());

		if (tempF.getOpf() != null)
			return getOpF(tempF.getOpf(), tempF.getF());

		if (tempF.getOpfn() != null)//	(opfn=OPFN f1=TPFormula i=INT ) 
			return getOpFN(tempF.getOpfn(), tempF.getF1(), tempF.getI());

		throw new IllegalArgumentException("Invalid temporal formula.");
	}

	/*
	 * 'futr' | 'past' | 'withinf' | 'withinp' | 'lasts' | 'lasted'
	 */
	private Formula getOpFN(String opfn, TPFormula f1, int i) {
		if (opfn.equals("futr"))
			return new Futr(getFormula(f1), i);

		if (opfn.equals("past"))
			return new Past(getFormula(f1), i);

		if (opfn.equals("withinf"))
			return new WithinF(getFormula(f1), i);

		if (opfn.equals("withinp"))
			return new WithinP(getFormula(f1), i);

		if (opfn.equals("lasts"))
			return new Lasts(getFormula(f1), i);

		if (opfn.equals("lasted"))
			return new Lasted(getFormula(f1), i);

		throw new IllegalArgumentException("Invalid temporal formula.");
	}

	/*
	 * 	'next' | 'yesterday' | 'alw' | 'som' | 'alwf' | 'somf' | 'alwp' | 'somp'
	 */
	private Formula getOpF(String opf, TPFormula f) {
		if (opf.equals("next"))
			return new Next(getFormula(f));
		if (opf.equals("yesterday"))
			return new Yesterday(getFormula(f));
		if (opf.equals("alw"))
			return new Alw(getFormula(f));
		if (opf.equals("alwf"))
			return new AlwF(getFormula(f));
		if (opf.equals("alwp"))
			return new AlwP(getFormula(f));
		if (opf.equals("som"))
			return new Som(getFormula(f));
		if (opf.equals("somf"))
			return new SomF(getFormula(f));
		if (opf.equals("somp"))
			return new SomP(getFormula(f));

		throw new IllegalArgumentException("Invalid temporal formula.");
	}


	private Formula getOp2(String op2, TPFormula f1, TPFormula f2) {
		if (op2.equals("->"))
			return new Or(new Not(getFormula(f1)), getFormula(f2));
		if (op2.equals("<->"))
			return new And(new Or(new Not(getFormula(f1)), getFormula(f2)), new Or(new Not(getFormula(f2)), getFormula(f1)));
		if (op2.equals("until"))
			return new Until(getFormula(f1), getFormula(f2));
		if (op2.equals("release"))
			return new Release(getFormula(f1), getFormula(f2));
		if (op2.equals("since"))
			return new Since(getFormula(f1), getFormula(f2));
		if (op2.equals("trigger"))
			return new Trigger(getFormula(f1), getFormula(f2));

		throw new IllegalArgumentException("Invalid temporal formula.");
	}
	//-->
}
