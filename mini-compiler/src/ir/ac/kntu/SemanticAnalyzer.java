package ir.ac.kntu;
import java.util.HashMap;
import java.util.Map;

/**
 * NOTE: checking whether the variable is declared before "SHOW VAR" is an example of `Semantic`
 * check.
 */
public class SemanticAnalyzer extends SimplerLangBaseVisitor<Void>{
private final Map<String, String> variableMap;

    public SemanticAnalyzer() {
        super();
        this.variableMap = new HashMap<>();
    }

    /** Validate Statement Semantics. */
    @Override
    public Void visitStatement(StatementContext context) throws SemanticException {
        RuntimeException ex1 = new RuntimeException();
        if (context.getLetContext() == null && context.getShowContext() == null) {
            throw new SemanticException("Statement should of type LET or SHOW.", ex1);
        } else if (context.getLetContext() != null && context.getShowContext() != null) {
            throw new SemanticException("Statement should be either of type LET or SHOW & not both.", ex1);
        }

        return super.visitStatement(context);
    }

    /** Validate LET Semantics. */
    @Override
    public Void visitLet(LetContext context) throws SemanticException {

        String variableName = context.getVariableName().getText();
        String variableValue = context.getVariableValue().getText();

        RuntimeException ex = new RuntimeException();
        if (variableName == null || variableName.isEmpty()) {
            throw new SemanticException("Variable name cannot be empty.", ex);
        } else if (variableValue == null || variableValue.isEmpty()) {
            throw new SemanticException("Variable value cannot be empty.", ex);
        }

        // Check if variable value is Integer. In our case, this will be already handled in the
        // Tokenizer.
        try {
            Integer.parseInt(variableValue);
        } catch (NumberFormatException | NullPointerException ex3) {
            throw new SemanticException("Variable value should be integer.", ex3);
        }

        // This will be used to check whether variable is declared using LET before invoking SHOW for
        // the variable.
        variableMap.put(variableName, variableValue);

        return super.visitLet(context);
    }

    /**
     * Validate SHOW Semantics.
     *
     * <p>NOTE: We validate if the variable is previously declared using LET.
     */
    @Override
    public Void visitShow(ShowContext context) throws SemanticException {

        TerminalNode variableNameTN = context.getVariableName();
        TerminalNode integerValueTN = context.getIntegerValue();

        /* 1. Checking whether either of VAR | INT is present.*/
        boolean isVarPresent = variableNameTN != null && !variableNameTN.getText().isEmpty();
        boolean isIntPresent = integerValueTN != null && !integerValueTN.getText().isEmpty();

        RuntimeException ex = new RuntimeException();
        if (!isVarPresent && !isIntPresent) {
            throw new SemanticException("SHOW should have integer or variable as argument", ex);
        } else if (isVarPresent && isIntPresent) {
            throw new SemanticException("SHOW should have either integer or variable as argument", ex);
        }

    /* 2. If SHOW Argument is Number, check if it is an integer. In our case, this will be
    already handled in the Tokenizer.*/
        if (integerValueTN != null) {
            try {
                Integer.parseInt(integerValueTN.getText());
            } catch (NumberFormatException | NullPointerException ex2) {
                throw new SemanticException("SHOW argument is not a valid integer.", ex2);
            }
        }

        /* 3. if SHOW Argument is Variable, check if the variable is declared previously.*/
        if (variableNameTN != null && !variableMap.containsKey(variableNameTN.getText())) {
            throw new SemanticException("SHOW argument variable is not declared.", ex);
        }

        return super.visitShow(context);
}}
