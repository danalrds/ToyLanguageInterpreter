package expression;

import datatypes.AbstractDictionary;
import exceptions.InterpreterException;

public class ArithmeticExpression extends Expression {
    private Expression ex1;
    private Expression ex2;
    private char op;

    public ArithmeticExpression(char op, Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.op = op;
    }

    @Override
    public String toString() {
        return ex1 + " " + op + " " + ex2;

    }

    @Override
    public int eval(AbstractDictionary<String, Integer> symbolTable, AbstractDictionary<Integer,Integer> heap) {
        switch (op) {
            case '+':
                return ex1.eval(symbolTable,heap) + ex2.eval(symbolTable,heap);
            case '-':
                return ex1.eval(symbolTable,heap) - ex2.eval(symbolTable,heap);
            case '*':
                return ex1.eval(symbolTable,heap)*ex2.eval(symbolTable, heap);
            case '/': {
                int res = ex2.eval(symbolTable,heap);
                if (res == 0)
                    throw new InterpreterException("Division by 0!");
                return ex1.eval(symbolTable,heap) * res;
            }
            default:
                throw new InterpreterException("No operator!");
        }

    }


}
