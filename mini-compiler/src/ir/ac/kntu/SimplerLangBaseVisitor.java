package ir.ac.kntu;

import ir.ac.kntu.ParseTree;
import ir.ac.kntu.TerminalNode;
import ir.ac.kntu.LetContext;
import ir.ac.kntu.ProgramContext;
import ir.ac.kntu.ShowContext;
import ir.ac.kntu.StatementContext;

public class SimplerLangBaseVisitor<T> implements Visitor<T> {

    @Override
    public T visitProgram(ProgramContext context) {
        return visitChildren(context);
    }

    @Override
    public T visitStatement(StatementContext context) throws SemanticException {
        return visitChildren(context);
    }

    @Override
    public T visitLet(LetContext context) throws SemanticException {
        return visitChildren(context);
    }

    @Override
    public T visitShow(ShowContext context) throws SemanticException {
        return visitChildren(context);
    }

    /** There is no child to propagate. */
    @Override
    public T visitTerminal(TerminalNode context) {
        return defaultResult();
    }

    /**
     * Propagate visitor to the children.
     *
     * <p>So when you call invoke something like this
     *
     * <pre>
     *     ParseTree tree = parser.parseProgram();
     *     SimplerLangCustomVisitor visitor = new SimplerLangCustomVisitor();
     *     tree.accept(visitor);
     * </pre>
     *
     * The accept(visitor) `visitor` is propagated to the children of that tree node.
     *
     * @param node Visitable implementation
     */
    public T visitChildren(ParseTree node) {
        T result = defaultResult();
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree c = node.getChild(i);
            result = (T) c.accept((InterpreterVisitor) this);
        }

        return result; // return the last accept result of the children list.
    }

    protected T defaultResult() {
        return null;
    }
}
