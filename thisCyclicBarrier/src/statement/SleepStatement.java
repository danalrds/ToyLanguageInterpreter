package statement;

import state.ProgramState;

public class SleepStatement implements Statement {
    private int number;

    public SleepStatement(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "sleep(" +number +");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (number!=0){
            int nr=number-1;
            Statement newSleep=new SleepStatement(nr);
            state.getExecStack().push(newSleep);
        }
        return null;
    }
}
