package ir.ac.kntu;

public class LetContext extends ParserRuleContext {

    private final TerminalNode variableName;
    private final TerminalNode variableValue;

    public LetContext(TerminalNode variableName, TerminalNode variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;

        // Add the arguments as  children to this node.
        this.addChild(variableName);
        this.addChild(variableValue);
    }

    public TerminalNode getVariableName() {
        return variableName;
    }

    public TerminalNode getVariableValue() {
        return variableValue;
    }
}