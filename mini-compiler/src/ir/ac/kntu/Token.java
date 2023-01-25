package ir.ac.kntu;

public class Token {

    String variableName;
    TokenType type;

    public Token(TokenType type) {
    }

    public Token() {
    }

    public Token(TokenType type,String variableName) {
        this.variableName = variableName;
        this.type = type;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getValue() {
        return this.variableName;
    }
}
