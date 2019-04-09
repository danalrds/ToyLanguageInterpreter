package statement;

import exceptions.InterpreterException;
import state.ProgramState;

public class AwaitStatement implements Statement {
    private String varName;

    public AwaitStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "await(" + varName + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (!state.getSymbolTable().contains(varName))
            throw new InterpreterException("var not in SymbolTable!");
        int foundIndex = state.getSymbolTable().getValue(varName);
        synchronized (state.getLatchTable()) {
            if (!state.getLatchTable().contains(foundIndex))
                throw new InterpreterException("index not found in LatchTable!");
            if (state.getLatchTable().getValue(foundIndex) != 0)
                state.getExecStack().push(this);
        }
        return null;
    }
}
