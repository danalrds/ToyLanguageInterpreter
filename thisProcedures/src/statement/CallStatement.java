package statement;

import datatypes.AbstractDictionary;
import datatypes.MyDictionary;
import exceptions.InterpreterException;
import expression.Expression;
import state.ProgramState;

import java.util.List;

public class CallStatement implements Statement {
    private String name;
    private List<Expression> expList;

    public CallStatement(String name, List<Expression> expList) {
        this.name = name;
        this.expList = expList;
    }

    @Override
    public String toString() {
        return "call "+ name + "("+expList+");";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (!state.getProcTable().contains(name))
            throw new InterpreterException("procedure name not found!");
        List<String> listvariable=state.getProcTable().getValue(name).getKey();
        Statement procBody=state.getProcTable().getValue(name).getValue();
        AbstractDictionary<String,Integer> newSymbolTable=new MyDictionary<>();
        int index=0;
        for(Expression exp:expList){
            int value=exp.eval(state.getSymbolTable(),state.getHeap());
            newSymbolTable.add(listvariable.get(index),value);
            index++;
        }
        state.getStackSymbolTable().push(newSymbolTable);
        state.getExecStack().push(new ReturnStatement());
        state.getExecStack().push(procBody);
        return null;
    }
}
