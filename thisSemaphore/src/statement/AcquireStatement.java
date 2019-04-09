package statement;

import exceptions.InterpreterException;
import javafx.util.Pair;
import state.ProgramState;

import java.util.Set;

public class AcquireStatement implements Statement {
    private String varName;

    public AcquireStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "acquire(" + varName + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (!state.getSymbolTable().contains(varName))
            throw new InterpreterException("semaphore not in SymbolTable!");
        int foundIndex=state.getSymbolTable().getValue(varName);
        synchronized (state.getSemaphoreTable()){
            if (!state.getSemaphoreTable().contains(foundIndex))
                throw new InterpreterException("index not in SemaphoreTable!");
            int value=state.getSemaphoreTable().getValue(foundIndex).getValue();
            if (value>0){
                if (state.getSemaphoreTable().getValue(foundIndex).getKey().contains(state.getId()))
                    throw new InterpreterException("thread already in the critical section!");
                else{
                    Set<Integer> set=state.getSemaphoreTable().getValue(foundIndex).getKey();
                    set.add(state.getId());
                    state.getSemaphoreTable().update(foundIndex,new Pair(set,value-1));
                }
            }

            else
                state.getExecStack().push(this);
        }
        return null;
    }
}
