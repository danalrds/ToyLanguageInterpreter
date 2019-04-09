package expression;

import datatypes.AbstractDictionary;
import exceptions.InterpreterException;

public class HeapReading extends Expression {
    private String varname;

    public HeapReading(String varname) {
        this.varname = varname;
    }

    @Override
    public int eval(AbstractDictionary<String, Integer> symbolTable, AbstractDictionary<Integer, Integer> heap) {
        if (!symbolTable.contains(varname))
            throw new InterpreterException("Variable name not found!");
        int id = symbolTable.getValue(this.varname);
        if (!heap.contains(id))
            throw new InterpreterException("Heap location not allocated!");
        int value = heap.getValue(id);
        return value;
    }

    @Override
    public String toString() {
        return "rH(" + varname + ");";
    }
}
