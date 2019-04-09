package statement;

import exceptions.InterpreterException;
import state.ProgramState;

import java.util.List;

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
        synchronized (state.getBarrierTable()) {
            if (!state.getBarrierTable().contains(foundIndex))
                throw new InterpreterException("index not in BarrierTable!");
            if (state.getBarrierTable().getValue(foundIndex).getKey().size() != state.getBarrierTable().getValue(foundIndex).getValue()) {
                if (state.getBarrierTable().getValue(foundIndex).getKey().contains(state.getId()))
                    state.getExecStack().push(this);
                else {
                    state.getExecStack().push(this);
                    state.getBarrierTable().getValue(foundIndex).getKey().add(state.getId());
                }
            }
        }
        return null;
    }
}
