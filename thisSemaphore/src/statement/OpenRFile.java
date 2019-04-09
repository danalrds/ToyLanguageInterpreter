package statement;

import datatypes.DictionaryFile;
import datatypes.Tuple;
import exceptions.InterpreterException;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements Statement {
    private String varfile;
    private String filename;

    public OpenRFile(String varfile, String filename) {
        this.varfile = varfile;
        this.filename = filename;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable = state.getFileTable();
        if (existsFilename(fileTable)) {
            throw new InterpreterException("File already exists!");
        }
        try {
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            int id = IdGenerator.generateId();
            fileTable.add(id, new Tuple<>(filename, bf));
            if (state.getSymbolTable().contains(varfile)) {
                state.getSymbolTable().update(varfile, id);
            } else {
                state.getSymbolTable().add(varfile, id);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
            System.out.println(exc.getMessage());
        }
        return null;
    }

    public boolean existsFilename(DictionaryFile<Integer, Tuple<String, BufferedReader>> fileTable) {
        for (Integer id : fileTable.getAll()) {
            String filename = fileTable.getValue(id).getFirst();
            if (this.filename == filename)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "open(" + varfile + ", \"" + filename + "\");";
    }
}
