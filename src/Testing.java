import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Testing Class
 * Test Cases for all of the methods
 *
 * @author Ashvin
 * @version 1
 */
public class Testing {
    public static void main(String[] args) {
        System.out.println("Starting Tests");
        accountTests();
        teacherTests();
        studentTests();
        fileImportTests();
        votingTests();
        emojiTests();
        UpdateFiles.wipeFiles();
    }

    private static void accountTests() {
        System.out.println("\nStarting Account Tests:");
        String ans;

        System.out.println("\nCreate Login Tests\n");
        //Test Case 1
        System.out.print("Working creating teacher: ");
        LoginChecker.createLogin("teacher", "A", "B");
        LoginChecker.createLogin("teacher", "C", "D");
        ans = "A,B,0\nC,D,1\n";
        compareToFile("storage/teachers.txt", ans);

        //Test Case 2
        System.out.print("Working creating student: ");
        LoginChecker.createLogin("student", "E", "F");
        LoginChecker.createLogin("student", "G", "H");
        ans = "E,F,0\nG,H,1\n";
        compareToFile("storage/students.txt", ans);

        //Test Case 3
        System.out.print("Preventing duplicate usernames on creation: ");
        LoginChecker.createLogin("teacher", "A", "B");
        LoginChecker.createLogin("teacher", "E", "F");
        ans = "A,B,0\nC,D,1\n";
        compareToFile("storage/teachers.txt", ans);

        System.out.println("\nVerify Login Tests\n");

        //Test Case 1
        System.out.print("Allow for Proper Login: ");
        ans = "Teacher|Username: A|Password: B";
        compareToObject(LoginChecker.verifyLogin("A", "B"), false, ans);

        //Test Case 2
        System.out.print("Deny improper Login: ");
        ans = "T";
        compareToObject(LoginChecker.verifyLogin("A", "C"), true, ans);

        System.out.println("\nDelete Login Tests\n");

        //Test Case 1
        System.out.print("Deleting user if they exist: ");
        LoginChecker.deleteLogin("A");
        ans = "C,D,1\n";
        compareToFile("storage/teachers.txt", ans);

        //Test Case 2
        System.out.print("Doesn't delete anything if username doesn't exist: ");
        LoginChecker.deleteLogin("Z");
        ans = "C,D,1\n";
        compareToFile("storage/teachers.txt", ans);

        System.out.println("\nChange Username Tests\n");

        //Test Case 1
        System.out.print("Changing username if user exists: ");
        LoginChecker.changeUsername("Teacher", 1, "E");
        ans = "E,D,1\n";
        compareToFile("storage/teachers.txt", ans);

        //Test Case 2
        System.out.print("Doesn't change anything if user doesn't exist: ");
        LoginChecker.changeUsername("Teacher", 2, "Z");
        ans = "E,D,1\n";
        compareToFile("storage/teachers.txt", ans);

        System.out.println("\nChange Password Tests\n");
        //Test Case 1
        System.out.print("Changing password if user exists: ");
        LoginChecker.changePassword("E", "F");
        ans = "E,F,1\n";
        compareToFile("storage/teachers.txt", ans);

        //Test Case 2
        System.out.print("Doesn't change password if username doesn't exist: ");
        LoginChecker.changePassword("A", "B");
        ans = "E,F,1\n";
        compareToFile("storage/teachers.txt", ans);

        System.out.println("\nEnding Account Tests");
    }

    private static void teacherTests() {
        System.out.println("\nStarting Teacher Tests:");
        String ans;
        Teacher teacher = (Teacher) LoginChecker.createLogin("Teacher", "A", "B");

        System.out.println("\nCourse Tests\n");

        //Test 1
        System.out.print("Creating Courses: ");
        assert teacher != null;
        teacher.createCourse("A");
        teacher.createCourse("B");
        ans = "A,0,2\nB,1,2\n";
        compareToFile("storage/courses.txt", ans);

        //Test 2
        System.out.print("Deleting Courses: ");
        teacher.deleteCourse(1);
        ans = "A,0,2\n";
        compareToFile("storage/courses.txt", ans);

        //Test 3
        System.out.print("Editing Course Name: ");
        teacher.editCourse(0, "C");
        ans = "C,0,2\n";
        compareToFile("storage/courses.txt", ans);

        System.out.println("\nForum Tests\n");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date().getTime());
        Course course = teacher.getCourses().get(0);
        //Test 1
        System.out.print("Creating Forum: ");
        course.createForum("A");
        course.createForum("B");
        ans = "A,0,0," + timeStamp + "\nB,1,0," + timeStamp + "\n";
        compareToFile("storage/forums.txt", ans);

        //Test 2
        System.out.print("Deleting Forum: ");
        course.deleteForum(1);
        ans = "A,0,0," + timeStamp + "\n";
        compareToFile("storage/forums.txt", ans);

        //Test 3
        System.out.print("Editing Forum Name: ");
        course.editForum(0, "C");
        ans = "C,0,0," + timeStamp + "\n";
        compareToFile("storage/forums.txt", ans);

