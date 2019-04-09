package statement;

import expression.Expression;
import javafx.util.Pair;
import state.ProgramState;

import java.util.ArrayList;

public class NewBarrierStatement implements Statement {
    private String varName;
    private Expression exp;
    public NewBarrierStatement(String varName, Expression exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "newBarrier(" + varName + ","+ exp+");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int number=exp.eval(state.getSymbolTable(),state.getHeap());
        int id=IdGenerator.generateId();
        synchronized (state.getBarrierTable()){
            state.getBarrierTable().add(id,new Pair(new ArrayList <>(),number));
        }
        if (state.getSymbolTable().contains(varName))
            state.getSymbolTable().update(varName,id);
        else
            state.getSymbolTable().add(varName,id);
        return null;
    }
}
