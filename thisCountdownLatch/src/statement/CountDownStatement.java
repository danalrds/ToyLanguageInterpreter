package statement;

import exceptions.InterpreterException;
import state.ProgramState;

public class CountDownStatement implements Statement {
    private String varName;

    public CountDownStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "countDown(" + varName + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (!state.getSymbolTable().contains(varName))
            throw new InterpreterException("var not in SymbolTable!");
        int foundIndex = state.getSymbolTable().getValue(varName);
        synchronized (state.getLatchTable()) {
            if (state.getLatchTable().contains(foundIndex)) {
                if (state.getLatchTable().getValue(foundIndex) > 0) {
                    int oldValue = state.getLatchTable().getValue(foundIndex);
                    state.getLatchTable().update(foundIndex, oldValue - 1);
                    state.getOutput().add(state.getId());
                }
            }
        }
        return null;
    }
}
