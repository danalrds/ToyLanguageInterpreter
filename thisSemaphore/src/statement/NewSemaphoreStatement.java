package statement;

import expression.Expression;
import javafx.util.Pair;
import state.ProgramState;

import java.util.HashSet;

public class NewSemaphoreStatement implements Statement {
    private String varName;
    private Expression permits;

    public NewSemaphoreStatement(String varName, Expression permits) {
        this.varName = varName;
        this.permits = permits;
    }

    @Override
    public String toString() {
        return "newSemaphore(" +varName + ',' +permits +");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int id=IdGenerator.generateId();
        int permValue=permits.eval(state.getSymbolTable(),state.getHeap());
        synchronized (state.getSemaphoreTable()){
            state.getSemaphoreTable().add(id,new Pair(new HashSet<>(),permValue));
        }
        if (state.getSymbolTable().contains(varName))
            state.getSymbolTable().update(varName,id);
        else
            state.getSymbolTable().add(varName,id);
        return null;
    }
}

