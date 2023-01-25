package ir.ac.kntu;


public class TerminalNode implements ParseTree{
    @Override
    public ParseTree getParent() {
        return null;
    }

    @Override
    public void setParent(ParseTree parent) {

    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public ParseTree getChild(int i) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public String toStringTree() {
        return null;
    }

    @Override
    public <T> T accept(InterpreterVisitor tSimplerLangBaseVisitor) {
        return null;
    }
}
