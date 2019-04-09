package statement;

import exceptions.InterpreterException;
import state.ProgramState;

public class unlock implements Statement {
    private String varName;

    public unlock(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "unlock{" + varName + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (! state.getSymbolTable().contains(varName))
            throw new InterpreterException("variable not found in SymbolTable!");
        int foundIndex=state.getSymbolTable().getValue(varName);
        synchronized (state.getLockTable()){
            if (state.getLockTable().contains(foundIndex)){
                if (state.getLockTable().getValue(foundIndex)==state.getId())
                    state.getLockTable().update(foundIndex,-1);
            }
        }
        return null;
    }
}
