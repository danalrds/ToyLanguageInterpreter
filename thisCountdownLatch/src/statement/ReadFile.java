package statement;

import exceptions.InterpreterException;
import expression.Expression;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements Statement {
    private Expression exp;
    private String varname;

    public ReadFile(Expression exp, String varname) {
        this.exp = exp;
        this.varname = varname;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int value = this.exp.eval(state.getSymbolTable(),state.getHeap());
        if (!state.getFileTable().contains(value)) {
            throw new InterpreterException("File does not exists!");
        }
        try {
            BufferedReader bf = state.getFileTable().getValue(value).getSecond();
            String line = bf.readLine();
            int val = 0;
            if (line != null) {
                val = Integer.parseInt(line);
            }
            if (state.getSymbolTable().contains(varname)) {
                state.getSymbolTable().update(varname, val);
            } else {
                state.getSymbolTable().add(varname, val);
            }
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "readfile(" + exp.toString() + "," + varname + ");";
    }
}
