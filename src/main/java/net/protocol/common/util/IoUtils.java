package net.protocol.common.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author jinhongw@gmail.com
 */
public class IoUtils {
    public final static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    /**
     * Returns an array of abstract pathnames denoting the files in the
     * directory denoted by this abstract pathname.
     *
     * @param file File or Directory
     * @return An array of File
     */
    public static File[] listFiles(File file) {
        return listFiles(file, null);
    }

    /**
     * Returns an array of abstract pathnames denoting the files and directories
     * in the directory denoted by this abstract pathname that satisfy the
     * specified filter.
     *
     * @param file   File or Directory
     * @param filter the sequence to search for file name filter
     * @return An array of File
     */
    public static File[] listFiles(File file, CharSequence filter) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (filter == null)
                    return files;
                List<File> list = new ArrayList<File>();
                for (File e : files) {
                    if (e.getName().contains(filter)) {
                        list.add(e);
                    }
                }
                return list.toArray(new File[0]);
            }
            if (filter == null)
                return new File[]{file};
            return file.getName().contains(filter) ? new File[]{file} : null;
        }
        return null;
    }

    /**
     * Deletes the file or directory denoted by this abstract pathname.
     *
     * @param path File or Directory
     */
    public static void clear(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File[] list = listFiles(file);
        for (File e : list) {
            e.delete();
        }
    }

    /**
     * Returns the path of the class
     *
     * @param c Instances of the class
     * @return The path of the class
     */
    public static String getPath(Class<?> c) {
        final String name = c.getName().replaceAll("\\.", "/") + ".class";
        final java.net.URL url = c.getClassLoader().getResource(name);
        String path = url.getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);
        return path.contains(":") ? path : path.replaceAll(" ", "%20");
    }

    /**
     * Read all lines from a file.Bytes from the file are decoded into UTF-8
     * using the specified charset.
     *
     * @param file the given src file
     * @return the lines from the file as a {@code Collection}
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    public static Collection<String> readAllLines(File file) throws IOException {
        return readAllLines(file, UTF8_CHARSET);
    }

    /**
     * Read all lines from a file.Bytes from the file are decoded into
     * characters using the specified charset.
     *
     * @param file the given src file
     * @param cs   the charset to use for decoding
     * @return the lines from the file as a {@code Collection}
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    public static Collection<String> readAllLines(File file, Charset cs)
            throws IOException {
		BufferedReader reader = 
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(file), cs));
		return readAllLines(reader);
    }
    
    /**
     * 
     * @param in the given src InputStream
     * @return the lines from the InputStream as a {@code Collection}
     * @throws IOException
     */
    public static Collection<String> readAllLines(InputStream in) throws IOException {
        return readAllLines(in, UTF8_CHARSET);
    }
    
    /**
     * 
     * @param in the given src InputStream
     * @param cs the charset to use for decoding
     * @return the lines from the InputStream as a {@code Collection}
     * @throws IOException
     */
	public static Collection<String> readAllLines(InputStream in, Charset cs)
			throws IOException {
		BufferedReader reader = 
				new BufferedReader(
						new InputStreamReader(in, cs));
		return readAllLines(reader);
	}
    
	private static Collection<String> readAllLines(BufferedReader reader)
			throws IOException {
		Collection<String> list = new ArrayList<String>();
		try {
			for (;;) {
				String line = reader.readLine();
				if (line == null)
					break;
				list.add(line);
			}
		} finally {
			if (reader != null)
				reader.close();
		}
		return list;
	}

    /**
     * Write lines of text to a file.Characters are encoded into bytes using the
     * UTF-8 charset.
     *
     * @param file  the given destination file
     * @param lines an object to iterate over the char sequences
     * @return the file
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    public static File write(File file, Iterable<? extends CharSequence> lines)
            throws IOException {
        return write(file, lines, UTF8_CHARSET);
    }

    /**
     * Write lines of text to a file.Characters are encoded into bytes using the
     * specified charset.
     *
     * @param file  the given destination file
     * @param lines an object to iterate over the char sequences
     * @param cs    the charset to use for encoding
     * @return the file
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    public static File write(File file, Iterable<? extends CharSequence> lines,
                             Charset cs) throws IOException {
        BufferedWriter writer = null;
        try {
            writer =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(file), cs));
            for (CharSequence e : lines) {
                writer.append(e);
                writer.newLine();
            }
        } finally {
            if (writer != null)
                writer.close();
        }
        return file;
    }
}
