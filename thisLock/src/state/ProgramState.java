package state;

import datatypes.*;
import exceptions.InterpreterException;
import statement.Statement;

import java.io.BufferedReader;

public class ProgramState {
    private MyStack<Statement> execStack;
    private AbstractDictionary<String, Integer> symbolTable;
    private MyList<Integer> output;
    private DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable;
    private Heap<Integer, Integer> heap;
    private AbstractDictionary<Integer,Integer> lockTable;
    private int id;

    public ProgramState(MyStack<Statement> execStack,
                        AbstractDictionary<String, Integer> symbolTable,
                        MyList<Integer> output, DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable,
                        Heap<Integer, Integer> heap,
                        AbstractDictionary<Integer,Integer> lockTable,
                        int id) {
        this.execStack = execStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable=lockTable;
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nid=" + this.id +
                "\nProgramState{" + '\n' +
                " EXECSTACK=" + "\n" + execStack.toString() +
                "SYMBOLTABLE=" + '\n' + symbolTable.toString() +
                "FILETABLE=" + '\n' + fileTable.toString() +
                "HEAP=" + '\n' + heap.toString() +
                "LOCKTABLE"+'\n'+lockTable.toString()+
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
        return symbolTable;
    }

    public void setSymbolTable(AbstractDictionary<String, Integer> symbolTable) {
        this.symbolTable = symbolTable;
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

    public AbstractDictionary<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public void setLockTable(AbstractDictionary<Integer, Integer> lockTable) {
        this.lockTable = lockTable;
    }
}
