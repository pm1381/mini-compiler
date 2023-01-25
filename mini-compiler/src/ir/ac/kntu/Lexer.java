package ir.ac.kntu;

//import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class Lexer {


    private final String code;
    private final int codeLength;

    private boolean isEndOfCode=false;

    private int currentIndex;

    private Token currentToken;
    private Token previousToken;
    private List<Token> allTokens;

    private String currentNumber,currentVariable;

    public Lexer(String code) {
        this.code = code;
        this.currentIndex = 0;
        this.codeLength = code.length();
    }

    public List<Token> getAllTokens() {
        return this.allTokens;
    }

    public void setTokens(Token token) {

        this.allTokens.add(token);
    }

    /**
     * Updates currentToken to the next valid Token if it is available.
     *
     * @return true, if a valid token is available next.
     */
    public boolean nextToken() {

        while (!isEndOfCode) { // while loop is to fetch nextToken, if a skipWS occurs.

            previousToken = currentToken; // in case you need the previous token
            char currentChar;

            try {
                currentChar = code.charAt(currentIndex);
            } catch (StringIndexOutOfBoundsException e) {
                return false;
            }

            if (Arrays.asList(' ', '\r', '\t', '\n').contains(currentChar)) { // 1. WS
                skipWhiteSpace();
                continue;
            } else if (currentChar == '=') { // 2. equal
                currentToken = new Token(TokenType.EQUALS_OPERATOR);
//                currentIndex++;
            } else  if (currentChar == '$') {
                return  false;
            } else  if (currentChar == ';') {
                continue;
            } else if (Character.isDigit(currentChar)) { // 3. INT
                currentToken = new Token(TokenType.NUMBER, readNumber());
            } else if (Character.isLetter(currentChar)) {
                String variableName = readVariable();
                if (currentChar == 'P') { // 4. SHOW
                    // variableName!= null && variableName.equalsIgnoreCase("show")
                    currentToken = new Token(TokenType.SHOW);
                } else { // 5. VAR
                    currentToken = new Token(TokenType.VARIABLE, variableName);
                }
            } else {
                try {
                    throw new LexerException("Token not defined.");
                } catch (LexerException e) {
                    e.printStackTrace();
                }
            }
            currentIndex++;
            return true;
        }
        return false;
    }

    /**
     * Read Integer as String
     *
     * @return String value of Integer Number.
     */
    private String readNumber() {
      return currentNumber;
    }

    /**
     * @return String read from current index.
     */
    private String readVariable() {
     return currentVariable;
    }

    /**
     * Skip WhiteSpace(WS)
     */
    private void skipWhiteSpace() {
    }

    /**
     * Get current Token.
     */
    public Token getCurrentToken() {
        return currentToken;
    }

}
