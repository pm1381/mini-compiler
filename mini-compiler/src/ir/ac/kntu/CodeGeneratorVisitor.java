package ir.ac.kntu;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import ir.ac.kntu.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

import static jdk.internal.org.objectweb.asm.Opcodes.*;
//import org.objectweb.asm.MethodVisitor;
//import ir.ac.kntu.Type;

public class CodeGeneratorVisitor extends SimplerLangBaseVisitor<Void> {

    // Class Writer
    private final ClassWriter classWriter;

    // For assigning correct variable index from LET to SHOW.
    private final Map<String, Integer> variableIndexMap;
    private int variableIndex;

    // Main Method visitor used across LET and SHOW.
    private MethodVisitor mainMethodVisitor;

    public CodeGeneratorVisitor() {
        this.classWriter = new ClassWriter(0);
        variableIndexMap = new HashMap<>();

        // Variable0 is reserved for args[] in :  `main(String[] var0)`
        variableIndex = 1;
    }

    /** Called when the program node is visited. The main entry point. */
    @Override
    public Void visitProgram(ProgramContext context) {

        /** ASM = CODE : public class CgSample. */
        // BEGIN 1: creates a ClassWriter for the `CgSample.class` public class,
        classWriter.visit(
                V1_8, // Java 1.8
                ACC_PUBLIC + ACC_SUPER, // public static
                "CgSample", // Class Name
                null, // Generics <T>
                "java/lang/Object", // Interface extends Object (Super Class),
                null // interface names
        );

        /** ASM = CODE : public static void main(String args[]). */
        // BEGIN 2: creates a MethodVisitor for the 'main' method
        mainMethodVisitor =
                classWriter.visitMethod(
                        ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);

        super.visitProgram(context);

        // END 2: Close main()
        mainMethodVisitor.visitEnd();

        // END 1: Close class()
        classWriter.visitEnd();

        byte[] code = classWriter.toByteArray();
        writeToFile(code, "output/CgSample.class");

        return null;
    }

    @Override
    public Void visitLet(LetContext context) {

        /** ASM = BIPUSH : Push bytes. */
        int variableIntegerVal = Integer.parseInt(context.getVariableValue().getText());
        mainMethodVisitor.visitIntInsn(BIPUSH, variableIntegerVal);

        /** ASM = CODE : Integer.valueOf( <BIPUSH_VALUE> ) . */
        mainMethodVisitor.visitMethodInsn(
                INVOKESTATIC,
                Type.getType(Integer.class).getInternalName(),
                "valueOf",
                "(I)Ljava/lang/Integer;",
                false);

        /**
         * ASM = ASTORE : Store reference into local variable.
         *
         * <p>This stores the above valueOf(INT) as Integer Variable.
         */
        // STORE to Variable Pool at variableIndex
        mainMethodVisitor.visitVarInsn(ASTORE, variableIndex);

        // Saving the variableIndex for reference in SHOW()
        variableIndexMap.put(context.getVariableName().getText(), variableIndex);
        variableIndex++;

        return null;
    }

    @Override
    public Void visitShow(ShowContext context) {

        /** ASM = CODE : System.out */
        mainMethodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        if (context.getVariableName() != null) {

            /**
             * ASM = LOAD Variable: ALOAD variable
             *
             * <p>ALOAD: Load reference from local variable
             */
            // Fetch index from VariablePool
            int index = variableIndexMap.get(context.getVariableName().getText());
            // LOAD from variable pool
            mainMethodVisitor.visitVarInsn(ALOAD, index);

            /** ASM = INVOKE: println(Object) with variable loaded via ALOAD. */
            mainMethodVisitor.visitMethodInsn(
                    INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
        } else if (context.getIntegerValue() != null) {

            /** ASM = BIPUSH: Push byte. */

            // Get integer value of the constant.
            int integerVal = Integer.parseInt(context.getIntegerValue().getText());
            // PUSH integerValue
            mainMethodVisitor.visitIntInsn(BIPUSH, integerVal);

            /** ASM = INVOKE: println(I) with variable loaded via ALOAD. */
            mainMethodVisitor.visitMethodInsn(
                    INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        }
        return null;
    }

    /**
     * Writes Byte Array to a file
     *
     * @param code Byte Array of Source code.
     * @param filePath File Path to write. Eg:- "Example.class"
     */
    private void writeToFile(byte[] code, String filePath) {

        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}