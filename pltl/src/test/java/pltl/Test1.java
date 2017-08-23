package pltl;

public class Test1 {

	public static void main(String[] args) {
		String s = "FORMULA:"
//				+ "(&&"//there must be an alw here
//				+ "(withif (-p- a) 2)"
//				+ "(> (zot-p (withinf (-p- a) 2)) 0.5)"
////				+ "(!! (= (zot-p (-p- a)) 0.9))"
//				+ ")";
				+ "(withif (-p- a) 2)";		Parser p = new Parser();
		p.Parse(s);
	}

}
