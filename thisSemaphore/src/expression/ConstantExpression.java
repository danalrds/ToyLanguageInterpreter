package expression;

import datatypes.AbstractDictionary;

public class ConstantExpression extends Expression {
    private int number;


    public ConstantExpression(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public int eval(AbstractDictionary<String, Integer> symbolTable, AbstractDictionary<Integer,Integer> heap) {
        return number;
    }
}
