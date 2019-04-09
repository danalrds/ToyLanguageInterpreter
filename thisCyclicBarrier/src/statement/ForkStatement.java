package statement;

import datatypes.AbstractDictionary;
import datatypes.MyDictionary;
import datatypes.MyStack;
import datatypes.MyStackImpl;
import state.ProgramState;

import java.util.Map;

public class ForkStatement implements Statement {
    private Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyStack<Statement> newStack = new MyStackImpl<>();
        newStack.push(this.statement);
        AbstractDictionary<String, Integer> newSymbolTable = new MyDictionary<>();
        AbstractDictionary<String, Integer> crtSymbolTable = state.getSymbolTable();
        for (Map.Entry<String, Integer> e : crtSymbolTable.entrySet()) {
            newSymbolTable.add(e.getKey(), e.getValue());
        }
        int newId = IdGenerator.generateId();
        ProgramState newState = new ProgramState(newStack, newSymbolTable, state.getOutput(),
                state.getFileTable(), state.getHeap(),state.getBarrierTable(), newId);
        return newState;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ");";
    }
}
