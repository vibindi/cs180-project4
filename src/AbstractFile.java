import java.io.*;
import java.util.*;

/**
 * Teacher Class
 * class to read abstract files
 *
 * @author Ashvin
 * @version 1
 */
public abstract class AbstractFile {

    protected String path;

    public AbstractFile(String path) {
        this.path = path;
    }

    public void reset() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path, false));
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error Occurred");
        }
    }

    public ArrayList<String> readFile() {
        ArrayList<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            System.out.println("Line does not exist.");
        }
        return list;
    }
}