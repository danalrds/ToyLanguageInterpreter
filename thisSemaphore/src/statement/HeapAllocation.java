package statement;

import datatypes.AbstractDictionary;
import expression.Expression;
import state.ProgramState;

public class HeapAllocation implements Statement {
    private String varname;
    private Expression expression;

    public HeapAllocation(String varname, Expression expression) {
        this.varname = varname;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        AbstractDictionary<String, Integer> symbolTable = state.getSymbolTable();
        AbstractDictionary<Integer, Integer> heap = state.getHeap();
        int value = expression.eval(symbolTable,heap);
        int id = IdGenerator.generateId();
        heap.add(id, value);
        if (symbolTable.contains(varname))
            symbolTable.update(varname, id);
        else
            symbolTable.add(varname, id);
        return null;
    }

    @Override
    public String toString() {
        return "new("+ varname + ',' + expression + ");";
    }
}
