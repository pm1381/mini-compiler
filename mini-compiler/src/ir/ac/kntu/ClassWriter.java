package ir.ac.kntu;

import jdk.internal.org.objectweb.asm.MethodVisitor;

//out
public class ClassWriter {

    int index;

    public ClassWriter(int index) {
        this.index = index;
    }

    public ClassWriter() {
    }

    public void visit(int v18, int i, String cgSample, Object o, String s, Object o1) {
    }

    public MethodVisitor visitMethod(int i, String main, String s, Object o, Object o1) {
        return null;
    }

    public void visitEnd() {
    }

    public byte[] toByteArray() {
        return new byte[0];
    }
}
