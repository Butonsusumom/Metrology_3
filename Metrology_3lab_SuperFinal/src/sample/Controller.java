package sample;

import java.util.LinkedHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import metrics.Metrics3;

public class Controller {

    private ObservableList<FillingTable> usersData = FXCollections.observableArrayList();


    @FXML
    private Label LabelQ1,LabelQ2;



    @FXML
    private TableView<FillingTable> table;

    @FXML
    private TableColumn<FillingTable, String> C,M, T,P,C1,M1, T1,P1, Operand, OperandKolvo;

    @FXML
    private Button BGo;


    @FXML
    void initialize() {

        BGo.setOnAction(actionEvent -> {

            table.getItems().clear();

            initData();

            // устанавливаем тип и значение которое должно хранится в колонке
            Operand.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("Operand"));
            OperandKolvo.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("OperandKolvo"));

            P.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("P"));
            M.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("M"));
            C.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("C"));
            T.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("T"));

            P1.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("P1"));
            M1.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("M1"));
            C1.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("C1"));
            T1.setCellValueFactory(new PropertyValueFactory<FillingTable, String>("T1"));


            // заполняем таблицу данными
            table.setItems(usersData);

        });

    }

    private void initData() {

        int Q1=0,Q2=0;
        Metrics3 metrics3 = new Metrics3("C:\\Users\\KSU\\универ\\второй курс\\3 сем\\Метрология\\Задание№3_МетрикиПотокаДанных\\Metrology_3lab_SuperFinal\\text.txt");
        LinkedHashMap<String, Integer> operands = metrics3.searchOperands();
        String operandP, operandM, operandC, operandT;
        String operandP1, operandM1, operandC1, operandT1;
        int lastIndex = metrics3.defineAmountCol();
        Object[] ylala = metrics3.operandsMnePlevat.keySet().toArray();
        int kolvoSpen = 0 ;

        for (int i = 0; i < ylala.length; i++) {

            if (i < metrics3.P.size()) operandP = metrics3.P.get(i);
            else operandP = "-";
            if (i < metrics3.M.size()) operandM = metrics3.M.get(i);
            else operandM = "-";
            if (i < metrics3.C.size()) operandC = metrics3.C.get(i);
            else operandC = "-";
            if (i < metrics3.T.size()) operandT = metrics3.T.get(i);
            else operandT = "-";

            if (i < metrics3.P1.size()) operandP1 = metrics3.P1.get(i);
            else operandP1 = "-";
            if (i < metrics3.M1.size()) operandM1 = metrics3.M1.get(i);
            else operandM1 = "-";
            if (i < metrics3.C1.size()) operandC1 = metrics3.C1.get(i);
            else operandC1 = "-";
            if (i < metrics3.T1.size()) operandT1 = metrics3.T1.get(i);
            else operandT1 = "-";

            kolvoSpen += metrics3.operandsMnePlevat.get(ylala[i].toString()) ;
            usersData.add(new FillingTable(ylala[i].toString(), Integer.toString(metrics3.operandsMnePlevat.get(ylala[i].toString())),
                    operandP, operandM, operandC, operandT, operandP1, operandM1, operandC1, operandT1));
        }
        Q1 =metrics3.P.size()+2*metrics3.M.size()+3*metrics3.C.size()+metrics3.T.size()/2;


        usersData.add(new FillingTable("Кол-во", Integer.toString(kolvoSpen), Integer.toString(metrics3.P.size()),
                Integer.toString(metrics3.M.size()),
                Integer.toString(metrics3.C.size()), Integer.toString(metrics3.T.size()),

                Integer.toString(metrics3.P1.size()),
                Integer.toString(metrics3.M1.size()),
                Integer.toString(metrics3.C1.size()),
                Integer.toString(metrics3.T1.size())));


        Q2 =metrics3.P1.size()+2*metrics3.M1.size()+3*metrics3.C1.size()+metrics3.T1.size()/2;
        LabelQ1.setText("Q1="+Q1);
        LabelQ2.setText("Q2="+Q2);

    }



}




