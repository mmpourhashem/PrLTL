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
import org.eclipse.emf.common.util.URI;
import com.google.inject.Injector;

import pltl.PltlFormula.probOp;
import pltl.bool.And;
import pltl.bool.BooleanFormulae;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Next;
import pltl.trio.Predicate;

public class Parser {
	private Model model;
	private BooleanFormulae mainFormula;
	public boolean Parse(String script){
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
		if (model==null)
			System.out.println(model + " not recognized");
		mainFormula = getFormula(model.getFma());
		System.out.println(mainFormula.toString());
		//		mainFormula = getNormalizedFormula(mainFormula);
		//		mainFormula = removeDuplicateFormulae(mainFormula);
		//		System.out.println(mainFormula.toString());

		int mainF = PltlFormula.add(mainFormula);
		PltlFormula.mainF = mainF;
		String semantics = Probability.getProbFormulae();
		semantics += getRangeConstraints();////////////////////////////
		if (mainF > -1)
			semantics = "(assert (and (zot 0 " + mainF + ") (= (zot-p 0 " + mainF + ") 1.0) \n" + semantics + "))\n\n";
		//		if (mainF > -1)//bv zot-px
		//			semantics = "(assert (and (zot 0 " + mainF + ") "+ Smt2Formula.getSmt2Eq("1.0", PltlFormula.getZotpx(0, mainFormula)) + "\n" + semantics + "))\n\n";
		String fTable = "";
		for (int i=0;i<PltlFormula.instances.size();i++)
			fTable += ";" + i+"\t"+PltlFormula.instances.get(i).toString() + "\n";
		String z3Commands = "(check-sat-using qfufnra)\n(get-model)\n";
				String smt2Model = "(set-option :smtlib2-compliant true)\n" + fTable + "\n" + getIntDec() + "\n" + semantics + z3Commands;
//				String smt2Model = fTable + "\n" + getBVDec(PltlFormula.instances.size()) + "\n"+ semantics + z3Commands;
//		String smt2Model = fTable + "\n" + getBVDec(3) + "\n"+ semantics + z3Commands;
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


	private String getIntDec() {
		return "(declare-fun zot (Int Int) Bool)\n"
				+ "(declare-fun zot-p (Int Int) Real)\n"
				+ "(declare-fun zot-cp (Int Int Int) Real)\n";
	}

	//	private String getBVDec0(int size) {
	//		String bv = "(_ BitVec " + size + ")";
	//		return "(declare-fun zot (Int " + bv + ") Bool)\n"
	//		+ "(declare-fun zot-p (Int " + bv + " " + bv + ") Real)\n"
	//		+ "(declare-fun zot-cp (Int " + bv + " " + bv + " " + bv + " " + bv + ") Real)\n";
	//	}

	private String getBVDec(int size) {
		String bv = "(_ BitVec " + size + ")";
		String s = "(declare-fun zot (Int Int) Bool)\n"
				+ "(declare-fun zot-p (Int " + bv + ") Real)\n"
				//		+ "(declare-fun zot-px (Int " + bv + " " + bv + ") Real)\n"
				+ "(declare-fun zot-cp (Int " + bv + " " + bv + " " + bv + " " + bv + ") Real)\n";

		for (int i = 0; i < PltlFormula.depCluster.size(); i++){
			s += ";Cluster" + i + ":\n;";
			for (int f = 0; f < PltlFormula.depCluster.get(i).size(); f++)
				s += PltlFormula.depCluster.get(i).get(f).toString() + ", ";
			s += "\n(define-fun zot-px" + i + " ((time Int) (v (_ BitVec " + PltlFormula.depCluster.get(i).size() + ")) (x (_ BitVec " + PltlFormula.depCluster.get(i).size() + "))) Real (+\n";
			for (int j = 0; j < Math.pow(2, PltlFormula.depCluster.get(i).size()); j++){
				s += "(ite (= (bvor (bvand v (_ bv" + j + " " + PltlFormula.depCluster.get(i).size() + ")) x) (bvnot (_ bv0 " + PltlFormula.depCluster.get(i).size() + "))) (zot-p time (_ bv" + j + " " + PltlFormula.depCluster.get(i).size() + ")) 0.0)\n";
			}
			s += "))\n";
		}

		return s;
	}

	private String getRangeConstraints() {
		String s = ";<Range constraints>\n";
		for (int time = 0; time <= PltlFormula.bound; time++)
			for (int i = 0; i < PltlFormula.instances.size(); i++){
				s += "(and (>= (zot-p " + time + " " + i + ") 0.0) (<= (zot-p " + time + " " + i + ") 1.0))\n";
				for (int j = 0; j < PltlFormula.instances.size(); j++){
					if (i == j)
						s += "(= (zot-cp " + time + " " + i + " " + j + ") 1.0)\n"; //For all fma: G(CP(fma, fma) = 1.0)
					else {
						s += "(and (>= (zot-cp " + time + " " + i + " " + j + ") 0.0) (<= (zot-cp " + time + " " + i + " " + j + ") 1.0))\n";
						s += "(=> (= (zot-p " + time + " " + j + ") 1.0) (= (zot-cp " + time + " " + i + " " + j + ") (zot-p " + time + " " + i + ")))" + "\n";
						s += "(=> (= (zot-p " + time + " " + i + ") 1.0) (= (zot-cp " + time + " " + j + " " + i + ") (zot-p " + time + " " + j + ")))" + "\n";
					}
				}
			}

		return s + ";</Range constraints>\n";
	}

	public boolean parseOutput(){
		boolean unsat = false;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./z3.output.txt"), "Cp1252"));         
			String line;
			boolean prob = false;
			
			ArrayList<Row> rows = new ArrayList<Row>();
			while ((line = br.readLine()) != null) {
				if (line.equals("unsat")){
					unsat = true;break;
				}
				if (line.equals("  (define-fun zot-p ((x!0 Int) (x!1 Int)) Real")){
					prob = true;continue;
				}
				else if (! prob)
					continue;
				if (prob && line.indexOf("ite (and") > 0){
					String [] ss= line.split("!0");
					int time = Integer.parseInt(ss[1].substring(0, ss[1].indexOf(")")).trim());
					ss = line.split("!1");
					int fNumber = Integer.parseInt(ss[1].substring(0, ss[1].indexOf(")")).trim());
					String floatNumber = ss[1].substring(ss[1].indexOf("))") + 2, ss[1].length());
					int s = 1;
					if (floatNumber.indexOf("-") != -1){
						floatNumber = floatNumber.substring(3, floatNumber.length()-1);
						s = -1;
					}
					if (floatNumber.substring(floatNumber.length()-1).equals("?"))
						floatNumber = floatNumber.substring(0, floatNumber.length() -1);
					float value = Float.parseFloat(floatNumber) * s;
					rows.add(new Row(time, fNumber, value));
				}
			}
			br.close();
			try{
				PrintWriter writer = new PrintWriter("pltl.output.txt", "UTF-8");
				writer.println(mainFormula.toString());
				if (unsat)
					writer.println("UNSAT");
				else {
					for (int time = 0; time <= PltlFormula.bound; time++){
						writer.println("--- time " + time + " ---");
						for (Row r: rows)
							if (r.time == time)
								writer.println(r.value + " = P(" + PltlFormula.get(r.fNumber).toString() + ")");
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

	private class Row{
		int time;
		int fNumber;
		float value;
		public Row(int time, int fNumber, float value){
			this.time = time;
			this.fNumber = fNumber;
			this.value = value;
		}
	}

	private BooleanFormulae getFormula(Formula fma) {
		if (fma.getProbF() != null){
			return getProbF(fma.getProbF());
		}
		else if (fma.getTemp() != null){
			return getTemp(fma.getTemp());
		}
		return null;
	}

	private BooleanFormulae getProbF(ProbF probF) {
		probOp pOP = PltlFormula.getProbOp(probF.getProbOp());
		BooleanFormulae f1 = null, f11 = null, f12 = null, f2 = null, f21 = null, f22 = null;
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
		ProbAtom pa = new ProbAtom(pOP, f1, f11, f12, f2, f21, f22, r1, r2);
		PltlFormula.add(pa);
		return pa;
	}

	private Float getFloat(DataType f) {
		return f.getI() + Float.parseFloat("0" + f.getFloat()) ;
	}

	private BooleanFormulae getTemp(TempF tempF) {
		if (tempF.getAp() != null){
			Predicate ap = new Predicate(tempF.getAp().getName());
			PltlFormula.add(ap);
			return ap;
		}

		if (tempF.getFa().size() > 0){
			pltl.bool.And and = new pltl.bool.And(); 
			for (Formula fma: tempF.getFa())
				and.addFormulae(getFormula(fma));
			if (tempF.getFa().size() == 1)
				return getFormula(tempF.getFa().get(0));
			else {
				PltlFormula.add(and);
				return and;
			}
		}

		if (tempF.getFo().size() > 0){
			pltl.bool.Or or = new pltl.bool.Or(); 
			for (Formula fma: tempF.getFo())
				or.addFormulae(getFormula(fma));
			if (tempF.getFo().size() == 1)
				return getFormula(tempF.getFo().get(0));
			else {
				PltlFormula.add(or);
				return or;
			}
		}

		if (tempF.getFnot() != null){
			Not not = new Not(getFormula(tempF.getFnot()));
			PltlFormula.add(not);
			return not;
		}

		if (tempF.getOp2() != null){
			return getOp2(tempF.getOp2(), tempF.getF1(), tempF.getF2());
		}//TODO

		if (tempF.getOpf() != null){
			return getOpF(tempF.getOpf(), tempF.getF());
		}
		//TODO check completeness.
		return null;
	}

	private BooleanFormulae getOpF(String opf, Formula f) {
		if (opf.equals("next"))
			return new Next(getFormula(f));
		//TODO check completeness.		
		return null;
	}


	private BooleanFormulae getOp2(String op2, Formula f1, Formula f2) {
		if (op2.equals("->"))
			return new Or(new Not(getFormula(f1)), getFormula(f2));
		if (op2.equals("<->"))
			return new And(new Or(new Not(getFormula(f1)), getFormula(f2)), new Or(new Not(getFormula(f2)), getFormula(f1)));
		//TODO
		return null;
	}

	public static boolean bfArraySubset(ArrayList<BooleanFormulae> a1, ArrayList<BooleanFormulae> a2){
		for (int i = 0; i < a1.size(); i++)
			for (int j = 0; j < a2.size(); j++){
				if (a1.get(i).equals(a2.get(j)))
					break;
				if (j == a2.size() - 1)
					return false;
			}
		return true;
	}

	public static boolean bfArrayEqual(ArrayList<BooleanFormulae> a1, ArrayList<BooleanFormulae> a2){
		return (bfArraySubset(a1, a2) && bfArraySubset(a2, a1));
	}
	/*
	private BooleanFormulae getNormalizedFormula(BooleanFormulae f) {
		if (f instanceof Not)
			return getNormalizedNot((Not) f);
		if (f instanceof And){
			//			ArrayList<BooleanFormulae> bfs = ((And) f).getFormulae();
			//			And newAnd = new And();
			//			for (BooleanFormulae f1: bfs)
			//				newAnd.addFormulae(getNormalizedFormula(f1));
			//			return newAnd;
			return getNormalizedAnd((And) f);
		}
		if (f instanceof Or)
			//			return removeDuplicateFormulae(f);
			return getNormalizedOr((Or) f);
		//		if (f instanceof Predicate || f instanceof ProbAtom)
		//			return f;
		return f;
	}

	private BooleanFormulae getNormalizedAnd(And and) {
		if (isNormalizedAnd(and))
			return removeDuplicateFormulae(and);
		And newAnd = new And();
		And uAnd = new And(); // and with unique elements.
//		BooleanFormulae ftemp = removeAndDuplicateFormulae(and);
//		if (ftemp instanceof And)
		if (!(removeAndDuplicateFormulae(and) instanceof And)) //if atomic
			uAnd.addFormulae(removeAndDuplicateFormulae(and));
		else
			uAnd = (And) removeAndDuplicateFormulae(and);
		newAnd.addFormulae(getNormalizedFormula(uAnd.getFormulae().get(0)));
		for (int i = 1; i < uAnd.getFormulae().size(); i++){
			newAnd.setFormulae(0, getNormalizedFormula(getAndDistribution(getNormalizedFormula(newAnd.getFormulae().get(0)), getNormalizedFormula(uAnd.getFormulae().get(i)))));
		}

		return removeDuplicateFormulae(newAnd.getFormulae().get(0));
	}

	private BooleanFormulae getNormalizedOr(Or f) {
		if (f.getFormulae().size() == 1)
			return getNormalizedFormula(f.getFormulae().get(0));

		Or newOr = new Or();
		for (BooleanFormulae bf: f.getFormulae())
			if (bf instanceof Or){
				for (BooleanFormulae innerOrF: ((Or) bf).getFormulae())
					newOr.addFormulae(getNormalizedFormula(innerOrF));
			}
			else
				newOr.addFormulae(getNormalizedFormula(bf));
		return newOr;

//		for (int i = 0; i < f.getFormulae().size(); i++)
//			f.setFormulae(i, getNormalizedFormula(f.getFormulae().get(i)));
	}

	private boolean isNormalizedAnd(And and) {
		for (BooleanFormulae f: and.getFormulae())
			if (f instanceof Or)
				return false;
		return true;
	}

	private BooleanFormulae removeDuplicateFormulae(BooleanFormulae f) {
		if (f instanceof And){
			return removeAndDuplicateFormulae((And) f);
			//			BooleanFormulae bf = removeAndDuplicateFormulae((And) f);
			//			if (bf instanceof And){
			//				And and = (And) removeAndDuplicateFormulae((And) f);	
			//				if (((And) f).getFormulae().size() == 1)
			//					return removeDuplicateFormulae(((And) f).getFormulae().get(0));
			//				else
			//					return and;
			//			}
		}
		else if (f instanceof Or){
			return removeOrDuplicateFormulae((Or) f);
			//			BooleanFormulae bf = removeOrDuplicateFormulae((Or) f);
			//			if (bf instanceof Or){
			//				Or or = (Or) removeOrDuplicateFormulae((Or) bf);
			//				if (or.getFormulae().size() == 1)
			//					return removeDuplicateFormulae(or.getFormulae().get(0));
			//				else
			//					return or;
			//			}
		}

		return f;
	}

	private BooleanFormulae removeAndDuplicateFormulae(And f) {
		And uAnd = new And();
		uAnd.addAllFormulae(f.getFormulae());
		for (int iterate = 0; iterate < 2; iterate++) {//It does it 2 times, because inner simplified formulae can be redundant at the higher level. 
			for (int i = uAnd.getFormulae().size() - 1; i >= 0; i--)
				for (int j = i - 1; j >= 0; j--) {
					BooleanFormulae bfi = uAnd.getFormulae().get(i);
					BooleanFormulae bfj = uAnd.getFormulae().get(j);
					if (bfi.equals(bfj)){
						uAnd.removeFormulae(i);
						break;
					}
				}

			for (int i = 0; i < uAnd.getFormulae().size(); i++)
				if ((uAnd.getFormulae().get(i) instanceof And) || (uAnd.getFormulae().get(i) instanceof Or))
					uAnd.setFormulae(i, removeDuplicateFormulae(uAnd.getFormulae().get(i)));

			if (uAnd.getFormulae().size() == 1){
				//			return uAnd.getFormulae().get(0);
				if (uAnd.getFormulae().get(0) instanceof And || uAnd.getFormulae().get(0) instanceof Or)
					return removeDuplicateFormulae(uAnd.getFormulae().get(0));
				else
					return uAnd.getFormulae().get(0);
			}
		}

		return uAnd;
	}

	private BooleanFormulae removeOrDuplicateFormulae(Or f) {
		Or uOr= new Or();
		uOr.addAllFormulae(((Or) f).getFormulae());
		for (int iterate = 0; iterate < 2; iterate++) {//It does it 2 times, because inner simplified formulae can be redundant at the higher level.
			for (int i = uOr.getFormulae().size() - 1; i >= 0; i--)
				for (int j = i - 1; j >= 0; j--) {
					BooleanFormulae bfi = uOr.getFormulae().get(i);
					BooleanFormulae bfj = uOr.getFormulae().get(j);
					if (bfi.equals(bfj)){
						uOr.removeFormulae(i);
						break;
					}
				}

			for (int i = 0; i < uOr.getFormulae().size(); i++)
				if ((uOr.getFormulae().get(i) instanceof And) || (uOr.getFormulae().get(i) instanceof Or))
					uOr.setFormulae(i, removeDuplicateFormulae(uOr.getFormulae().get(i)));

			if (uOr.getFormulae().size() == 1) 
				return uOr.getFormulae().get(0);

		}
		return uOr;
	}

	private BooleanFormulae getAndDistribution(BooleanFormulae f1, BooleanFormulae f2){

		//(&& (&& (-p- a) (-p- b)) (&& (-p- c) (-p- d)))
		//returns (&& (-p- a) (-p- b) (-p- c) (-p- d))
		if (f1 instanceof And){
			if (f2 instanceof And){
				And newAnd = new And();
				newAnd.addAllFormulae(((And) f1).getFormulae());
				newAnd.addAllFormulae(((And) f2).getFormulae());
				return removeDuplicateFormulae(newAnd);
			}

			if (f2 instanceof Or){
				Or newOr = new Or();
				for (BooleanFormulae f:((Or) f2).getFormulae()){
					And newAnd = new And();
					newAnd.addAllFormulae(((And) f1).getFormulae());
					newAnd.addFormulae(f);
					newOr.addFormulae(newAnd);
				}
				return removeDuplicateFormulae(newOr);
			}

			return getAndDistribution(f1, new And(f2));
		}

		if (f1 instanceof Or){
			if (f2 instanceof And){
				return getAndDistribution(f2, f1);
			}

			if (f2 instanceof Or){
				Or newOr = new Or();
				for (BooleanFormulae fi:((Or) f1).getFormulae()){
					for (BooleanFormulae fj:((Or) f2).getFormulae()){
						And newAnd = new And(fi, fj);
						newOr.addFormulae(newAnd);
					}
				}
				return removeDuplicateFormulae(newOr);
			}

			return getAndDistribution(f1, new And(f2));
		}

		return getAndDistribution(new And(f1), f2);
	}

	private BooleanFormulae getNormalizedNot(Not f) {
		return new Not(getNormalizedFormula(f.f));
	}
	 */
}
