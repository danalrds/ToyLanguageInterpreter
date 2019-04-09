package statement;

import state.ProgramState;

public class ReturnStatement implements Statement {
    public ReturnStatement() {
    }

    @Override
    public String toString() {
        return "return();";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        state.getStackSymbolTable().pop();
        return null;
    }
}
