package statement;

import exceptions.InterpreterException;
import state.ProgramState;

public class lock implements Statement {
    private String varName;

    public lock(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "lock(" + varName + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (!state.getSymbolTable().contains(varName))
            throw new InterpreterException("variable of lock not in symbolTable!");
        int foundIndex = state.getSymbolTable().getValue(varName);
        synchronized (state.getLockTable()) {
            if (!state.getLockTable().contains(foundIndex))
                throw new InterpreterException("index not found in lockTable!");
            if (state.getLockTable().getValue(foundIndex) == -1)
                state.getLockTable().update(foundIndex, state.getId());
            else
                state.getExecStack().push(this);
        }
        return null;
    }
}
