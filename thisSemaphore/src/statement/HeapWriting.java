package statement;

import datatypes.AbstractDictionary;
import exceptions.InterpreterException;
import expression.Expression;
import state.ProgramState;

public class HeapWriting implements Statement {
    private String varname;
    private Expression expression;

    public HeapWriting(String varname, Expression expression) {
        this.varname = varname;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        AbstractDictionary<String, Integer> symbolTable = state.getSymbolTable();
        AbstractDictionary<Integer, Integer> heap = state.getHeap();
        int value = expression.eval(symbolTable, heap);
        if (!symbolTable.contains(varname))
            throw new InterpreterException("Var name not found!");
        int id = symbolTable.getValue(varname);
        if (!heap.contains(id))
            throw new InterpreterException("Heap allocation not allocated!");
        heap.update(id, value);
        return null;
    }

    @Override
    public String toString() {
        return "wH(" + varname + "," + expression.toString() + ");";
    }
}
