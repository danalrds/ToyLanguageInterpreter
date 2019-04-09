package statement;

import datatypes.AbstractDictionary;
import datatypes.MyDictionary;
import datatypes.MyStack;
import datatypes.MyStackImpl;
import state.ProgramState;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class ForkStatement implements Statement {
    private Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyStack<Statement> newStack = new MyStackImpl<>();
        newStack.push(this.statement);
        //AbstractDictionary<String, Integer> newSymbolTable = new MyDictionary<>();
        Stack<AbstractDictionary<String, Integer>> newStackSymbolTable= new Stack<>();
        state.getStackSymbolTable().forEach(x->{
            Map<String,Integer>m=new HashMap<>();
            x.entrySet().forEach(entry->m.put(entry.getKey(),entry.getValue()));
            newStackSymbolTable.push(new MyDictionary<>(m));
        });

        int newId = IdGenerator.generateId();
        ProgramState newState = new ProgramState(newStack, newStackSymbolTable, state.getOutput(),
                state.getFileTable(), state.getHeap(),state.getProcTable(), newId);
        return newState;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ");";
    }
}
