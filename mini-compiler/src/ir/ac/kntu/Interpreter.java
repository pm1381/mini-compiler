package ir.ac.kntu;
////out
import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    /**
     * SimplerLang Interpreter Implementation. Interpreter executes code line by line.
     *
     * <p>NOTE: Here we write the logic for storing the `let` variable and displaying the `show` output.
     */

    private final Map<String, String> variableMap;

    public Interpreter() {
        super();
        variableMap = new HashMap<>();
    }

    public void visitLet(LetContext context) {
        this.variableMap.put(context.getVariableName().getText(), context.getVariableValue().getText());
    }

    public void visitShow(Context context) {
        if (context.getIntegerValue() != 0) {
            System.out.println(context.getIntegerValue());
        } else if (context.getVariableName() != null) {
            System.out.println(this.variableMap.get(context.getVariableName().getText()));
        }
    }
}

