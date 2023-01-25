package ir.ac.kntu;

//import org.junit.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) throws IOException {

         // 0. Input Code
         Path fileName
                 = Path.of("G:\\university\\compiler\\prj-3-mahshid\\untitled1\\src\\ir\\ac\\kntu\\test.txt");
        String sourceCode = Files.readString(fileName);
        // 1. Lexer
         Lexer lexer = new Lexer(sourceCode);

         // 2. Parser
         Parser parser = new Parser(lexer);
         ParseTree tree = parser.parseProgram();
//        Assert.assertNotNull(tree);

         // 3. Semantic Analyzer Visitor
         tree.accept(new SemanticAnalyzer());

         // 4.1 Interpreter Visitor
         tree.accept(new InterpreterVisitor());

         // 4.2 Compiler Visitor
         tree.accept(new CodeGeneratorVisitor());

         exit(0);
    }
}
