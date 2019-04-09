package controller;

import datatypes.*;
import exceptions.InterpreterException;
import expression.ArithmeticExpression;
import expression.VarExpression;
import javafx.util.Pair;
import repository.Repository;
import state.ProgramState;
import statement.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller {

    private Repository repository;
    private ExecutorService executor;

    public Controller(Repository repository) {
        this.repository = repository;
        this.executor=Executors.newFixedThreadPool(5);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> statesList) {
        return statesList.stream().filter(k -> k.isNotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> list) throws InterruptedException {
        List<Callable<ProgramState>> callableList = list.stream()
                .map(prg -> ((Callable<ProgramState>) (prg::oneStep)))
                .collect(Collectors.toList());
        List<ProgramState> prgList = executor
                .invokeAll(callableList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception exc) {
                        return null;
                    }
                })
                .filter(prg -> prg != null)
                .collect(Collectors.toList());
        list.addAll(prgList);
        System.out.println("begin**********************");
        list.forEach(prg -> repository.saveProgramLog(prg));
        System.out.println("end**********************");
        repository.setProgramList(list);
    }


    public void allStep() {
        executor = Executors.newFixedThreadPool(5);
        List<ProgramState> list = removeCompletedPrograms(repository.getProgramList());
        System.out.println("begin**********************");
        list.forEach(prg -> repository.saveProgramLog(prg));
        System.out.println("end**********************");
        try {
            while (list.size() > 0) {
                list.forEach(prg -> garbageCollector(prg.getSymbolTable().values(), prg.getHeap()));
                oneStepForAllPrograms(list);
                list = removeCompletedPrograms(repository.getProgramList());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        executor.shutdownNow();
        this.closeFileInRepo();
        repository.setProgramList(list);
        repository.closeFile();
    }


    public void createProgramState(Statement statement) {

        MyStack<Statement> stack = new MyStackImpl();
        stack.push(statement);
        Stack<AbstractDictionary<String, Integer>> dict = new Stack<>();
        dict.push(new MyDictionary<>());
        MyList<Integer> list = new MyListImpl();
        DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable = new DictionaryFile<>();
        Heap<Integer, Integer> heap = new Heap<>();
        int id = IdGenerator.generateId();
        ProcTable<String, Pair<List<String>,Statement>> procTable=new ProcTable<String, Pair<List<String>, Statement>>();
        ProgramState state = new ProgramState(stack, dict, list, fileTable, heap,procTable, id);
        procTable.add("sum",new Pair<>(Arrays.asList("a","b"),new CompoundStatement(
                new AssignStatement("v",new ArithmeticExpression('+',new VarExpression("a"),new VarExpression("b"))),
                new PrintStatement(new VarExpression("v"))
        )));
        procTable.add("product",new Pair<>(Arrays.asList("a","b"),new CompoundStatement(
                new AssignStatement("v",new ArithmeticExpression('*',new VarExpression("a"),new VarExpression("b"))),
                new PrintStatement(new VarExpression("v"))
        )));
        this.repository.addProgram(state);
    }

    public void closeFileInRepo() {
        AbstractDictionary<Integer, Tuple<String, BufferedReader>> fileTable = repository.getProgramList().get(0).getFileTable();
        List<Integer> list = new ArrayList<Integer>();
        fileTable
                .getAll()
                .forEach(k -> {
                    try {
                        Tuple<String, BufferedReader> tuple = fileTable.getValue(k);
                        tuple.getSecond().close();

                        list.add(k);
                        System.out.println(tuple.getFirst() + " has been closed!");
                    } catch (IOException exc) {
                        throw new InterpreterException("File can not be closed!");
                    }

                });
        for (int i = 0; i < list.size(); i++)
            fileTable.remove(list.get(i));
        if (list.size() > 0)
            System.out.println(repository.getProgramList().get(0));

    }

    public Map<Integer, Integer> garbageCollector(Collection<Integer> symbolTableValues, AbstractDictionary<Integer, Integer> heap) {
        return heap.entrySet()
                .stream()
                .filter(e -> symbolTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Repository getRepository() {
        return repository;
    }
}
