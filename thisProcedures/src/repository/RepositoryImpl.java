package repository;

import exceptions.InterpreterException;
import state.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    private List<ProgramState> statesList;
    private String logFilePath;
    private String filename;
    private PrintWriter logFile = null;

    public RepositoryImpl(String filename) {
        this.filename = filename;
        this.statesList = new ArrayList<ProgramState>();
        File file = new File(filename);

        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));

        } catch (IOException e) {
            System.err.println("Eroare deschidere logFile " + e);
            throw new InterpreterException("Error in opening log file!:"+e.getMessage());
        }
    }

    public void addProgram(ProgramState programState) {
        statesList.add(programState);
    }

    @Override
    public void saveProgramLog(ProgramState state) {
        System.out.println(state.toString());
        logFile.println(state.toString());
    }

    @Override
    public List<ProgramState> getProgramList() {
        return statesList;
    }

    @Override
    public void setProgramList(List<ProgramState> list) {
        this.statesList = list;
    }

    @Override
    public void closeFile() {
        logFile.close();
    }
}
