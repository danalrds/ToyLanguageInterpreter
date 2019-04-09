package statement;

import state.ProgramState;

public class NewLock implements Statement {
    private String varName;

    public NewLock(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "NewLock(" + varName + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int id = IdGenerator.generateId();
        synchronized (state.getLockTable()) {
            state.getLockTable().add(id, -1);
        }
        if (state.getSymbolTable().contains(varName))
            state.getSymbolTable().update(varName,id);
        else
            state.getSymbolTable().add(varName,id);
        return null;
    }
}
