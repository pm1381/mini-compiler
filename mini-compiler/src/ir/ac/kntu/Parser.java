package ir.ac.kntu;


import java.util.ArrayList;
import java.util.List;

public class Parser {

    private List<Token> tokens;
    private Lexer lexer;
    private int current = 0;

    public Parser(List<Token> allTokens) {
        this.tokens = allTokens;
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public ProgramContext parseProgram() {
        List<StatementContext> statements = new ArrayList<>();
        do {
        statements.add(parseStatement());
        } while (lexer.nextToken());
        return new ProgramContext(statements);
    }

    public StatementContext parseStatement() {
        if (lexer.getCurrentToken() == null) {
        lexer.nextToken(); // Current Token =  LET | SHOW
        }
        Token token = lexer.getCurrentToken(); // LET | SHOW

        if (token.getType() == TokenType.VARIABLE || token.getType() == TokenType.NUMBER) { // LET
            return new StatementContext(parseLet(), null);
        } else {
            throw new ParserException("Not of type LET or SHOW.");
        }
    }

      public LetContext parseLet() {
        if (lexer.getCurrentToken() == null) {
        lexer.nextToken(); // Current Token =  VAR
        }
        TerminalNode variableNameToken = parseTerminalNode(); // VAR

        lexer.nextToken(); // move to : =
        lexer.nextToken(); // move to : INT

        TerminalNode valueToken = parseTerminalNode(); // INT

        return new LetContext(variableNameToken, valueToken);
    }

  /** Parse Logic for Show. */
  public ShowContext parseShow() {

    if (lexer.getCurrentToken() == null) {
      lexer.nextToken(); // Current Token =  SHOW
    }

    lexer.nextToken(); // Current Token = VAR | INT

    TerminalNode terminal = parseTerminalNode(); // VAR | INT
    final Token token = (Token) terminal.getPayload();

    if (token.getType() == TokenType.NUMBER) {
      return new ShowContext(terminal, null);
    } else if (token.getType() == TokenType.VARIABLE) {
      return new ShowContext(null, terminal);
    } else {
      throw new ParserException("Show not preceded with var or int");
    }
  }

  /** Parse Logic for Terminal Node. */
  public TerminalNode parseTerminalNode() {

    if (lexer.getCurrentToken() == null) {
      lexer.nextToken(); // Current Token =  VAR | INT
    }

    TerminalNode token = new TerminalNode();
    return token;
  }

}