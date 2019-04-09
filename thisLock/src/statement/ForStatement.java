package statement;

import expression.*;
import state.ProgramState;

public class ForStatement implements Statement{
    private String varName;
    private Expression initialExpression;
    private Expression incrementExpression;
    private Expression boundExpression;
    private Statement stmt;

    public ForStatement(String varName, Expression initialExpression, Expression incrementExpression, Expression boundExpression, Statement stmt) {
        this.varName = varName;
        this.initialExpression = initialExpression;
        this.incrementExpression = incrementExpression;
        this.boundExpression = boundExpression;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "for( " + varName+"=" +initialExpression+";"+boundExpression+";v="+incrementExpression+"){"+stmt+"};";
    }
    @Override
    public ProgramState execute(ProgramState state) {
        Statement st=new CompoundStatement(new AssignStatement(varName,initialExpression),
                new WhileStatement(
                                boundExpression,
                        new CompoundStatement(
                                stmt, new AssignStatement(varName,incrementExpression))));
        state.getExecStack().push(st);
        return null;
    }
}
