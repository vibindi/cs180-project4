import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Teacher Class
 * class to read uploaded files
 *
 * @author Noah
 * @version 1
 */
public class UploadFile {
    public static String[] uploadStudentPosts(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);

            String title = bfr.readLine();
            String content = bfr.readLine();

            if (title == null || content == null) {
                return null;
            }

            bfr.close();

            return new String[]{title, content};
        } catch (IOException e) {
            System.out.println("File not Formatted Properly");
            return null;
        }
    }

    public static String uploadTeacherForum(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);

            String title = bfr.readLine();

            if (title == null) {
                return null;
            }

            bfr.close();

            return title;
        } catch (IOException e) {
            System.out.println("File not Formatted Properly");
            return null;
        }
    }
}
