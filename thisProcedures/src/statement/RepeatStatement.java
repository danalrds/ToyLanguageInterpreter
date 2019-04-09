package statement;

import expression.Expression;
import expression.NegationExpression;
import state.ProgramState;

public class RepeatStatement implements Statement {
    private Statement stmt;
    private Expression exp;

    public RepeatStatement(Statement stmt, Expression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "repeat("+ stmt + ") until " + exp + ';';
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Statement equivalent=new CompoundStatement(
                stmt,
                new WhileStatement(new NegationExpression(exp),stmt)
        );
        state.getExecStack().push(equivalent);
        System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        System.out.println(exp.eval(state.getSymbolTable(),state.getHeap()));
        return null;
    }
}
