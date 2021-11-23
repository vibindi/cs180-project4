import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/*
 * Driver Class
 * main method that takes scanner inputs from users
 * @version 1
 * @author Shreya
 */
public class Driver {

    public static HashMap<Integer, Teacher> teachers = new HashMap<>();
    public static HashMap<Integer, Student> students = new HashMap<>();
    public static HashMap<Integer, Course> courses = new HashMap<>();
    public static HashMap<Integer, Forum> forums = new HashMap<>();
    public static HashMap<Integer, Post> posts = new HashMap<>();
    public static HashMap<Integer, Comment> comments = new HashMap<>();

    public static void main(String[] args) {
        // MAIN DRIVER CLASS
        // ALL Scanner input and system output to be done here
        // ALL menu prompts
        UpdateFiles.readFiles();
        String exitMessage = "Thanks for using the discussion board!";
        /*
        Instantiating all the variables
        */
        boolean cont = true;
        Scanner scan = new Scanner(System.in);
        String name = "";
        String lastName = "";
        String user = "";
        String password = "";
        String password2 = "";
        String userType = "";
        System.out.println("Welcome to the discussion board");
        while (cont) {
        /*
        Resetting the booleans at the beginning of each loop so that everything runs again
        */
            boolean passwordCheck = true;
            boolean contAgain = true;
            boolean userCheck = true;
            boolean teacherDashboard = true;
            boolean studentDashboard = true;
            boolean enterCourse = true;
            boolean moreComment = true;

            System.out.println("Do you want to create an account or log in (create/login/exit)");
            String firstChoice = scan.nextLine();
            if (firstChoice.equalsIgnoreCase("create")) {
                System.out.println("Enter your first name");
                name = scan.nextLine();
                System.out.println("Enter your last name");
                lastName = scan.nextLine();
                //creating a username
                System.out.println("Choose a username");
                user = scan.nextLine();
                //creating a password
                while (passwordCheck) {
                    System.out.println("Choose a password");
                    password = scan.nextLine();
                    System.out.println("Re-enter your password");
                    password2 = scan.nextLine();
                    if (!password.equals(password2)) {
                        System.out.println("Please try again - passwords don't match");
                    } else {
                        passwordCheck = false;
                    }
                }
                System.out.println("Are you a teacher or a student?(teacher/student)");
                userType = scan.nextLine();
                //creating instances of either a teacher or a student
                User user1 = LoginChecker.createLogin(userType, user, password);
                while (user1 == null) {
                    System.out.println("Sorry there was an error -- ensure you selected teacher or student");
                    System.out.println(("If you did, please choose a new username!"));
                    user = scan.nextLine();
                    System.out.println("Are you a teacher or a student?(teacher/student)");
                    userType = scan.nextLine();
                    user1 = LoginChecker.createLogin(userType, user, password);
                }

                while (userCheck) {
                    if (userType.equalsIgnoreCase("Teacher")) {
                        Teacher teacher = (Teacher) user1;
                        while (contAgain) {
                            teacherDashboard = true;

                            //the looping menu for what a teacher can do
                            enterCourse = true;
                            teacherMenu(user);
                            int choice = 0;
                            if (scan.hasNextInt()) {
                                choice = scan.nextInt();
                                scan.nextLine();
                            } else {
                                while (!scan.hasNextInt()) {
                                    System.out.println("Not a number!");
                                }
                                choice = scan.nextInt();
                                scan.nextLine();
                            }

                            if (choice == 1) {
                                System.out.println(exitMessage);
                                //cont = true;
                                contAgain = false;
                                userCheck = false;
                            }
                            if (choice == 2) {
                                //edit or delete account
                                System.out.println("Do you want to edit or delete your account?(edit/delete)");
                                String modify = scan.nextLine();
                                if (modify.equalsIgnoreCase("edit")) {
                                    System.out.println("Do you want to change your password or your username?(password/username)");
                                    System.out.println("If neither, please type exit");
                                    String edit = scan.nextLine();
                                    if (edit.equalsIgnoreCase("password")) {
                                        //changing password
                                        for (int i = 0; i < 3; i++) {
                                            System.out.println("Enter in current password");
                                            String passwordChecker = scan.nextLine();
                                            if (!password.equals(passwordChecker)) {
                                                System.out.println("Error - invalid password");
                                                System.out.println("You have " + (2 - i) + " more attempts");
                                                if (i == 2) {
                                                    System.out.println("Password attempts failed -- logging out now");
                                                    contAgain = false;
                                                    userCheck = false;
                                                    passwordCheck = false;
                                                    continue;
                                                }
                                            }
                                            if (passwordChecker.equals(password)) {
                                                i = 3;
                                                passwordCheck = true;
                                            }
                                        }

                                        while (passwordCheck) {
                                            System.out.println("Choose a new password");
                                            password = scan.nextLine();
                                            System.out.println("Re-enter your password");
                                            password2 = scan.nextLine();
                                            if (!password.equals(password2)) {
                                                System.out.println("Please try again - passwords don't match");
                                            } else {
                                                passwordCheck = false;
                                            }
                                        }
                                        LoginChecker.changePassword(user, password);
                                    } else if (edit.equalsIgnoreCase("username")) {
                                        //changing username
                                        System.out.println("Enter your new username");
                                        String newUser = scan.nextLine();
                                        teacher.setUsername(newUser);
                                        //System.out.println(teacher.getUsername());
                                        LoginChecker.changeUsername("Teacher", teacher.getTeacherId(), newUser);
                                    } else {
                                        System.out.println("Returning back to log-in menu");
                                        contAgain = false;
                                        userCheck = false;
                                    }

                                } else if (modify.equalsIgnoreCase("delete")) {
                                    //deleting account
                                    for (int i = 0; i < 3; i++) {
                                        System.out.println("Enter in current password");
                                        String passwordChecker = scan.nextLine();
                                        if (!password.equals(passwordChecker)) {
                                            System.out.println("Error - invalid password");
                                            System.out.println("You have " + (2 - i) + " more attempts");
                                            if (i == 2) {
                                                System.out.println("Password attempts failed -- logging out now");
                                                contAgain = false;
                                                userCheck = false;
                                            }
                                        }
                                        if (passwordChecker.equals(password)) {
                                            LoginChecker.deleteLogin(user);
                                            contAgain = false;
                                            userCheck = false;
                                            i = 3;

                                        }
                                    }
                                } else {
                                    System.out.println("Error invalid input - please try again");
                                }
                            }
                            if (choice == 3) {
                                //creating a new course
                                while (enterCourse) {
                                    System.out.println("Enter your new course name");
                                    String newCourseName = scan.nextLine();
                                    teacher.createCourse(newCourseName);
                                    System.out.println("Do you want to make another course(Y/N)");
                                    String answer = scan.nextLine();
                                    if (answer.equalsIgnoreCase("N")) {
                                        enterCourse = false;
                                    }
                                }
                            }
                            if (choice == 4) {
                                //showing posts and teacher dashboard
                                while (teacherDashboard) {
//

                                    ArrayList<Course> getTeacherCourses = teacher.getCourses();
                                    if (getTeacherCourses.size() == 0) {
                                        System.out.println("Sorry there are no courses to see, you must create a course");
                                        teacherDashboard = false;
                                        continue;

                                    }
                                    System.out.println("What course do you wish to view?");
                                    //System.out.println(teacher.getCourses());
                                    for (int i = 0; i < getTeacherCourses.size(); i++) {
                                        System.out.println((i + 1) + ". " + getTeacherCourses.get(i).getName());
                                    }
                                    System.out.println("Please choose a course(type in a number)");
                                    int courseNum = 0;
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }

                                    while (courseNum > getTeacherCourses.size()) {
                                        System.out.println("Sorry there was an error, try again");
                                        for (int i = 0; i < getTeacherCourses.size(); i++) {
                                            System.out.println((i + 1) + ". " + getTeacherCourses.get(i).getName());
                                        }
                                        System.out.println("Please choose a course(type in a number)");
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    //choosing a course so that teacher can edit it
                                    Course course = getTeacherCourses.get(courseNum - 1);
                                    System.out.println("You selected: " + course);
                                    int courseId = course.getCourseId();
                                    //teacher.editCourse(courseId, course.getName());
                                    System.out.println("Do you want to \n * (1)change a course Name" +
                                            "\n * (2)create a new discussion forum" +
                                            "\n * (3)view an existing forum" +
                                            "\n * (4)edit an existing forum" +
                                            "\n * (5)log out");
                                    int choices = 0;
                                    if (scan.hasNextInt()) {
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    if (choices == 1) {
                                        //create a new course
                                        System.out.println("What do you want the new course name to be?");
                                        String courseName = scan.nextLine();
                                        teacher.editCourse(courseId, courseName);

                                    } else if (choices == 2) {
                                        //create a new discussion forum
                                        boolean newForums = true;
                                        while (newForums) {
                                            System.out.println();
                                            System.out.println("Do you want to import a forum (Y/N)");
                                            String importForum = scan.nextLine();
                                            if (importForum.equalsIgnoreCase("Y")) {
                                                System.out.println("Enter file path location");
                                                String forum = UploadFile.uploadTeacherForum(scan.nextLine());
                                                while (forum == null) {
                                                    System.out.println("Enter file path location");
                                                    forum = UploadFile.uploadTeacherForum(scan.nextLine());
                                                }
                                                course.createForum(forum);
                                            } else {
                                                System.out.println("What do you want the topic of this forum to be?");
                                                String forum = scan.nextLine();
                                                course.createForum(forum);
                                            }
                                            System.out.println("Discussion board created!");
                                            System.out.println("Do you want to make another discussion forum?(Y/N)");
                                            String moreForums = scan.nextLine();
                                            if (moreForums.equalsIgnoreCase("N")) {
                                                newForums = false;
                                                teacherDashboard = false;
                                                //userCheck = false;
                                            }
                                        }
                                    } else if (choices == 3) {
                                        //view an existing forum
                                        //print posts or see the dashboard
                                        //view dashboard - ascending or descending by votes
                                        System.out.println("Do you want to view the teacher dashboard or view all posts?(dashboard/posts)");
                                        String dashboard = scan.nextLine();
                                        if (dashboard.equalsIgnoreCase("dashboard")) {
                                            System.out.println("Which discussion forum do you wish to see?");
                                            //choose what discussion forum the teacher wants to see
                                            ArrayList<Forum> forums = course.getForums();
                                            if (forums.size() == 0) {
                                                System.out.println("No forums!");
                                                System.out.println("Exiting...");
                                                teacherDashboard = false;
                                                continue;
                                            }
                                            for (int i = 0; i < forums.size(); i++) {
                                                System.out.println((i + 1) + ". " + forums.get(i).getTopic());
                                            }
                                            System.out.println("Choose a forum(enter a number)");
                                            int forumNum = 0;
                                            if (scan.hasNextInt()) {
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (forumNum > forums.size() || forumNum < 0) {
                                                System.out.println("Sorry there was an error, try again");
                                                for (int i = 0; i < forums.size(); i++) {
                                                    System.out.println((i + 1) + ". " + forums.get(i));
                                                }

                                                System.out.println("Choose a forum(enter a number)");
                                                forumNum = 0;
                                                if (scan.hasNextInt()) {
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                } else {
                                                    while (!scan.hasNextInt()) {
                                                        System.out.println("Not a number!");
                                                    }
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                }
                                            }
                                            Forum forum = forums.get(forumNum - 1);
                                            int forumId = forum.getForumId();
                                            //choosing the order of the teacher dashboard
                                            System.out.println("How do you want to view the dashboard?" +
                                                    "\n * (1)Default" +
                                                    "\n * (2)Ascending" +
                                                    "\n * (3)Descending ");
                                            int sort = 0;
                                            if (scan.hasNextInt()) {
                                                sort = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                sort = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (sort != 1 && sort != 2 && sort != 3) {
                                                System.out.println("How do you want to view the dashboard?" +
                                                        "\n * (1)Default" +
                                                        "\n * (2)Ascending" +
                                                        "\n * (3)Descending ");
                                                sort = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            teacher.teacherDashboard(sort, forumId);

                                        } else if (dashboard.equalsIgnoreCase("posts")) {
                                            //viewing all of the posts in a forum(unorganized)
                                            System.out.println("Which discussion forum do you wish to see?");
                                            ArrayList<Forum> forums1 = course.getForums();
                                            if (forums1.size() == 0) {
                                                System.out.println("No forums!");
                                                System.out.println("Exiting...");
                                                teacherDashboard = false;
                                                continue;
                                            }
                                            for (int i = 0; i < forums1.size(); i++) {
                                                System.out.println((i + 1) + ". " + forums1.get(i).getTopic() +
                                                        " " + forums1.get(i).getTimeStamp());
                                            }
                                            System.out.println("Choose a forum(enter a number)");
                                            int forumNum = 0;
                                            if (scan.hasNextInt()) {
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (forumNum > forums1.size() || forumNum < 0) {
                                                System.out.println("Sorry there was an error, try again");
                                                for (int i = 0; i < forums1.size(); i++) {
                                                    System.out.println((i + 1) + ". " + forums1.get(i));
                                                }

                                                System.out.println("Choose a forum(enter a number)");
                                                forumNum = 0;
                                                if (scan.hasNextInt()) {
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                } else {
                                                    while (!scan.hasNextInt()) {
                                                        System.out.println("Not a number!");
                                                    }
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                }
                                            }
                                            Forum forum = forums1.get(forumNum - 1);
                                            ArrayList<Post> postsforum = forum.getPosts();
                                            for (int x = 0; x < postsforum.size(); x++) {
                                                System.out.print(postsforum.get(x).getTitle() + " " + postsforum.get(x).getContent() +
                                                        " " + postsforum.get(x).getTimeStamp());
                                                for (Comment comment : postsforum.get(x).getComments()) {
                                                    System.out.print(comment.getContent() + comment.getTimeStamp());
                                                }
                                                System.out.println();
                                            }
                                            System.out.println("Do you want to add a comment?(Y/N)");
                                            String addCom = scan.nextLine();
                                            if (addCom.equalsIgnoreCase("Y")) {
                                                //add comment to a post
                                                do {
                                                    if (postsforum.size() == 0) {
                                                        System.out.println("Sorry nothing to comment on");
                                                        moreComment = false;
                                                        teacherDashboard = false;
                                                        continue;
                                                    }
                                                    for (int i = 0; i < postsforum.size(); i++) {
                                                        System.out.println((i + 1) + ". " + postsforum.get(i).getContent());
                                                    }
                                                    System.out.println("Select a post by number");
                                                    int postNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        postNum = scan.nextInt();
                                                        scan.nextLine();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Not a number!");
                                                        }
                                                        postNum = scan.nextInt();
                                                        scan.nextLine();
                                                    }
                                                    while (postNum < 0 || postNum > postsforum.size() + 1) {
                                                        System.out.println("Invalid input - try again");
                                                        System.out.println("Select a post by number");
                                                        postNum = scan.nextInt();
                                                        scan.nextLine();
                                                    }
                                                    Post post = postsforum.get(postNum - 1);
                                                    System.out.println(post.getContent());
                                                    System.out.println("Enter your comment");
                                                    String comment = scan.nextLine();
                                                    post.createComment(comment, teacher);
                                                    System.out.println("Do you want to add another comment(Y/N)");
                                                    String YN = scan.nextLine();
                                                    if (YN.equalsIgnoreCase("N")) {
                                                        moreComment = false;
                                                    }

                                                } while (moreComment == true);

                                                System.out.println("Do you want to edit or delete a comment?(edit/delete/neither)");

                                                String editDeleteComment = scan.nextLine();
                                                if (editDeleteComment.equalsIgnoreCase("edit")) {
                                                    //editing a students comment
                                                    int count = 1;
                                                    for (int x = 0; x < postsforum.size(); x++) {
                                                        System.out.print(x + ". " + postsforum.get(x).getTitle() + " " + postsforum.get(x).getContent() +
                                                                " " + postsforum.get(x).getTimeStamp());
                                                        for (Comment comment : postsforum.get(x).getComments()) {
                                                            System.out.print(count + ". " + comment.getContent() + comment.getTimeStamp());
                                                        }
                                                    }
                                                    System.out.println("Choose a post");
                                                    int postNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        postNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            postNum = scan.nextInt();
                                                        }
                                                    }
                                                    Post post = posts.get(postNum - 1);
                                                    for (int k = 0; k < post.getComments().size(); k++) {
                                                        System.out.println(k + ". " +
                                                                post.getComments().get(k).getContent());
                                                    }
                                                    System.out.println("Choose a comment");
                                                    int commentNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        commentNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            commentNum = scan.nextInt();
                                                        }
                                                    }
                                                    System.out.println(post.getComments().get(commentNum - 1).getContent());
                                                    int commentId = post.getComments().get(commentNum - 1).getId();
                                                    System.out.println("Type in your updated comment");
                                                    String newComment = scan.nextLine();
                                                    post.editComment(commentId, newComment);
                                                    System.out.println("Edited!");

                                                } else if (editDeleteComment.equalsIgnoreCase("delete")) {
                                                    //deleting a students comments
                                                    int count = 1;
                                                    for (int x = 0; x < postsforum.size(); x++) {
                                                        System.out.print(x + ". " + postsforum.get(x).getTitle() + " " + postsforum.get(x).getContent() +
                                                                " " + postsforum.get(x).getTimeStamp());
                                                        for (Comment comment : postsforum.get(x).getComments()) {
                                                            System.out.print(count + ". " + comment.getContent() + comment.getTimeStamp());
                                                        }
                                                    }
                                                    System.out.println("Choose a post");
                                                    int postNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        postNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            postNum = scan.nextInt();
                                                        }
                                                    }
                                                    Post post = posts.get(postNum - 1);
                                                    for (int k = 0; k < post.getComments().size(); k++) {
                                                        System.out.println(k + ". " +
                                                                post.getComments().get(k).getContent());
                                                    }
                                                    System.out.println("Choose a comment");
                                                    int commentNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        commentNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            commentNum = scan.nextInt();
                                                        }
                                                    }
                                                    System.out.println(post.getComments().get(commentNum - 1).getContent());
                                                    int commentId = post.getComments().get(commentNum - 1).getId();
                                                    post.deleteComment(commentId);
                                                    System.out.println("Deleted!");
                                                } else {
                                                    continue;
                                                }

                                            }

                                        } else {
                                            System.out.println("Invalid Input - try again");
                                            teacherDashboard = false;
                                            continue;
                                        }


                                    } else if (choices == 4) {
                                        //edit or delete a forum
                                        System.out.println("Which discussion forum do you wish to change?");
                                        if (forums.size() == 0) {
                                            System.out.println("No forums!");
                                            System.out.println("Exiting...");
                                            teacherDashboard = false;
                                            continue;
                                        }
                                        ArrayList<Forum> forums1 = course.getForums();
                                        if (forums.size() == 0) {
                                            System.out.println("No forums!");
                                            System.out.println("Exiting...");
                                            teacherDashboard = false;
                                            continue;
                                        }
                                        for (int i = 0; i < forums1.size(); i++) {
                                            System.out.println((i + 1) + ". " + forums1.get(i));
                                        }
                                        System.out.println("Choose a forum(enter a number)");
                                        int forumNum = 0;
                                        if (scan.hasNextInt()) {
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                        while (forumNum > forums1.size()) {
                                            System.out.println("Sorry there was an error, try again");
                                            for (int i = 0; i < forums1.size(); i++) {
                                                System.out.println((i + 1) + ". " + forums1.get(i));
                                            }
                                            System.out.println("Choose a forum(enter a number)");
                                            forumNum = 0;
                                            if (scan.hasNextInt()) {
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                        }
                                        Forum forum = forums1.get(forumNum - 1);
                                        System.out.println("Do you want to edit or delete this forum?");
                                        String delete = scan.nextLine();
                                        if (delete.equalsIgnoreCase("edit")) {
                                            //edit forum
                                            System.out.println("What do you want your new topic to be?");
                                            String topic = scan.nextLine();
                                            course.editForum(forum.getForumId(), topic);
                                        } else if (delete.equalsIgnoreCase("delete")) {
                                            //delete forum
                                            course.deleteForum(forum.getForumId());
                                        }
                                    } else {
                                        System.out.println("Returning back to log-in menu");
                                        contAgain = false;
                                        userCheck = false;
                                        teacherDashboard = false;

                                    }
                                }

                            }
                            if (choice == 5) {
                                //delete courses
                                ArrayList<Course> getTeacherCourses = teacher.getCourses();
                                if (getTeacherCourses.size() == 0) {
                                    System.out.println("Sorry there are no courses to see, you must create a course");

                                    break;

                                }
                                System.out.println("What course do you want to delete?");
                                for (int i = 0; i < getTeacherCourses.size(); i++) {
                                    System.out.println((i + 1) + ". " + getTeacherCourses.get(i));
                                }
                                System.out.println("Please choose a course(type in a number)");
                                int courseNum = 0;
                                if (scan.hasNextInt()) {
                                    courseNum = scan.nextInt();
                                    scan.nextLine();
                                } else {
                                    while (!scan.hasNextInt()) {
                                        System.out.println("Not a number!");
                                    }
                                    courseNum = scan.nextInt();
                                    scan.nextLine();
                                }
                                while (courseNum > getTeacherCourses.size() || courseNum < 0) {
                                    System.out.println("Sorry number choice invalid, try again");
                                    for (int i = 0; i < getTeacherCourses.size(); i++) {
                                        System.out.println((i + 1) + ". " + getTeacherCourses.get(i).getName());
                                    }
                                    System.out.println("Please choose a course(type in a number)");
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                }
                                Course course = getTeacherCourses.get(courseNum - 1);
                                System.out.println("You selected: " + course);
                                System.out.println("Deleting...");
                                int courseId = course.getCourseId();
                                teacher.deleteCourse(courseId);

                            }
                        }

                    } else if (userType.equalsIgnoreCase("Student")) {
                        Student student = (Student) user1;
                        while (contAgain) {
                            studentMenu(user);
                            int choice = 0;
                            if (scan.hasNextInt()) {
                                choice = scan.nextInt();
                                scan.nextLine();
                            } else {
                                while (!scan.hasNextInt()) {
                                    System.out.println("Not a number!");
                                }
                                choice = scan.nextInt();
                                scan.nextLine();
                            }
                            if (choice == 1) {
                                System.out.println(exitMessage);
                                //cont = true;
                                contAgain = false;
                                userCheck = false;
                            }
                            if (choice == 2) {
                                System.out.println("Do you want to edit or delete your account?(edit/delete)");
                                String modify = scan.nextLine();
                                if (modify.equalsIgnoreCase("edit")) {
                                    //edit account
                                    System.out.println("Do you want to change your password or your username?(password/username)");
                                    System.out.println("If neither, please type exit");
                                    String edit = scan.nextLine();
                                    if (edit.equalsIgnoreCase("password")) {
                                        //changing password
                                        for (int i = 0; i < 3; i++) {
                                            System.out.println("Enter in current password");
                                            String passwordChecker = scan.nextLine();
                                            if (!password.equals(passwordChecker)) {
                                                System.out.println("Error - invalid password");
                                                System.out.println("You have " + (2 - i) + " more attempts");
                                                if (i == 2) {
                                                    System.out.println("Password attempts failed -- logging out now");
                                                    contAgain = false;
                                                    userCheck = false;
                                                    passwordCheck = false;
                                                    continue;
                                                }
                                            }
                                            if (passwordChecker.equals(password)) {
                                                i = 3;
                                                passwordCheck = true;
                                            }
                                        }

                                        while (passwordCheck) {
                                            System.out.println("Choose a new password");
                                            password = scan.nextLine();
                                            System.out.println("Re-enter your password");
                                            password2 = scan.nextLine();
                                            if (!password.equals(password2)) {
                                                System.out.println("Please try again - passwords don't match");
                                            } else {
                                                passwordCheck = false;
                                            }
                                        }
                                        LoginChecker.changePassword(user, password);
                                    } else if (edit.equalsIgnoreCase("username")) {
                                        //changing username
                                        System.out.println("Enter your new username");
                                        String newUser = scan.nextLine();
                                        student.setUsername(newUser);
                                        LoginChecker.changeUsername("Student", student.getStudentId(), newUser);
                                    } else {
                                        System.out.println("Returning back to log-in menu");
                                        contAgain = false;
                                        userCheck = false;
                                    }

                                } else if (modify.equalsIgnoreCase("delete")) {
                                    //deleting account
                                    for (int i = 0; i < 3; i++) {
                                        System.out.println("Enter in current password");
                                        String passwordChecker = scan.nextLine();
                                        if (!password.equals(passwordChecker)) {
                                            System.out.println("Error - invalid password");
                                            System.out.println("You have " + (2 - i) + " more attempts");
                                            if (i == 3) {
                                                System.out.println("Password attempts failed -- logging out now");
                                                contAgain = false;
                                                userCheck = false;
                                            }
                                        }
                                        if (passwordChecker.equals(password)) {
                                            LoginChecker.deleteLogin(user);
                                            contAgain = false;
                                            userCheck = false;
                                            i = 3;

                                        }
                                    }
                                } else {
                                    System.out.println("Error invalid input - please try again");
                                }
                            }
                            if (choice == 3) {
                                //enroll in courses
                                enterCourse = true;
                                while (enterCourse) {
                                    if (courses.size() == 0) {
                                        System.out.println("Sorry there are no courses to enroll in");
                                        enterCourse = false;
                                        continue;
                                    }
                                    for (int i = 0; i < courses.size(); i++) {
                                        System.out.println((i + 1) + courses.get(i).getName());
                                    }
                                    System.out.println("Which course do you want to join?(type in a number)");
                                    int courseNum = 0;
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (courseNum > courses.size() || courseNum < 0) {
                                        System.out.println("Sorry number choice invalid, try again");
                                        for (int i = 0; i < courses.size(); i++) {
                                            System.out.println((i + 1) + ". " + courses.get(i).getName());
                                        }
                                        System.out.println("Please choose a course(type in a number)");
                                        courseNum = 0;
                                        if (scan.hasNextInt()) {
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    Course course = courses.get(courseNum - 1);
                                    System.out.println("You selected: " + course);
                                    course.enrollCourse(student.getStudentId());
                                    System.out.println("Joined!");
                                    System.out.println("Do you want to join another course?(Y/N)");
                                    String answer = scan.nextLine();
                                    if (answer.equalsIgnoreCase("N")) {
                                        enterCourse = false;
                                    }

                                }

                            }
                            if (choice == 4) {
                                //work on a specific course and add new posts
                                studentDashboard = true;
                                while (studentDashboard) {
                                    ArrayList<Course> getStudentCourses = student.getCourses();
                                    if (getStudentCourses.size() == 0) {
                                        System.out.println("Sorry you're not enrolled in any courses, please register for one");
                                        studentDashboard = false;
                                        continue;

                                    }
                                    System.out.println("What course do you want to work on?");
                                    for (int i = 0; i < getStudentCourses.size(); i++) {
                                        System.out.println((i + 1) + "." + getStudentCourses.get(i).getName());
                                    }
                                    System.out.println("Choose a course(type in a number)");
                                    int courseNum;
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (courseNum > courses.size() || courseNum < 0) {
                                        System.out.println("Sorry number choice invalid, try again");
                                        for (int i = 0; i < courses.size(); i++) {
                                            System.out.println((i + 1) + ". " + courses.get(i).getName());
                                        }
                                        System.out.println("Please choose a course(type in a number)");
                                        if (scan.hasNextInt()) {
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    Course course = courses.get(courseNum - 1);
                                    ArrayList<Forum> forums = course.getForums();
                                    if (forums.size() == 0) {
                                        System.out.println("No forums!");
                                        System.out.println("Exiting...");
                                        studentDashboard = false;
                                        continue;
                                    }
                                    System.out.println("Choose a discussion forum");
                                    for (int j = 0; j < forums.size(); j++) {
                                        System.out.println((j + 1) + ". " + forums.get(j).getTopic() +
                                                " " + forums.get(j).getTimeStamp());
                                    }
                                    System.out.println("Choose a forum(enter a number)");
                                    int forumNum = 0;
                                    if (scan.hasNextInt()) {
                                        forumNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        forumNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (forumNum > forums.size() || forumNum < 0) {
                                        System.out.println("Sorry there was an error, try again");
                                        for (int i = 0; i < forums.size(); i++) {
                                            System.out.println((i + 1) + ". " + forums.get(i) +
                                                    " " + forums.get(i).getTimeStamp());
                                        }

                                        System.out.println("Choose a forum(enter a number)");
                                        forumNum = 0;
                                        if (scan.hasNextInt()) {
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    Forum forum = forums.get(forumNum - 1);
                                    System.out.println("Do you want to * (1)reply to a forum +" +
                                            "* (2)comment" +
                                            "* (3)vote");
                                    int choices = 0;
                                    if (scan.hasNextInt()) {
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (!(choices == 1 || choices == 2 || choices == 3)) {
                                        System.out.println("Invalid choice - try again");
                                        System.out.println("Do you want to * (1)reply to a forum +" +
                                                "* (2)comment" +
                                                "* (3)vote");
                                        choices = 0;
                                        if (scan.hasNextInt()) {
                                            choices = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            choices = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    ArrayList<Post> forumPosts = forum.getPosts();
                                    if (choices == 1) {
                                        //add a new post
                                        System.out.println("Enter in your post!");
                                        System.out.println("Do you want to import in your post?(Y/N)");
                                        String imports = scan.nextLine();
                                        if (imports.equalsIgnoreCase("Y")) {
                                            System.out.println("Enter Filename Location");
                                            String[] postInfo = UploadFile.uploadStudentPosts(scan.nextLine());
                                            while (postInfo == null) {
                                                System.out.println("Enter Filename Location");
                                                postInfo = UploadFile.uploadStudentPosts(scan.nextLine());
                                            }
                                            forum.makePost(postInfo[0], postInfo[1], student.getStudentId());
                                        } else {
                                            System.out.println("Enter in title");
                                            String title = scan.nextLine();
                                            System.out.println("Enter in your new post");
                                            String post = scan.nextLine();
                                            forum.makePost(title, post, student.getStudentId());
                                        }
                                    } else if (choices == 2) {
                                        //add comments to existing posts
                                        do {
                                            if (forumPosts.size() == 0) {
                                                System.out.println("Sorry nothing to comment on");
                                                studentDashboard = false;
                                                continue;
                                            }
                                            for (int i = 0; i < forumPosts.size(); i++) {
                                                System.out.println((i + 1) + ". " + forumPosts.get(i).getContent() +
                                                        " " + forumPosts.get(i).getTimeStamp());
                                            }
                                            System.out.println("Select a post by number");
                                            int postNum = 0;
                                            if (scan.hasNextInt()) {
                                                postNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                postNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (postNum < 0 || postNum > forumPosts.size() + 1) {
                                                System.out.println("Invalid input - try again");
                                                System.out.println("Select a post by number");
                                                postNum = 0;
                                                if (scan.hasNextInt()) {
                                                    postNum = scan.nextInt();
                                                    scan.nextLine();
                                                } else {
                                                    while (!scan.hasNextInt()) {
                                                        System.out.println("Not a number!");
                                                    }
                                                    postNum = scan.nextInt();
                                                    scan.nextLine();
                                                }
                                            }
                                            Post post = forumPosts.get(postNum - 1);
                                            System.out.println(post.getContent());
                                            System.out.println("Enter your comment");
                                            String comment = scan.nextLine();
                                            post.createComment(comment, student);
                                            System.out.println("Do you want to add another comment(Y/N)");
                                            String YN = scan.nextLine();
                                            if (YN.equalsIgnoreCase("N")) {
                                                moreComment = false;
                                            }
                                        } while (moreComment == true);

                                    } else if (choices == 3) {
                                        //add new votes
                                        if (forumPosts.size() == 0) {
                                            System.out.println("Sorry nothing to vote on");
                                            studentDashboard = false;
                                            continue;
                                        }
                                        for (int i = 0; i < forumPosts.size(); i++) {
                                            System.out.println((i + 1) + ". " + forumPosts.get(i).getContent()
                                                    + " " + forumPosts.get(i).getTimeStamp());
                                        }
                                        System.out.println("Select a post by number");
                                        int postNum = 0;
                                        if (scan.hasNextInt()) {
                                            postNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            postNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                        Vote vote = forumPosts.get(postNum - 1).getVotes();
                                        vote.addVote(student);
                                        System.out.println("Do you want to add an emoji?");
                                        String YN = scan.nextLine();
                                        if (YN.equalsIgnoreCase("Y")) {
                                            System.out.println("Enter in your emoji( :) / :| / :( )");
                                            String emoji = scan.nextLine();
                                            forumPosts.get(postNum - 1).addEmoji(emoji, student);
                                        }

                                    }
                                    System.out.println("Do you want to log out?(Y/N)");
                                    String logOut = scan.nextLine();
                                    if (logOut.equalsIgnoreCase("Y")) {
                                        studentDashboard = false;
                                        userCheck = false;
                                        contAgain = false;
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Error invalid input - please try again");
                    }
                }

            } else if (firstChoice.equalsIgnoreCase("login")) {

                while (userCheck) {
                    System.out.println("Enter your username");
                    user = scan.nextLine();
                    System.out.println("Enter your password");
                    password = scan.nextLine();
                    User user1 = LoginChecker.verifyLogin(user, password);
                    if (user1 == null) {
                        System.out.println("Incorrect Username or Password! Try Again!");
                    } else if (user1 instanceof Teacher) {
                        Teacher teacher = (Teacher) user1;
                        while (contAgain) {
                            teacherDashboard = true;

                            //the looping menu for what a teacher can do
                            enterCourse = true;
                            teacherMenu(user);
                            int choice = 0;
                            if (scan.hasNextInt()) {
                                choice = scan.nextInt();
                                scan.nextLine();
                            } else {
                                while (!scan.hasNextInt()) {
                                    System.out.println("Not a number!");
                                }
                                choice = scan.nextInt();
                                scan.nextLine();
                            }

                            if (choice == 1) {
                                System.out.println(exitMessage);
                                //cont = true;
                                contAgain = false;
                                userCheck = false;
                            }
                            if (choice == 2) {
                                //edit or delete account
                                System.out.println("Do you want to edit or delete your account?(edit/delete)");
                                String modify = scan.nextLine();
                                if (modify.equalsIgnoreCase("edit")) {
                                    System.out.println("Do you want to change your password or your username?(password/username)");
                                    System.out.println("If neither, please type exit");
                                    String edit = scan.nextLine();
                                    if (edit.equalsIgnoreCase("password")) {
                                        //changing password
                                        for (int i = 0; i < 3; i++) {
                                            System.out.println("Enter in current password");
                                            String passwordChecker = scan.nextLine();
                                            if (!password.equals(passwordChecker)) {
                                                System.out.println("Error - invalid password");
                                                System.out.println("You have " + (2 - i) + " more attempts");
                                                if (i == 2) {
                                                    System.out.println("Password attempts failed -- logging out now");
                                                    contAgain = false;
                                                    userCheck = false;
                                                    passwordCheck = false;
                                                    continue;
                                                }
                                            }
                                            if (passwordChecker.equals(password)) {
                                                i = 3;
                                                passwordCheck = true;
                                            }
                                        }

                                        while (passwordCheck) {
                                            System.out.println("Choose a new password");
                                            password = scan.nextLine();
                                            System.out.println("Re-enter your password");
                                            password2 = scan.nextLine();
                                            if (!password.equals(password2)) {
                                                System.out.println("Please try again - passwords don't match");
                                            } else {
                                                passwordCheck = false;
                                            }
                                        }
                                        LoginChecker.changePassword(user, password);
                                    } else if (edit.equalsIgnoreCase("username")) {
                                        //changing username
                                        System.out.println("Enter your new username");
                                        String newUser = scan.nextLine();
                                        teacher.setUsername(newUser);
                                        //System.out.println(teacher.getUsername());
                                        LoginChecker.changeUsername("Teacher", teacher.getTeacherId(), newUser);
                                    } else {
                                        System.out.println("Returning back to log-in menu");
                                        contAgain = false;
                                        userCheck = false;
                                    }

                                } else if (modify.equalsIgnoreCase("delete")) {
                                    //deleting account
                                    for (int i = 0; i < 3; i++) {
                                        System.out.println("Enter in current password");
                                        String passwordChecker = scan.nextLine();
                                        if (!password.equals(passwordChecker)) {
                                            System.out.println("Error - invalid password");
                                            System.out.println("You have " + (2 - i) + " more attempts");
                                            if (i == 2) {
                                                System.out.println("Password attempts failed -- logging out now");
                                                contAgain = false;
                                                userCheck = false;
                                            }
                                        }
                                        if (passwordChecker.equals(password)) {
                                            LoginChecker.deleteLogin(user);
                                            contAgain = false;
                                            userCheck = false;
                                            i = 3;

                                        }
                                    }
                                } else {
                                    System.out.println("Error invalid input - please try again");
                                }
                            }
                            if (choice == 3) {
                                //creating a new course
                                while (enterCourse) {
                                    System.out.println("Enter your new course name");
                                    String newCourseName = scan.nextLine();
                                    teacher.createCourse(newCourseName);
                                    System.out.println("Do you want to make another course(Y/N)");
                                    String answer = scan.nextLine();
                                    if (answer.equalsIgnoreCase("N")) {
                                        enterCourse = false;
                                    }
                                }
                            }
                            if (choice == 4) {
                                //showing posts and teacher dashboard
                                while (teacherDashboard) {
//

                                    ArrayList<Course> getTeacherCourses = teacher.getCourses();
                                    if (getTeacherCourses.size() == 0) {
                                        System.out.println("Sorry there are no courses to see, you must create a course");
                                        teacherDashboard = false;
                                        continue;

                                    }
                                    System.out.println("What course do you wish to view?");
                                    //System.out.println(teacher.getCourses());
                                    for (int i = 0; i < getTeacherCourses.size(); i++) {
                                        System.out.println((i + 1) + ". " + getTeacherCourses.get(i).getName());
                                    }
                                    System.out.println("Please choose a course(type in a number)");
                                    int courseNum = 0;
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }

                                    while (courseNum > getTeacherCourses.size()) {
                                        System.out.println("Sorry there was an error, try again");
                                        for (int i = 0; i < getTeacherCourses.size(); i++) {
                                            System.out.println((i + 1) + ". " + getTeacherCourses.get(i).getName());
                                        }
                                        System.out.println("Please choose a course(type in a number)");
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    //choosing a course so that teacher can edit it
                                    Course course = getTeacherCourses.get(courseNum - 1);
                                    System.out.println("You selected: " + course);
                                    int courseId = course.getCourseId();
                                    //teacher.editCourse(courseId, course.getName());
                                    System.out.println("Do you want to \n * (1)change a course Name" +
                                            "\n * (2)create a new discussion forum" +
                                            "\n * (3)view an existing forum" +
                                            "\n * (4)edit an existing forum" +
                                            "\n * (5)log out");
                                    int choices = 0;
                                    if (scan.hasNextInt()) {
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    if (choices == 1) {
                                        //create a new course
                                        System.out.println("What do you want the new course name to be?");
                                        String courseName = scan.nextLine();
                                        teacher.editCourse(courseId, courseName);

                                    } else if (choices == 2) {
                                        //create a new discussion forum
                                        boolean newForums = true;
                                        while (newForums) {
                                            System.out.println();
                                            System.out.println("Do you want to import a forum (Y/N)");
                                            String importForum = scan.nextLine();
                                            if (importForum.equalsIgnoreCase("Y")) {
                                                System.out.println("Enter file path location");
                                                String forum = UploadFile.uploadTeacherForum(scan.nextLine());
                                                while (forum == null) {
                                                    System.out.println("Enter file path location");
                                                    forum = UploadFile.uploadTeacherForum(scan.nextLine());
                                                }
                                                course.createForum(forum);
                                            } else {
                                                System.out.println("What do you want the topic of this forum to be?");
                                                String forum = scan.nextLine();
                                                course.createForum(forum);
                                            }
                                            System.out.println("Discussion board created!");
                                            System.out.println("Do you want to make another discussion forum?(Y/N)");
                                            String moreForums = scan.nextLine();
                                            if (moreForums.equalsIgnoreCase("N")) {
                                                newForums = false;
                                                teacherDashboard = false;
                                                //userCheck = false;
                                            }
                                        }
                                    } else if (choices == 3) {
                                        //view an existing forum
                                        //print posts or see the dashboard
                                        //view dashboard - ascending or descending by votes
                                        System.out.println("Do you want to view the teacher dashboard or view all posts?(dashboard/posts)");
                                        String dashboard = scan.nextLine();
                                        if (dashboard.equalsIgnoreCase("dashboard")) {
                                            System.out.println("Which discussion forum do you wish to see?");
                                            //choose what discussion forum the teacher wants to see
                                            ArrayList<Forum> forums = course.getForums();
                                            if (forums.size() == 0) {
                                                System.out.println("No forums!");
                                                System.out.println("Exiting...");
                                                teacherDashboard = false;
                                                continue;
                                            }
                                            for (int i = 0; i < forums.size(); i++) {
                                                System.out.println((i + 1) + ". " + forums.get(i).getTopic());
                                            }
                                            System.out.println("Choose a forum(enter a number)");
                                            int forumNum = 0;
                                            if (scan.hasNextInt()) {
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (forumNum > forums.size() || forumNum < 0) {
                                                System.out.println("Sorry there was an error, try again");
                                                for (int i = 0; i < forums.size(); i++) {
                                                    System.out.println((i + 1) + ". " + forums.get(i));
                                                }

                                                System.out.println("Choose a forum(enter a number)");
                                                forumNum = 0;
                                                if (scan.hasNextInt()) {
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                } else {
                                                    while (!scan.hasNextInt()) {
                                                        System.out.println("Not a number!");
                                                    }
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                }
                                            }
                                            Forum forum = forums.get(forumNum - 1);
                                            int forumId = forum.getForumId();
                                            //choosing the order of the teacher dashboard
                                            System.out.println("How do you want to view the dashboard?" +
                                                    "\n * (1)Default" +
                                                    "\n * (2)Ascending" +
                                                    "\n * (3)Descending ");
                                            int sort = 0;
                                            if (scan.hasNextInt()) {
                                                sort = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                sort = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (sort != 1 && sort != 2 && sort != 3) {
                                                System.out.println("How do you want to view the dashboard?" +
                                                        "\n * (1)Default" +
                                                        "\n * (2)Ascending" +
                                                        "\n * (3)Descending ");
                                                sort = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            teacher.teacherDashboard(sort, forumId);

                                        } else if (dashboard.equalsIgnoreCase("posts")) {
                                            //viewing all of the posts in a forum(unorganized)
                                            System.out.println("Which discussion forum do you wish to see?");
                                            ArrayList<Forum> forums1 = course.getForums();
                                            if (forums1.size() == 0) {
                                                System.out.println("No forums!");
                                                System.out.println("Exiting...");
                                                teacherDashboard = false;
                                                continue;
                                            }
                                            for (int i = 0; i < forums1.size(); i++) {
                                                System.out.println((i + 1) + ". " + forums1.get(i).getTopic() +
                                                        " " + forums1.get(i).getTimeStamp());
                                            }
                                            System.out.println("Choose a forum(enter a number)");
                                            int forumNum = 0;
                                            if (scan.hasNextInt()) {
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (forumNum > forums1.size() || forumNum < 0) {
                                                System.out.println("Sorry there was an error, try again");
                                                for (int i = 0; i < forums1.size(); i++) {
                                                    System.out.println((i + 1) + ". " + forums1.get(i));
                                                }

                                                System.out.println("Choose a forum(enter a number)");
                                                forumNum = 0;
                                                if (scan.hasNextInt()) {
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                } else {
                                                    while (!scan.hasNextInt()) {
                                                        System.out.println("Not a number!");
                                                    }
                                                    forumNum = scan.nextInt();
                                                    scan.nextLine();
                                                }
                                            }
                                            Forum forum = forums1.get(forumNum - 1);
                                            ArrayList<Post> postsforum = forum.getPosts();
                                            for (int x = 0; x < postsforum.size(); x++) {
                                                System.out.print(postsforum.get(x).getTitle() + " " + postsforum.get(x).getContent() +
                                                        " " + postsforum.get(x).getTimeStamp());
                                                for (Comment comment : postsforum.get(x).getComments()) {
                                                    System.out.print(comment.getContent() + comment.getTimeStamp());
                                                }
                                                System.out.println();
                                            }
                                            System.out.println("Do you want to add a comment?(Y/N)");
                                            String addCom = scan.nextLine();
                                            if (addCom.equalsIgnoreCase("Y")) {
                                                //add comment to a post
                                                do {
                                                    if (postsforum.size() == 0) {
                                                        System.out.println("Sorry nothing to comment on");
                                                        moreComment = false;
                                                        teacherDashboard = false;
                                                        continue;
                                                    }
                                                    for (int i = 0; i < postsforum.size(); i++) {
                                                        System.out.println((i + 1) + ". " + postsforum.get(i).getContent());
                                                    }
                                                    System.out.println("Select a post by number");
                                                    int postNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        postNum = scan.nextInt();
                                                        scan.nextLine();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Not a number!");
                                                        }
                                                        postNum = scan.nextInt();
                                                        scan.nextLine();
                                                    }
                                                    while (postNum < 0 || postNum > postsforum.size() + 1) {
                                                        System.out.println("Invalid input - try again");
                                                        System.out.println("Select a post by number");
                                                        postNum = scan.nextInt();
                                                        scan.nextLine();
                                                    }
                                                    Post post = postsforum.get(postNum - 1);
                                                    System.out.println(post.getContent());
                                                    System.out.println("Enter your comment");
                                                    String comment = scan.nextLine();
                                                    post.createComment(comment, teacher);
                                                    System.out.println("Do you want to add another comment(Y/N)");
                                                    String YN = scan.nextLine();
                                                    if (YN.equalsIgnoreCase("N")) {
                                                        moreComment = false;
                                                    }

                                                } while (moreComment == true);

                                                System.out.println("Do you want to edit or delete a comment?(edit/delete/neither)");

                                                String editDeleteComment = scan.nextLine();
                                                if (editDeleteComment.equalsIgnoreCase("edit")) {
                                                    //editing a students comment
                                                    int count = 1;
                                                    for (int x = 0; x < postsforum.size(); x++) {
                                                        System.out.print(x + ". " + postsforum.get(x).getTitle() + " " + postsforum.get(x).getContent() +
                                                                " " + postsforum.get(x).getTimeStamp());
                                                        for (Comment comment : postsforum.get(x).getComments()) {
                                                            System.out.print(count + ". " + comment.getContent() + comment.getTimeStamp());
                                                        }
                                                    }
                                                    System.out.println("Choose a post");
                                                    int postNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        postNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            postNum = scan.nextInt();
                                                        }
                                                    }
                                                    Post post = posts.get(postNum - 1);
                                                    for (int k = 0; k < post.getComments().size(); k++) {
                                                        System.out.println(k + ". " +
                                                                post.getComments().get(k).getContent());
                                                    }
                                                    System.out.println("Choose a comment");
                                                    int commentNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        commentNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            commentNum = scan.nextInt();
                                                        }
                                                    }
                                                    System.out.println(post.getComments().get(commentNum - 1).getContent());
                                                    int commentId = post.getComments().get(commentNum - 1).getId();
                                                    System.out.println("Type in your updated comment");
                                                    String newComment = scan.nextLine();
                                                    post.editComment(commentId, newComment);
                                                    System.out.println("Edited!");

                                                } else if (editDeleteComment.equalsIgnoreCase("delete")) {
                                                    //deleting a students comments
                                                    int count = 1;
                                                    for (int x = 0; x < postsforum.size(); x++) {
                                                        System.out.print(x + ". " + postsforum.get(x).getTitle() + " " + postsforum.get(x).getContent() +
                                                                " " + postsforum.get(x).getTimeStamp());
                                                        for (Comment comment : postsforum.get(x).getComments()) {
                                                            System.out.print(count + ". " + comment.getContent() + comment.getTimeStamp());
                                                        }
                                                    }
                                                    System.out.println("Choose a post");
                                                    int postNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        postNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            postNum = scan.nextInt();
                                                        }
                                                    }
                                                    Post post = posts.get(postNum - 1);
                                                    for (int k = 0; k < post.getComments().size(); k++) {
                                                        System.out.println(k + ". " +
                                                                post.getComments().get(k).getContent());
                                                    }
                                                    System.out.println("Choose a comment");
                                                    int commentNum = 0;
                                                    if (scan.hasNextInt()) {
                                                        commentNum = scan.nextInt();
                                                    } else {
                                                        while (!scan.hasNextInt()) {
                                                            System.out.println("Error invalid input");
                                                            commentNum = scan.nextInt();
                                                        }
                                                    }
                                                    System.out.println(post.getComments().get(commentNum - 1).getContent());
                                                    int commentId = post.getComments().get(commentNum - 1).getId();
                                                    post.deleteComment(commentId);
                                                    System.out.println("Deleted!");
                                                } else {
                                                    continue;
                                                }

                                            }

                                        } else {
                                            System.out.println("Invalid Input - try again");
                                            teacherDashboard = false;
                                            continue;
                                        }


                                    } else if (choices == 4) {
                                        //edit or delete a forum
                                        System.out.println("Which discussion forum do you wish to change?");
                                        if (forums.size() == 0) {
                                            System.out.println("No forums!");
                                            System.out.println("Exiting...");
                                            teacherDashboard = false;
                                            continue;
                                        }
                                        ArrayList<Forum> forums1 = course.getForums();
                                        if (forums.size() == 0) {
                                            System.out.println("No forums!");
                                            System.out.println("Exiting...");
                                            teacherDashboard = false;
                                            continue;
                                        }
                                        for (int i = 0; i < forums1.size(); i++) {
                                            System.out.println((i + 1) + ". " + forums1.get(i));
                                        }
                                        System.out.println("Choose a forum(enter a number)");
                                        int forumNum = 0;
                                        if (scan.hasNextInt()) {
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                        while (forumNum > forums1.size()) {
                                            System.out.println("Sorry there was an error, try again");
                                            for (int i = 0; i < forums1.size(); i++) {
                                                System.out.println((i + 1) + ". " + forums1.get(i));
                                            }
                                            System.out.println("Choose a forum(enter a number)");
                                            forumNum = 0;
                                            if (scan.hasNextInt()) {
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                forumNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                        }
                                        Forum forum = forums1.get(forumNum - 1);
                                        System.out.println("Do you want to edit or delete this forum?");
                                        String delete = scan.nextLine();
                                        if (delete.equalsIgnoreCase("edit")) {
                                            //edit forum
                                            System.out.println("What do you want your new topic to be?");
                                            String topic = scan.nextLine();
                                            course.editForum(forum.getForumId(), topic);
                                        } else if (delete.equalsIgnoreCase("delete")) {
                                            //delete forum
                                            course.deleteForum(forum.getForumId());
                                        }
                                    } else {
                                        System.out.println("Returning back to log-in menu");
                                        contAgain = false;
                                        userCheck = false;
                                        teacherDashboard = false;

                                    }
                                }

                            }
                            if (choice == 5) {
                                //delete courses
                                ArrayList<Course> getTeacherCourses = teacher.getCourses();
                                if (getTeacherCourses.size() == 0) {
                                    System.out.println("Sorry there are no courses to see, you must create a course");

                                    break;

                                }
                                System.out.println("What course do you want to delete?");
                                for (int i = 0; i < getTeacherCourses.size(); i++) {
                                    System.out.println((i + 1) + ". " + getTeacherCourses.get(i));
                                }
                                System.out.println("Please choose a course(type in a number)");
                                int courseNum = 0;
                                if (scan.hasNextInt()) {
                                    courseNum = scan.nextInt();
                                    scan.nextLine();
                                } else {
                                    while (!scan.hasNextInt()) {
                                        System.out.println("Not a number!");
                                    }
                                    courseNum = scan.nextInt();
                                    scan.nextLine();
                                }
                                while (courseNum > getTeacherCourses.size() || courseNum < 0) {
                                    System.out.println("Sorry number choice invalid, try again");
                                    for (int i = 0; i < getTeacherCourses.size(); i++) {
                                        System.out.println((i + 1) + ". " + getTeacherCourses.get(i).getName());
                                    }
                                    System.out.println("Please choose a course(type in a number)");
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                }
                                Course course = getTeacherCourses.get(courseNum - 1);
                                System.out.println("You selected: " + course);
                                System.out.println("Deleting...");
                                int courseId = course.getCourseId();
                                teacher.deleteCourse(courseId);

                            }
                        }

                    } else if (user1 instanceof Student) {
                        Student student = (Student) user1;
                        while (contAgain) {
                            studentMenu(user);
                            int choice = 0;
                            if (scan.hasNextInt()) {
                                choice = scan.nextInt();
                                scan.nextLine();
                            } else {
                                while (!scan.hasNextInt()) {
                                    System.out.println("Not a number!");
                                }
                                choice = scan.nextInt();
                                scan.nextLine();
                            }
                            if (choice == 1) {
                                System.out.println(exitMessage);
                                //cont = true;
                                contAgain = false;
                                userCheck = false;
                            }
                            if (choice == 2) {
                                System.out.println("Do you want to edit or delete your account?(edit/delete)");
                                String modify = scan.nextLine();
                                if (modify.equalsIgnoreCase("edit")) {
                                    //edit account
                                    System.out.println("Do you want to change your password or your username?(password/username)");
                                    System.out.println("If neither, please type exit");
                                    String edit = scan.nextLine();
                                    if (edit.equalsIgnoreCase("password")) {
                                        //changing password
                                        for (int i = 0; i < 3; i++) {
                                            System.out.println("Enter in current password");
                                            String passwordChecker = scan.nextLine();
                                            if (!password.equals(passwordChecker)) {
                                                System.out.println("Error - invalid password");
                                                System.out.println("You have " + (2 - i) + " more attempts");
                                                if (i == 2) {
                                                    System.out.println("Password attempts failed -- logging out now");
                                                    contAgain = false;
                                                    userCheck = false;
                                                    passwordCheck = false;
                                                    continue;
                                                }
                                            }
                                            if (passwordChecker.equals(password)) {
                                                i = 3;
                                                passwordCheck = true;
                                            }
                                        }

                                        while (passwordCheck) {
                                            System.out.println("Choose a new password");
                                            password = scan.nextLine();
                                            System.out.println("Re-enter your password");
                                            password2 = scan.nextLine();
                                            if (!password.equals(password2)) {
                                                System.out.println("Please try again - passwords don't match");
                                            } else {
                                                passwordCheck = false;
                                            }
                                        }
                                        LoginChecker.changePassword(user, password);
                                    } else if (edit.equalsIgnoreCase("username")) {
                                        //changing username
                                        System.out.println("Enter your new username");
                                        String newUser = scan.nextLine();
                                        student.setUsername(newUser);
                                        LoginChecker.changeUsername("Student", student.getStudentId(), newUser);
                                    } else {
                                        System.out.println("Returning back to log-in menu");
                                        contAgain = false;
                                        userCheck = false;
                                    }

                                } else if (modify.equalsIgnoreCase("delete")) {
                                    //deleting account
                                    for (int i = 0; i < 3; i++) {
                                        System.out.println("Enter in current password");
                                        String passwordChecker = scan.nextLine();
                                        if (!password.equals(passwordChecker)) {
                                            System.out.println("Error - invalid password");
                                            System.out.println("You have " + (2 - i) + " more attempts");
                                            if (i == 3) {
                                                System.out.println("Password attempts failed -- logging out now");
                                                contAgain = false;
                                                userCheck = false;
                                            }
                                        }
                                        if (passwordChecker.equals(password)) {
                                            LoginChecker.deleteLogin(user);
                                            contAgain = false;
                                            userCheck = false;
                                            i = 3;

                                        }
                                    }
                                } else {
                                    System.out.println("Error invalid input - please try again");
                                }
                            }
                            if (choice == 3) {
                                //enroll in courses
                                enterCourse = true;
                                while (enterCourse) {
                                    if (courses.size() == 0) {
                                        System.out.println("Sorry there are no courses to enroll in");
                                        enterCourse = false;
                                        continue;
                                    }
                                    for (int i = 0; i < courses.size(); i++) {
                                        System.out.println((i + 1) + courses.get(i).getName());
                                    }
                                    System.out.println("Which course do you want to join?(type in a number)");
                                    int courseNum = 0;
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (courseNum > courses.size() || courseNum < 0) {
                                        System.out.println("Sorry number choice invalid, try again");
                                        for (int i = 0; i < courses.size(); i++) {
                                            System.out.println((i + 1) + ". " + courses.get(i).getName());
                                        }
                                        System.out.println("Please choose a course(type in a number)");
                                        courseNum = 0;
                                        if (scan.hasNextInt()) {
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    Course course = courses.get(courseNum - 1);
                                    System.out.println("You selected: " + course);
                                    course.enrollCourse(student.getStudentId());
                                    System.out.println("Joined!");
                                    System.out.println("Do you want to join another course?(Y/N)");
                                    String answer = scan.nextLine();
                                    if (answer.equalsIgnoreCase("N")) {
                                        enterCourse = false;
                                    }

                                }

                            }
                            if (choice == 4) {
                                //work on a specific course and add new posts
                                studentDashboard = true;
                                while (studentDashboard) {
                                    ArrayList<Course> getStudentCourses = student.getCourses();
                                    if (getStudentCourses.size() == 0) {
                                        System.out.println("Sorry you're not enrolled in any courses, please register for one");
                                        studentDashboard = false;
                                        continue;

                                    }
                                    System.out.println("What course do you want to work on?");
                                    for (int i = 0; i < getStudentCourses.size(); i++) {
                                        System.out.println((i + 1) + "." + getStudentCourses.get(i).getName());
                                    }
                                    System.out.println("Choose a course(type in a number)");
                                    int courseNum;
                                    if (scan.hasNextInt()) {
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        courseNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (courseNum > courses.size() || courseNum < 0) {
                                        System.out.println("Sorry number choice invalid, try again");
                                        for (int i = 0; i < courses.size(); i++) {
                                            System.out.println((i + 1) + ". " + courses.get(i).getName());
                                        }
                                        System.out.println("Please choose a course(type in a number)");
                                        if (scan.hasNextInt()) {
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            courseNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    Course course = courses.get(courseNum - 1);
                                    ArrayList<Forum> forums = course.getForums();
                                    if (forums.size() == 0) {
                                        System.out.println("No forums!");
                                        System.out.println("Exiting...");
                                        studentDashboard = false;
                                        continue;
                                    }
                                    System.out.println("Choose a discussion forum");
                                    for (int j = 0; j < forums.size(); j++) {
                                        System.out.println((j + 1) + ". " + forums.get(j).getTopic() +
                                                " " + forums.get(j).getTimeStamp());
                                    }
                                    System.out.println("Choose a forum(enter a number)");
                                    int forumNum = 0;
                                    if (scan.hasNextInt()) {
                                        forumNum = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        forumNum = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (forumNum > forums.size() || forumNum < 0) {
                                        System.out.println("Sorry there was an error, try again");
                                        for (int i = 0; i < forums.size(); i++) {
                                            System.out.println((i + 1) + ". " + forums.get(i) +
                                                    " " + forums.get(i).getTimeStamp());
                                        }

                                        System.out.println("Choose a forum(enter a number)");
                                        forumNum = 0;
                                        if (scan.hasNextInt()) {
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            forumNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    Forum forum = forums.get(forumNum - 1);
                                    System.out.println("Do you want to * (1)reply to a forum +" +
                                            "* (2)comment" +
                                            "* (3)vote");
                                    int choices = 0;
                                    if (scan.hasNextInt()) {
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    } else {
                                        while (!scan.hasNextInt()) {
                                            System.out.println("Not a number!");
                                        }
                                        choices = scan.nextInt();
                                        scan.nextLine();
                                    }
                                    while (!(choices == 1 || choices == 2 || choices == 3)) {
                                        System.out.println("Invalid choice - try again");
                                        System.out.println("Do you want to * (1)reply to a forum +" +
                                                "* (2)comment" +
                                                "* (3)vote");
                                        choices = 0;
                                        if (scan.hasNextInt()) {
                                            choices = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            choices = scan.nextInt();
                                            scan.nextLine();
                                        }
                                    }
                                    ArrayList<Post> forumPosts = forum.getPosts();
                                    if (choices == 1) {
                                        //add a new post
                                        System.out.println("Enter in your post!");
                                        System.out.println("Do you want to import in your post?(Y/N)");
                                        String imports = scan.nextLine();
                                        if (imports.equalsIgnoreCase("Y")) {
                                            System.out.println("Enter Filename Location");
                                            String[] postInfo = UploadFile.uploadStudentPosts(scan.nextLine());
                                            while (postInfo == null) {
                                                System.out.println("Enter Filename Location");
                                                postInfo = UploadFile.uploadStudentPosts(scan.nextLine());
                                            }
                                            forum.makePost(postInfo[0], postInfo[1], student.getStudentId());
                                        } else {
                                            System.out.println("Enter in title");
                                            String title = scan.nextLine();
                                            System.out.println("Enter in your new post");
                                            String post = scan.nextLine();
                                            forum.makePost(title, post, student.getStudentId());
                                        }
                                    } else if (choices == 2) {
                                        //add comments to existing posts
                                        do {
                                            if (forumPosts.size() == 0) {
                                                System.out.println("Sorry nothing to comment on");
                                                studentDashboard = false;
                                                continue;
                                            }
                                            for (int i = 0; i < forumPosts.size(); i++) {
                                                System.out.println((i + 1) + ". " + forumPosts.get(i).getContent() +
                                                        " " + forumPosts.get(i).getTimeStamp());
                                            }
                                            System.out.println("Select a post by number");
                                            int postNum = 0;
                                            if (scan.hasNextInt()) {
                                                postNum = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                                while (!scan.hasNextInt()) {
                                                    System.out.println("Not a number!");
                                                }
                                                postNum = scan.nextInt();
                                                scan.nextLine();
                                            }
                                            while (postNum < 0 || postNum > forumPosts.size() + 1) {
                                                System.out.println("Invalid input - try again");
                                                System.out.println("Select a post by number");
                                                postNum = 0;
                                                if (scan.hasNextInt()) {
                                                    postNum = scan.nextInt();
                                                    scan.nextLine();
                                                } else {
                                                    while (!scan.hasNextInt()) {
                                                        System.out.println("Not a number!");
                                                    }
                                                    postNum = scan.nextInt();
                                                    scan.nextLine();
                                                }
                                            }
                                            Post post = forumPosts.get(postNum - 1);
                                            System.out.println(post.getContent());
                                            System.out.println("Enter your comment");
                                            String comment = scan.nextLine();
                                            post.createComment(comment, student);
                                            System.out.println("Do you want to add another comment(Y/N)");
                                            String YN = scan.nextLine();
                                            if (YN.equalsIgnoreCase("N")) {
                                                moreComment = false;
                                            }
                                        } while (moreComment == true);

                                    } else if (choices == 3) {
                                        //add new votes
                                        if (forumPosts.size() == 0) {
                                            System.out.println("Sorry nothing to vote on");
                                            studentDashboard = false;
                                            continue;
                                        }
                                        for (int i = 0; i < forumPosts.size(); i++) {
                                            System.out.println((i + 1) + ". " + forumPosts.get(i).getContent()
                                                    + " " + forumPosts.get(i).getTimeStamp());
                                        }
                                        System.out.println("Select a post by number");
                                        int postNum = 0;
                                        if (scan.hasNextInt()) {
                                            postNum = scan.nextInt();
                                            scan.nextLine();
                                        } else {
                                            while (!scan.hasNextInt()) {
                                                System.out.println("Not a number!");
                                            }
                                            postNum = scan.nextInt();
                                            scan.nextLine();
                                        }
                                        Vote vote = forumPosts.get(postNum - 1).getVotes();
                                        vote.addVote(student);
                                        System.out.println("Do you want to add an emoji?");
                                        String YN = scan.nextLine();
                                        if (YN.equalsIgnoreCase("Y")) {
                                            System.out.println("Enter in your emoji( :) / :| / :( )");
                                            String emoji = scan.nextLine();
                                            forumPosts.get(postNum - 1).addEmoji(emoji, student);
                                        }

                                    }
                                    System.out.println("Do you want to log out?(Y/N)");
                                    String logOut = scan.nextLine();
                                    if (logOut.equalsIgnoreCase("Y")) {
                                        studentDashboard = false;
                                        userCheck = false;
                                        contAgain = false;
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Error invalid input - please try again");
                    }
                }

            } else if (firstChoice.equalsIgnoreCase("exit")) {
                System.out.println(exitMessage);
                cont = false;
            } else {
                System.out.println("Invalid choice, try again");
            }


        }
    }


    public static void teacherMenu(String username) {
        System.out.println("Welcome " + username + ":");
        System.out.println("Select an Option(1/2/3/4/5)");
        System.out.println("* (1)Log Out\n" +
                "* (2)Modify Account\n" +
                "* (3)Create a course\n" +
                "* (4)Edit a course\n" +
                "* (5)Delete a course");
    }

    public static void studentMenu(String username) {
        System.out.println("Welcome " + username + ":");
        System.out.println("Select an Option(1/2/3/4)");
        System.out.println("* (1)Log Out\n" +
                "* (2)Modify Account\n" +
                "* (3)Enroll in a course\n" +
                "* (4)Work on a course");
    }

}
