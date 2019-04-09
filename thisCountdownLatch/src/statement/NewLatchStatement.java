package statement;

import expression.Expression;
import state.ProgramState;

public class NewLatchStatement implements Statement {
    private String varName;
    private Expression exp;

    public NewLatchStatement(String varName, Expression exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "newLatch(" + varName + ',' + exp + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int number = exp.eval(state.getSymbolTable(), state.getHeap());
        int id = IdGenerator.generateId();
        synchronized (state.getLatchTable()) {
            state.getLatchTable().add(id, number);
        }
        if (state.getSymbolTable().contains(varName))
            state.getSymbolTable().update(varName, id);
        else
            state.getSymbolTable().add(varName, id);
        return null;
    }
}
