package state;

import datatypes.*;
import exceptions.InterpreterException;
import javafx.util.Pair;
import statement.Statement;

import java.io.BufferedReader;
import java.util.List;
import java.util.Stack;

public class ProgramState {
    private MyStack<Statement> execStack;
    private Stack<AbstractDictionary<String, Integer>>  stackSymbolTable;
    private MyList<Integer> output;
    private DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable;
    private Heap<Integer, Integer> heap;
    private ProcTable<String, Pair<List<String>,Statement>> procTable;
    private int id;

    public ProgramState(MyStack<Statement> execStack,
                        Stack<AbstractDictionary<String,Integer>> stackSymbolTable,
                        MyList<Integer> output, DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable,
                        Heap<Integer, Integer> heap,
                        ProcTable<String,Pair<List<String>,Statement>> procTable,
                        int id) {
        this.execStack = execStack;
        this.stackSymbolTable=stackSymbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procTable=procTable;
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nid=" + this.id +
                "\nProgramState{" + '\n' +
                " EXECSTACK=" + "\n" + execStack.toString() +
                "STACKSYMBOLTABLE=" + '\n' + stackSymbolTable.toString() +
                "FILETABLE=" + '\n' + fileTable.toString() +
                "HEAP=" + '\n' + heap.toString() +
                "PROCTABLE"+'\n'+procTable.toString()+
                "OUTPUT=" + '\n' + output.toString() +
                '}';
    }

    public MyStack<Statement> getExecStack() {
        return execStack;
    }

    public boolean isNotCompleted() {
        if (!execStack.isEmpty())
            return true;
        return false;
    }

    public ProgramState oneStep() {
        if (this.execStack.isEmpty())
            throw new InterpreterException("ExecStack is empty!");
        Statement statement = execStack.pop();
        return statement.execute(this);
    }

    public void setExecStack(MyStack<Statement> execStack) {
        this.execStack = execStack;
    }

    public AbstractDictionary<String, Integer> getSymbolTable() {
        if (!stackSymbolTable.isEmpty())
            return stackSymbolTable.peek();
        else
            throw new InterpreterException("empty stack!");
    }

    public Stack<AbstractDictionary<String, Integer>> getStackSymbolTable() {
        return stackSymbolTable;
    }

    public void setSymbolTable(Stack<AbstractDictionary<String, Integer>> stackSymbolTable) {
        this.stackSymbolTable = stackSymbolTable;
    }

    public MyList<Integer> getOutput() {
        return output;
    }

    public void setOutput(MyList<Integer> output) {
        this.output = output;
    }

    public DictionaryFile<Integer, Tuple<String, BufferedReader>> getFileTable() {
        return fileTable;
    }

    public void setFileTable(DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable) {
        this.fileTable = fileTable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Heap<Integer, Integer> getHeap() {
        return heap;
    }

    public void setHeap(Heap<Integer, Integer> heap) {
        this.heap = heap;
    }

    public ProcTable<String, Pair<List<String>, Statement>> getProcTable() {
        return procTable;
    }
}
