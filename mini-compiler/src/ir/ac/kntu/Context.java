package ir.ac.kntu;

public class Context {
    TerminalNode terminalNode;
    public TerminalNode getVariableName(){
        return terminalNode;
    }

    public int getIntegerValue(){
        return Integer.parseInt(terminalNode.getText());
    }


}

//https://meet.google.com/emf-fsjf-rtk
