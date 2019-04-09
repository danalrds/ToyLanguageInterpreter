package expression;

import datatypes.AbstractDictionary;
import exceptions.InterpreterException;

public class BooleanExpression extends Expression {
    private Expression left;
    private Expression right;
    private String relation;

    public BooleanExpression(Expression left, Expression right, String relation) {
        this.left = left;
        this.right = right;
        this.relation = relation;
    }

    @Override
    public int eval(AbstractDictionary<String, Integer> symbolTable, AbstractDictionary<Integer, Integer> heap) {
        int leftvalue = left.eval(symbolTable, heap);
        int rightvalue = right.eval(symbolTable, heap);
        int rez = 1;
        switch (relation) {
            case "<": {
                if (!(leftvalue < rightvalue))
                    rez = 0;
                break;
            }
            case "<=": {
                if (!(leftvalue <= rightvalue))
                    rez = 0;
                break;
            }
            case ">": {
                if (!(leftvalue > rightvalue))
                    rez = 0;
                break;
            }
            case ">=": {
                if (!(leftvalue >= rightvalue))
                    rez = 0;
                break;
            }
            case "==": {
                if (!(leftvalue == rightvalue))
                    rez = 0;
                break;
            }
            case "!=": {
                if (leftvalue == rightvalue)
                    rez = 0;
                break;
            }
            default:
              throw new InterpreterException("Not valid relation!");
        }
        return rez;
    }

    @Override
    public String toString() {
        return "("+left.toString() + relation+right.toString()+")";
    }
}
