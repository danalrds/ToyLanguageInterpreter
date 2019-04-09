package sample;

import expression.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import statement.*;

import java.util.ArrayList;
import java.util.List;

public class ProgramsWindowController {
    @FXML
    private ListView<Statement> listProgramsList;

    @FXML
    private Button selectProgramButton;

    public static Stage stage;

    @FXML
    public void initialize(){
        listProgramsList.getItems().clear();
        listProgramsList.getItems().addAll(getProgramsList());
        selectProgramButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectProgramHandler();
                stage.close();
            }
        });
    }

    public void selectProgramHandler(){
        int idx=listProgramsList.getSelectionModel().getSelectedIndex();
        if(idx!=-1){
            System.out.println("selection done with "+idx);
            SelectedProgramHolder.selectedProgram=getProgramsList().get(idx);
        }
    }

    private List<Statement> getProgramsList() {
        List<Statement> list=new ArrayList<>();
        Statement first = new CompoundStatement(new AssignStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new HeapAllocation("v", new ConstantExpression(20)),
                        new HeapAllocation("a", new ConstantExpression(22))));
        Statement printare = new PrintStatement(new VarExpression("v"));
        Statement ex1 = new CompoundStatement(first, printare);
        ///SECOND
        Statement firstthis=new CompoundStatement(new AssignStatement("v",new ConstantExpression(10)),
                new HeapAllocation("a",new ConstantExpression(22)));
        Statement fork=new ForkStatement(
                new CompoundStatement(
                        new HeapWriting("a",new ConstantExpression(30)),
                        new CompoundStatement(
                                new AssignStatement("v",new ConstantExpression(32)),
                                new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                        new PrintStatement(new HeapReading("a"))))));
        Statement last=new CompoundStatement(
                new PrintStatement(new VarExpression("v")),new PrintStatement(new HeapReading("a")));
        Statement ex2=new CompoundStatement(firstthis,new CompoundStatement(fork,last));
        //ex 3

        Statement whilestate = new WhileStatement(
                new ArithmeticExpression('-', new VarExpression("v"), new ConstantExpression(4)),
                new CompoundStatement(
                        new PrintStatement(new VarExpression("v")),
                        new AssignStatement(
                                "v", new ArithmeticExpression('-', new VarExpression("v"), new ConstantExpression(1))
                        )));
        Statement ex3 = new CompoundStatement(
                new AssignStatement("v", new ConstantExpression(5)),
                new CompoundStatement(whilestate, new PrintStatement(new VarExpression("v"))));
        /////ex 4

        String filename="D:\\Intellij\\this\\src\\test.in";
        OpenRFile file = new OpenRFile("var_f",filename);
        Statement rf = new ReadFile(new VarExpression("var_f"), "var_c");
        Statement ps = new PrintStatement(new VarExpression("var_c"));
        Statement ifst = new IfStatement(new VarExpression("var_c"),
                new CompoundStatement(
                        new ReadFile(new VarExpression("var_f"), "var_c"),
                        new PrintStatement(new VarExpression("var_c"))),
                new PrintStatement(new ConstantExpression(0)));
        Statement closes = new CloseRFile(new VarExpression("var_f"));
        Statement ex4 = new CompoundStatement(file,
                new CompoundStatement(rf, new CompoundStatement(ps, new CompoundStatement(ifst, closes))));
        Statement partt1=new RepeatStatement(
                new CompoundStatement(
                        new ForkStatement(
                                new CompoundStatement(
                                        new PrintStatement(new VarExpression("v")),
                                        new AssignStatement("v",
                                                new ArithmeticExpression('-',new VarExpression("v"), new ConstantExpression(1)))
                                )),
                                new AssignStatement("v",
                                        new ArithmeticExpression('+',new VarExpression("v"), new ConstantExpression(1)))
                        ),
                new BooleanExpression(new VarExpression("v"),new ConstantExpression(3), "==")
                );
        Statement partt2=new CompoundStatement(
                new CompoundStatement(
                        new AssignStatement("x", new ConstantExpression(1)),
                        new AssignStatement("y", new ConstantExpression(2))
                ),
                new CompoundStatement(
                        new AssignStatement("z",new ConstantExpression(3)),
                        new AssignStatement("w",new ConstantExpression(4))
                )
        );
        Statement ex5= new CompoundStatement(
                new CompoundStatement(
                    new AssignStatement("v",new ConstantExpression(0)),
                    partt1
                ),
                new CompoundStatement(
                        partt2,
                        new PrintStatement(new ArithmeticExpression('*', new VarExpression("v"),new ConstantExpression(10)))
                )
        );

        Statement firstpart=new CompoundStatement(
                new HeapAllocation("v1",new ConstantExpression(2)),
                new CompoundStatement(
                        new HeapAllocation("v2", new ConstantExpression(3)),
                        new CompoundStatement(
                                new HeapAllocation("v3",new ConstantExpression(4)),
                                new NewLatchStatement("cnt",
                                        new HeapReading("v2"))
                        )
                )
        );
        Statement firstFork=new ForkStatement(
                new CompoundStatement(
                        new HeapWriting("v1",
                                new ArithmeticExpression('*',new HeapReading("v1"),new ConstantExpression(10))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReading("v1")),
                                new CountDownStatement("cnt")
                        )
                )
        );
        Statement secondFork=new ForkStatement(
                new CompoundStatement(
                        new HeapWriting("v2",
                                new ArithmeticExpression('*',new HeapReading("v2"),new ConstantExpression(10))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReading("v2")),
                                new CountDownStatement("cnt")
                        )
                )
        );
        Statement thirdFork=new ForkStatement(
                new CompoundStatement(
                        new HeapWriting("v3",
                                new ArithmeticExpression('*',new HeapReading("v3"),new ConstantExpression(10))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReading("v3")),
                                new CountDownStatement("cnt")
                        )
                )
        );
        Statement lastpart=new CompoundStatement(
                new AwaitStatement("cnt"),
                new CompoundStatement(
                        new PrintStatement(new VarExpression("cnt")),
                        new CompoundStatement(
                                new CountDownStatement("cnt"),
                                new PrintStatement(new VarExpression("cnt"))
                        )
                )
        );
        Statement ex6= new CompoundStatement(
                firstpart,
                new CompoundStatement(
                        firstFork,
                        new CompoundStatement(
                                secondFork,
                                new CompoundStatement(
                                        thirdFork,
                                        lastpart
                                )
                        )
                )
        );
        list.add(ex1);
        list.add(ex2);
        list.add(ex3);
        list.add(ex4);
        list.add(ex5);
        list.add(ex6);
        return list;
    }


}
