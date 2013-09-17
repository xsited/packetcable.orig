package net.protocol.common.tools;

import net.protocol.common.util.IoUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

/**
 * @author jinhongw@gmail.com
 */
public class JCompiler {

    private final static JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();

    /**
     * Compile java file to class.
     *
     * @param java the compile to java file
     * @return the compiled class file
     */
    public static File compile(File java) {
        final String info = "javac compile '" + java.getPath() + "'";
        String[] args = new String[]{
                // "-verbose",
                java.getPath()
        };

        int result = COMPILER.run(null, null, System.err, args);
        if (result == 0) {
            System.out.println(info + " Done.");
            IoUtils.clear(java.getPath());
        } else {
            System.out.println(info + " Fail.");
            return null;
        }
        return getClass(java);
    }


    private static File getClass(File java) {
        // generate\src\net\protocol\diameter\base\avp\SessionId.java
        String path = java.getPath();
        // generate\src\net\protocol\diameter\base\avp\SessionId.class
        return new File(path.substring(0, path.indexOf(".java")) + ".class");
    }
}
