package statement;

import datatypes.AbstractDictionary;
import expression.Expression;
import state.ProgramState;

public class AssignStatement implements Statement {
    private String name;
    private Expression expression;

    public AssignStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return name + " = " + expression + ";";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        //AbstractDictionary<String, Integer> symbolTable = state.getSymbolTable();
        AbstractDictionary<Integer, Integer> heap = state.getHeap();
        int value = expression.eval(state.getSymbolTable(),heap);
        if (state.getSymbolTable().contains(name))
            state.getSymbolTable().update(name, value);
        else
            state.getSymbolTable().add(name, value);
        return null;
    }

}
