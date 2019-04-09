package repository;

import state.ProgramState;

import java.util.List;

public interface Repository {
    void addProgram(ProgramState state);

    void saveProgramLog(ProgramState state);

    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> list);
    void closeFile();
}
