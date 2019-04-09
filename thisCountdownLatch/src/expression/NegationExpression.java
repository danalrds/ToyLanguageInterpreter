package expression;

import datatypes.AbstractDictionary;

public class NegationExpression extends Expression {
    private Expression exp;

    public NegationExpression(Expression exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "not("+exp + ";";
    }

    @Override
    public int eval(AbstractDictionary<String, Integer> symbolTable, AbstractDictionary<Integer, Integer> heap) {
        if (exp.eval(symbolTable,heap)==1)
            return 0;
        else
            return 1;
    }
}
