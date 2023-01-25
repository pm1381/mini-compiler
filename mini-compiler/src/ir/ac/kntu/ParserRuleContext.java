package ir.ac.kntu;


import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation of Type of semantics in our Grammar.
 */
public class ParserRuleContext implements ParseTree {

    public ParseTree parent;

    public List<ParseTree> children;

    @Override
    public ParseTree getParent() {
        return parent;
    }

    @Override
    public void setParent(ParseTree parent) {
    }

    @Override
    public String getText() {
        return parent.getText();
    }

    @Override
    public Object getPayload() {
        return this;
    }

    public void addChild(ParseTree child) {
        child.setParent(this);
        if (children == null) children = new ArrayList<>();
        children.add(child);
    }

    @Override
    public ParseTree getChild(int i) {
        return this.children.get(i);
    }

    @Override
    public int getChildCount() {
        return children != null ? children.size() : 0;
    }

    @Override
    public String toStringTree() {
        return ParseTree.class.toString();
    }

    @Override
    public <T> T accept(InterpreterVisitor tSimplerLangBaseVisitor) {
        return null;
    }

}
