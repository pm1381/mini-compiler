package ir.ac.kntu;

import java.util.HashMap;
import java.util.Map;

/**
 * SimplerLang Interpreter Implementation. Interpreter executes code line by line.
 *
 * <p>NOTE: Here we write the logic for storing the `let` variable and displaying the `show` output.
 */
public class InterpreterVisitor extends SimplerLangBaseVisitor<Void> {

    private final Map<String, String> variableMap;

    public InterpreterVisitor() {
        super();
        variableMap = new HashMap<>();
    }

    @Override
    public Void visitLet(LetContext context) throws SemanticException {
        this.variableMap.put(context.getVariableName().getText(), context.getVariableValue().getText());
        return super.visitLet(context);
    }

    @Override
    public Void visitShow(ShowContext context) throws SemanticException {
        if (context.getIntegerValue() != null) {
            System.out.println(context.getIntegerValue().getText());
        } else if (context.getVariableName() != null) {
            System.out.println(this.variableMap.get(context.getVariableName().getText()));
        }
        return super.visitShow(context);
    }
}
