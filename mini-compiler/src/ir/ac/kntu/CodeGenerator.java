package ir.ac.kntu;
//out
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {


    // Class Writer
    private ClassWriter classWriter;

    // For assigning correct variable index from LET to SHOW.
    private Map<String, Integer> variableIndexMap;
    private int variableIndex;

    public CodeGenerator() {
        this.classWriter = new ClassWriter(0);
        variableIndexMap = new HashMap<>();

        // Variable0 is reserved for args[] in :  `main(String[] var0)`
        variableIndex = 1;
    }
}
