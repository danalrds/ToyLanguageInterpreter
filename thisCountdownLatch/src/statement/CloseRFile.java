package statement;

import exceptions.InterpreterException;
import expression.Expression;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements Statement {
    Expression expfile;

    public CloseRFile(Expression expfile) {
        this.expfile = expfile;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        try {
            int val = this.expfile.eval(state.getSymbolTable(), state.getHeap());
            if (!state.getFileTable().contains(val)) {
                throw new InterpreterException("File does not exist!");
            }
            BufferedReader bf = state.getFileTable().getValue(val).getSecond();
            bf.close();
            state.getFileTable().remove(val);
        } catch (IOException exc) {
            System.out.println("Close File error!");
            throw new InterpreterException(exc.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "close(" + expfile.toString() + ");";
    }
}
