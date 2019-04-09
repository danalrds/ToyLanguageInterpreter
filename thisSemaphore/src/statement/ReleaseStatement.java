package statement;

import exceptions.InterpreterException;
import javafx.util.Pair;
import state.ProgramState;

import java.util.Set;

public class ReleaseStatement implements Statement {
    private String varName;

    public ReleaseStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "release(" + varName + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (!state.getSymbolTable().contains(varName))
            throw new InterpreterException("var not in symbolTable!");
        int foundIndex=state.getSymbolTable().getValue(varName);
        synchronized (state.getSemaphoreTable()){
            if (state.getSemaphoreTable().contains(foundIndex)){
                Set<Integer> set=state.getSemaphoreTable().getValue(foundIndex).getKey();
                if (set.contains(state.getId())){
                    int value=state.getSemaphoreTable().getValue(foundIndex).getValue();
                    set.remove(state.getId());
                    state.getSemaphoreTable().update(foundIndex,new Pair(set,value+1));
                }
                else
                    throw new InterpreterException("thread can not release, because it wasn't in the critical saection!");
            }
        }
        return null;
    }
}