        System.out.println("\nEnding Teacher Tests");
    }

    private static void studentTests() {
        System.out.println("\nStarting Student Tests");
        String ans;
        Student student = (Student) LoginChecker.createLogin("student", "I", "J");
        Student student1 = Driver.students.get(0);


        System.out.println("\nCourse Tests\n");

        //Test 1
        System.out.print("Adding Student to Course: ");
        Course course = Driver.courses.get(0);
        assert student != null;
        course.enrollCourse(student.getStudentId());
        course.enrollCourse(student1.getStudentId());
        ans = "C,0,2,2,0\n";
        compareToFile("storage/courses.txt", ans);

        System.out.println("\nForum Tests\n");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date().getTime());

        //Test 1
        System.out.print("Posting to Forum: ");
        Forum forum = course.getForums().get(0);
        forum.makePost("A", "B", student.getStudentId());
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,0\n";
        compareToFile("storage/posts.txt", ans);

        System.out.println("\nComment Tests\n");

        //Test 1
        System.out.print("Commenting to Post: ");
        Post post = forum.getPosts().get(0);
        post.createComment("A", student1);
        ans = "A,0,S0,0," + timeStamp + "\n";
        compareToFile("storage/comments.txt", ans);

        System.out.println("\nEnding Student Tests");
    }

    private static void fileImportTests() {
        System.out.println("\nStarting File Import Tests");

        System.out.println("\nImporting Post Tests\n");

        //Test1
        System.out.print("Valid File Import Post: ");
        String[] vals = UploadFile.uploadStudentPosts("testing/validpost.txt");
        compareToObject(vals, false, "");

        //Test2
        System.out.print("Invalid File Import Post: ");
        vals = UploadFile.uploadStudentPosts("testing/invalidpost.txt");
        compareToObject(vals, true, "");

        System.out.println("\nImporting Forum Tests\n");

        //Test1
        System.out.print("Valid File Import Forum: ");
        String val = UploadFile.uploadTeacherForum("testing/validforum.txt");
        compareToObject(val, false, "Forum");

        //Test2
        System.out.print("Invalid File Import Forum: ");
        val = UploadFile.uploadTeacherForum("testing/invalidforum.txt");
        compareToObject(val, true, "");
        System.out.println("\nEnding File Import Tests");
    }

    private static void votingTests() {
        System.out.println("\nStarting Voting Tests");
        String ans;
        Post post = Driver.posts.get(0);
        Student student = Driver.students.get(0);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date().getTime());

        //Test 1
        post.getVotes().addVote(student);
        System.out.print("Adding Vote: ");
        post.getVotes().addVote(student);
        ans = "A,B,0,2,0," + timeStamp + ",0,Emoji,0\n";
        compareToFile("storage/posts.txt", ans);

        //Test 2
        System.out.print("Can't Repeat Vote: ");
        post.getVotes().addVote(student);
        ans = "A,B,0,2,0," + timeStamp + ",0,Emoji,0\n";
        compareToFile("storage/posts.txt", ans);

        //Test 3
        System.out.print("Remove Vote: ");
        post.getVotes().removeVote(student);
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,0\n";
        compareToFile("storage/posts.txt", ans);

        //Test 4
        System.out.print("Can't Remove Unvoted Vote: ");
        post.getVotes().removeVote(student);
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,0\n";
        compareToFile("storage/posts.txt", ans);

        System.out.println("\nEnding Voting Tests");
    }

    private static void emojiTests() {
        System.out.println("\nStarting Emoji Tests");
        String ans;
        Post post = Driver.posts.get(0);
        Student student = Driver.students.get(0);
        Student student1 = Driver.students.get(2);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date().getTime());

        //Test 1
        System.out.print("Adding New Emoji: ");
        post.addEmoji(":)", student);
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,1,Emoji,:),0\n";
        compareToFile("storage/posts.txt", ans);

        //Test 2
        System.out.print("Same Emoji, new Vote: ");
        post.addEmoji(":)", student1);
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,1,Emoji,:),2,0\n";
        compareToFile("storage/posts.txt", ans);

        //Test 3
        System.out.print("Remove Emoji Vote: ");
        post.removeEmoji(":)", student);
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,1,Emoji,:),2\n";
        compareToFile("storage/posts.txt", ans);

        //Test 4
        System.out.print("Can't Remove Emoji Vote: ");
        post.removeEmoji(":)", student);
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,1,Emoji,:),2\n";
        compareToFile("storage/posts.txt", ans);

        //Test 5
        System.out.print("Delete Emoji: ");
        post.removeEmoji(":)", student1);
        ans = "A,B,0,2,0," + timeStamp + ",Emoji,1,Emoji,:)\n";
        compareToFile("storage/posts.txt", ans);
        System.out.println("\nEnding Voting Tests");
    }

    private static void compareToFile(String file, String answer) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            String val = "";
            while (line != null) {
                val += line + "\n";
                line = br.readLine();
            }
            if (val.equals(answer)) {
                System.out.println("\u001B[32mCorrect\u001B[0m");
            } else {
                System.out.println("\u001B[31mIncorrect\u001B[0m");
                System.out.println("Expected - " + answer);
                System.out.println("Received - " + val);
            }
        } catch (IOException e) {
            System.out.println("Error Reading File: " + file);
        }
    }

    private static void compareToObject(Object object, boolean isNull, String answer) {
        if (isNull) {
            if (object == null) {
                System.out.println("\u001B[32mCorrect\u001B[0m");
            } else {
                System.out.println("\u001B[31mIncorrect\u001B[0m");
                System.out.println("Expected - null");
                System.out.println("Received - " + object);
            }
        } else {
            if (object == null) {
                System.out.println("\u001B[31mIncorrect\u001B[0m");
                System.out.println("Expected - " + answer);
                System.out.println("Received - null");
            } else {
                if (answer.equals("") || object.toString().equals(answer)) {
                    System.out.println("\u001B[32mCorrect\u001B[0m");
                } else {
                    System.out.println("\u001B[31mIncorrect\u001B[0m");
                    System.out.println("Expected - " + answer);
                    System.out.println("Received - " + object);
                }
            }
        }
    }
}