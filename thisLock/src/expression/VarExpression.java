package expression;

import datatypes.AbstractDictionary;
import exceptions.InterpreterException;

public class VarExpression extends Expression {
    private String name;

    public VarExpression(String name) {
        this.name = name;
    }

    @Override
    public int eval(AbstractDictionary<String, Integer> symbolTable, AbstractDictionary<Integer,Integer> heap) {
        if (symbolTable.contains(name))
            return symbolTable.getValue(name);
        else
            throw new InterpreterException("Var does not exist!");
    }

    @Override
    public String toString() {
        return name;
    }

}
