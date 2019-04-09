package sample;

import controller.Controller;
import datatypes.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import repository.Repository;
import repository.RepositoryImpl;
import state.ProgramState;
import statement.Statement;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class LayoutController {
    @FXML
    private TableView<Pair<String, Integer>> tableSymbolTable;

    @FXML
    private TableColumn<Pair<String, Integer>, Integer> symbolTableValueColumn;

    @FXML
    private TableColumn<Pair<String, Integer>, String> symbolTableVarColumn;


    @FXML
    private TableView<Pair<Integer, Tuple<String, BufferedReader>>> tableFileTable;

    @FXML
    private TableColumn<Pair<Integer, Tuple<String, BufferedReader>>, Tuple<String, BufferedReader>> fileTableFileNameColumn;

    @FXML
    private TableColumn<Pair<Integer, Tuple<String, BufferedReader>>, Integer> fileTableIdColumn;

    @FXML
    private TableView<Pair<Integer, Integer>> tableHeap;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> heapAdressColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> heapValueColumn;

    @FXML
    private ListView<Statement> listExeStack;

    @FXML
    private ListView<Integer> listOutput;

    @FXML
    private ListView<Integer> listPrograms;

    @FXML
    public TextField nrPrograms;

    @FXML
    private Button oneStepButton;

    @FXML
    private Button loadProgramButton;

    @FXML
    private TableColumn<Triple<Integer,Integer,String>, Integer>barrierIndexColumn;

    @FXML
    private TableColumn<Triple<Integer,Integer,String>, String>barrierListColumn;

    @FXML
    private TableColumn<Triple<Integer,Integer,String>, Integer> barrierValueColumn;


    @FXML
    private TableView<Triple<Integer,Integer,String>> tableBarrierTable;


    private Controller controller;
    private int selected = -1;

    public LayoutController() {

        Repository repo1 = new RepositoryImpl("D:\\Intellij\\this\\src\\states1.txt");
        Controller control1 = new Controller(repo1);
        ///control1.createProgramState(ex4);
        this.controller = control1;
    }

    @FXML
    void initialize() {
        symbolTableVarColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        symbolTableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        heapAdressColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        fileTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        fileTableFileNameColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        barrierIndexColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        barrierValueColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        barrierListColumn.setCellValueFactory(new PropertyValueFactory<>("elems"));
        listPrograms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selected=listPrograms.getSelectionModel().getSelectedIndex();
            System.out.println("selected in listener: "+selected);
            populateSymbolTable();
            populateExeStack();
        });
    }

    public void startProgramsWindow() throws Exception {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ProgramsWindow.fxml"));
        ProgramsWindowController.stage = primaryStage;
        primaryStage.setTitle("Select a program!");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(new Scene(root, 800, 575));
        primaryStage.showAndWait();
        if (SelectedProgramHolder.selectedProgram != null) {
            this.controller.getRepository().setProgramList(new ArrayList<>());
            this.controller.createProgramState(SelectedProgramHolder.selectedProgram);
            populateAll();
            this.listPrograms.getSelectionModel().select(0);
            selected=0;

        }

    }

    void populateAll() {
        populateExeStack();
        populateSymbolTable();
        populateOutput();
        populateHeap();
        populateFileTable();
        populateBarrierTable();
        populateListPrograms();

    }

    public void populateBarrierTable(){
        if (this.controller.getRepository().getProgramList().size() > 0){
            BarrierTable<Integer,Pair<List<Integer>,Integer>>barrierTable=controller.getRepository().getProgramList().get(0).getBarrierTable();
            tableBarrierTable.getItems().setAll(
                    barrierTable.getContent()
                            .keySet().stream()
                            .map(k->{return new Triple<Integer,Integer,String>(k,barrierTable.getValue(k).getValue(),barrierTable.getValue(k).getKey().toString());})
                            .collect(Collectors.toList())
            );

        }
        else tableBarrierTable.setItems(FXCollections.observableArrayList());

    }

    private void populateExeStack() {
        int idx = listPrograms.getSelectionModel().getSelectedIndex();
        if (this.controller.getRepository().getProgramList().size() > 0 && idx != -1) {
            MyStack<Statement> exeStack = this.controller.getRepository().getProgramList().get(idx).getExecStack();
            listExeStack.getItems().clear();
            List<Statement> statements = new ArrayList<>();
            exeStack.getAll().forEach(statements::add);
            Collections.reverse(statements);
            listExeStack.getItems().addAll(statements);
        } else
            listExeStack.setItems(FXCollections.observableArrayList());
    }

    private void populateSymbolTable() {
        int idx = listPrograms.getSelectionModel().getSelectedIndex();
        if (this.controller.getRepository().getProgramList().size() > 0 && idx != -1) {
            AbstractDictionary<String, Integer> symbolTable = this.controller.getRepository().getProgramList().get(idx).getSymbolTable();
            tableSymbolTable.getItems().clear();
            tableSymbolTable.getItems().addAll(
                    symbolTable.entrySet()
                            .stream()
                            .map(x -> new Pair<String, Integer>(x.getKey(), x.getValue()))
                            .collect(Collectors.toList())
            );
        } else
            tableSymbolTable.setItems(FXCollections.observableArrayList());
    }

    private void populateOutput() {
        if (this.controller.getRepository().getProgramList().size() > 0) {
            MyList<Integer> list = this.controller.getRepository().getProgramList().get(0).getOutput();
            listOutput.getItems().clear();
            List<Integer> elems = new ArrayList<>();
            list.getAll().forEach(elems::add);
            listOutput.getItems().addAll(elems);
        } else
            listOutput.setItems(FXCollections.observableArrayList());
    }

    private void populateHeap() {
        if (this.controller.getRepository().getProgramList().size() > 0) {
            AbstractDictionary<Integer, Integer> heap = this.controller.getRepository().getProgramList().get(0).getHeap();
            tableHeap.getItems().clear();
            tableHeap.getItems().addAll(
                    heap.entrySet()
                            .stream()
                            .map(x -> new Pair<Integer, Integer>(x.getKey(), x.getValue()))
                            .collect(Collectors.toList())
            );
        } else
            tableHeap.setItems(FXCollections.observableArrayList());
    }

    private void populateFileTable() {
        if (this.controller.getRepository().getProgramList().size() > 0) {
            AbstractDictionary<Integer, Tuple<String, BufferedReader>> fileTable = this.controller.getRepository().getProgramList().get(0).getFileTable();
            tableFileTable.getItems().clear();
            tableFileTable.getItems().addAll(
                    fileTable.entrySet()
                            .stream()
                            .map(x -> new Pair<Integer, Tuple<String, BufferedReader>>(x.getKey(), x.getValue()))
                            .collect(Collectors.toList())
            );
        } else
            tableFileTable.setItems(FXCollections.observableArrayList());
    }

    public void populateListPrograms() {
        if (this.controller.getRepository().getProgramList().size() > 0) {
            List<Integer> list = this.controller.getRepository().getProgramList().stream().map(ProgramState::getId).collect(Collectors.toList());
            listPrograms.getItems().clear();
            listPrograms.getItems().addAll(list);
            nrPrograms.setText(Integer.toString(list.size()));
        } else {
            listPrograms.setItems(FXCollections.observableArrayList());
            nrPrograms.setText("0");
        }
    }

    public void runOneStep() {
        System.out.println("button clicked");
        try {
            if (this.controller.getRepository().getProgramList().size() == 0) {
                throw new RuntimeException("no more statements to execute");
            }

            this.controller.oneStepForAllPrograms(this.controller.getRepository().getProgramList());
            populateAll();
            this.controller.getRepository().setProgramList(this.controller.removeCompletedPrograms(this.controller.getRepository().getProgramList()));
            if (this.controller.getRepository().getProgramList().size()!=0)
                populateAll();
            System.out.println("selected in onestep "+selected);
            listPrograms.getSelectionModel().select(0);
        } catch (Exception exc) {
            //populateAll();
            //exc.printStackTrace();
            exceptionHandler(exc.getMessage());
        }
    }


    public void exceptionHandler(String s) {
        //Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(s);

        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

}