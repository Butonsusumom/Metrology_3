package sample;

import javafx.scene.control.TableColumn;

public class FillingTable extends TableColumn<FillingTable, Object> {

    private String P;
    private String M;
    private String C ;
    private String T;
    private String P1;
    private String M1;
    private String C1 ;
    private String T1;
    private String Operand;
    private String OperandKolvo;


    public FillingTable( String Operand, String OperandKolvo,
                         String P, String M, String C,String T,
                         String P1, String M1, String C1,String T1) {

        this.Operand = Operand ;
        this.OperandKolvo = OperandKolvo ;

        this.P = P;
        this.M = M;
        this.C = C ;
        this.T = T;

        this.P1 = P1 ;
        this.M1 = M1 ;
        this. C1 = C1 ;
        this.T1 = T1 ;


    }

    public FillingTable() {
    }

    public String getP() {
        return P;
    }

    public void setP(String P) {
        this.P = P;
    }

    public String getM() {
        return M;
    }

    public void setM(String M) {
        this.M = M;
    }

    public String getC () {
        return C ;
    }

    public void setC (String C) {
        this.C = C ;
    }

    public String getT() {
        return T;
    }

    public void setT(String T) {
        this.T = T;
    }

    public String getP1() {
        return P1;
    }

    public void setP1(String P1) { this.P1 = P1; }

    public String getM1() {
        return M1;
    }

    public void setM1(String M1) {
        this.M1 = M1;
    }

    public String getC1 () {
        return C1 ;
    }

    public void setC1 (String C1) {
        this.C1 = C1 ;
    }

    public String getOperand() {
        return Operand;
    }

    public void setOperand(String Operand) {
        this.Operand = Operand;
    }

    public String getOperandKolvo() {
        return OperandKolvo;
    }

    public void setOperandKolvo(String OperandKolvo) {
        this.OperandKolvo = OperandKolvo;
    }


    public String getT1() {
        return T1;
    }

    public void setT1(String t1) {
        T1 = t1;
    }
}

