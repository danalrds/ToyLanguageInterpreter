package statement;

import expression.Expression;
import state.ProgramState;

public class WhileStatement implements Statement {
    private Expression expression;
    private Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int value=expression.eval(state.getSymbolTable(),state.getHeap());
        if (value==1){
            state.getExecStack().push(this);
            state.getExecStack().push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while(" + expression + "){" + statement.toString() + "};";
    }
}
