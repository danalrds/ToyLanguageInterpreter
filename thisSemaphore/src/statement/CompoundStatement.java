package statement;

import datatypes.MyStack;
import state.ProgramState;

public class CompoundStatement implements Statement {
    private Statement first;
    private Statement second;


    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
        //System.out.println(first.toString());
        //System.out.println(second.toString());
    }

    @Override
    public String toString() {
        return "( " + first + " " + second + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyStack<Statement> stack = state.getExecStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
}
