package arith;

public enum Op {
	LT, LTE, EQ, GT, GTE, PLUS, MINUS, MUL;
	
	public String toString() {
		switch (this) {
		case LT:
			return "<";
		case LTE:
			return "<=";
		case EQ:
			return "=";
		case GT:
			return ">";
		case GTE:
			return ">=";
		case PLUS:
			return "+";
		case MINUS:
			return "-";
		case MUL:
			return "*";
		}
		return null;
	}
}

