package statement;

import expression.Expression;
import state.ProgramState;

public class PrintStatement implements Statement {
    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        state.getOutput().add(expression.eval(state.getSymbolTable(),state.getHeap()));  ////here
        return null;

    }
}
