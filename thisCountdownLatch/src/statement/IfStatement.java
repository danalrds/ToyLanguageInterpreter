package statement;

import datatypes.AbstractDictionary;
import expression.Expression;
import state.ProgramState;

public class IfStatement implements Statement {
    Expression expression;
    Statement thenS;
    Statement elseS;

    public IfStatement(Expression expression, Statement thenS, Statement elseS) {
        this.expression = expression;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "if (" + expression + ") then " + thenS.toString() + " else " + elseS.toString() + "";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        AbstractDictionary<String, Integer> symbolTable = state.getSymbolTable();
        AbstractDictionary<Integer, Integer> heap = state.getHeap();
        int value = expression.eval(symbolTable, heap);
        if (value == 0) {
            state.getExecStack().push(elseS);
            //elseS.execute(state);
        } else {
            state.getExecStack().push(thenS);
            //thenS.execute(state);
        }
        return null;
    }
}
