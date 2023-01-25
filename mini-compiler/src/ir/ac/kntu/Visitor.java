
package ir.ac.kntu;
import ir.ac.kntu.TerminalNode;
import ir.ac.kntu.LetContext;
import ir.ac.kntu.ProgramContext;
import ir.ac.kntu.ShowContext;
import ir.ac.kntu.StatementContext;

/**
 * Visitor Pattern used for separating Business logic from domain class code.
 *
 * @param <T> Return type of visitXXX. Could be Void, Boolean etc.
 */
public interface Visitor<T> {

    T visitProgram(ProgramContext context);

    T visitStatement(StatementContext context) throws SemanticException;

    T visitLet(LetContext context) throws SemanticException;

    T visitShow(ShowContext context) throws SemanticException;

    T visitTerminal(TerminalNode context);
}

