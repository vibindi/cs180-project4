# CS 18000 Project 4
## Option 1: Learning Management System Discussion Board

### Compiling and Running the Program

1. Clone the repository
    - Go into your Terminal and write `git clone https://github.com/ashviniyer21/CS18000-Project4.git`
2. Move into the directory by doing `cd CS18000-Project4`
3. Run and Compile
    - **via Terminal**
      - `javac $(find . -name "*.java")`
      - `java Driver`
    - **via IntelliJ Idea**
      - Right click on `Driver.java` and click `Run Driver.main()`

### Submissions
Ashvin - Submit Report on Brightspace

Vishnu - Submit Vocareum Workspace

### Class Descriptions

**AbstractFile**

AbstractFile is the overarching class that serves as the abstract super class to all of the other classes that save the data for important program objects (teachers, students, etc.). It is extended by all of those classes and provides core functionalities shared by those classes such as reading each subclasses' corresponding data file to an arraylist.

***Testing:***
Abstract File was tested through all the other File classes since they all extended AbstractFile(). the `reset()` and `readFile()` methods were tested numerous times and all showed to provide their intended results.

---
**Comment**

The Comment class is the class that takes in the content of a comment, and the user who created the comment and creates a comment ID for it in order to be able to reference the comment later. The Comment class is linked to the Post class because a post can have multiple comments. The Comment class also uses the CommentFile to for the persistance of its data.

***Testing:***
The Comment class was tested by comparing the output in `storage/comments.txt` to expected output in several cases. This included creating comments, deleting comments, and editing comments.

---
**CommentFile**

CommentFile, which is a subclass of AbstractFile, serves as the utility class that is used to read and write the Comment data to storage. It is called from UpdateFile for its reading and writing functionality.

***Testing:***
The CommentFile class was tested by ensuring that output to the `storage/comments.txt` was correctly formatted when running test cases. The `readFile()` and `reset()` methods inherited from AbstractFile were also tested.

---
**Course**

The Course class is the class that takes in the name of the course and the teacherId of the teacher who created the course. But the course class can also take in the name, teacherId, and the courseId for after it has been created. A teacher can use the course class to create, edit, and delete forums. A student can utilize the course class to enroll in a specific course. The Course class also uses the CourseFile to for the persistance of its data.

***Testing:***
The Course class was tested by comparing the output in `storage/courses.txt` to expected output in several cases. This included creating, deleting, and editing courses as well as enrolling students into courses. This also included allowing forums to be made on a course.

---
**CourseFile**

CourseFile, which is a subclass of AbstractFile, serves as the utility class that is used to read and write the Course data to storage. It is called from UpdateFile for its reading and writing functionality.

***Testing:***
The CourseFile class was tested by ensuring that output to the `storage/courses.txt` was correctly formatted when running test cases. The `readFile()` and `reset()` methods inherited from AbstractFile were also tested.

---
**Driver**

The Driver class is the main method of the entire project. From the driver the user has the ability to navigate through the entire program. When the user first opens the program, they are greeted with a screen asking them to sign in or sign up. If they decide to sign in, the program will automatically detect whether they are a student or a teacher and prompt them with their respective options. Teachers can create classes, edit classes, create discussion forums, edit discussion forums, edit posts, view posts, and view the teacher dashboard. Students can join any classes, create discussion posts and comment on previous posts. They can also vote. Through a series of while loops and multiple scanner inputs, the Driver loops through various menus for logging in, for teachers, and for students. The Driver class also holds the main identification container that keeps track of the next possible id that can be used by any new objects. It also holds a hashmap for each object that will return the respective object for each passed in id. 

***Testing:***
The Driver was tested by running the code through several test cases and ensuring the printouts of the driver were always correct to what was the expected behavior of the program. The Driver was also tested to ensure it worked regardless of what data was previously stored in the file from previous runs.

---
**Emoji**

The Emoji class is a class that holds a string that represents an emoji and a set of students / teachers that have replied using that emoji. The Emoji class is the super class to the Vote class. The class allows users to add and remove emojis, and prevents users from making duplicate reactions.

***Testing:***
The Emoji class was tested by comparing the output in `stoarge/posts.txt` to expected output in several cases. This included creating an emoji, deleting an emoji, voting on an emoji, and preventing duplicate voting on an emoji, and removing votes from a emoji.

---
**Forum**

The Forum class is the class that holds all of the information for a given forum including the topic of the forum and the posts that it contains. It also has the functionality of creating a new post. A forum can be created, deleted, and edited using the methods defined in the Course class. The Forum class also uses the ForumFile to for the persistance of its data.

***Testing:***
The Forum class was tested by comparing the output in `storage/fourms.txt` to expected output in several cases. This included creating, deleting, and editing forums.

---
**ForumFile**

ForumFile, which is a subclass of AbstractFile, serves as the utility class that is used to read and write the Forum data to storage. It is called from UpdateFile for its reading and writing functionality. This also included allowing posts to be made on a forum.

***Testing:***
The ForumFile class was tested by ensuring that output to the `storage/forums.txt` was correctly formatted when running test cases. The `readFile()` and `reset()` methods inherited from AbstractFile were also tested.

---
**IdentificationContainer**

The IdentificationChecker class is the class that keeps track of the next available id that is usable for a teacher, student, course, forum, post, and comment individually. It also takes care of data persistence by reading and writing to idcontainer.txt. It is called from within each of these classes to set the id.

***Testing:***
The IdentificationContainer class was tested by ensuring the output to `storage/idcontainer.txt` was correct regardless of the current situation. The Container was checked to always make sure it contained the value of the current highest ID for teacher, student, course, forum, post, and comment so duplicates cannot be created.

---
**LoginChecker**

The LoginChecker class contains methods to manage all of the accounts of the program. It contains 5 methods: createLogin (creates a new account if the username isn’t taken), verifyLogin (verifies that a username and password combination is a valid login), deleteLogin (deletes an account), changeUsername (changes the username of an account), and changePassword (changes the password of an account).

***Testing:***
The LoginChecker class was tested by ensuring that all the methods ( `createLogin()`, `verifyLogin()`, `deleteLogin()`, `changeUsername()`, and `changePassword()` all functioned as intended. This included making a login, preventing creating a login if a username is already taken, verifying if a username and password credential was valid, deleting an account, and changing username and password of an account.

---
**Post**

The Post class is the class that holds all of the posts. It takes in the content of a post, the title of the post, and the studentID of the person who uploaded the post. It then has getter and setter methods for all of the instance variables along with methods to create, edit and delete comments. The Post class also uses the PostFile to for the persistance of its data.

***Testing:***
The Post class was tested by comparing the output in `storage/posts.txt` to expected output in several cases. This included creating, deleting, and editing posts, as well as storing information on votes and emojis on the post. This also included allowing comments to be made on a post.

---
**PostFile**

PostFile, which is a subclass of AbstractFile, serves as the utility class that is used to read and write the Post data to storage. It is called from UpdateFile for its reading and writing functionality.

***Testing:***
The PostFile class was tested by ensuring that output to the `storage/posts.txt` was correctly formatted when running test cases. The `readFile()` and `reset()` methods inherited from AbstractFile were also tested.

---
**Student**

The Student class is the class that holds the information for a student. All of the student functionality is taken care of in other classes. The student id that is held in the Student class is what is referred to by all other classes to get the student. The Student class also uses the StudentFile to for the persistance of its data.

***Testing:***
The Student class was tested by comparing the output in `storage/students.txt` to expected output in several cases. This included creating, deleting, and editing student accounts.

---
**StudentFile**

StudentFile, which is a subclass of AbstractFile, serves as the utility class that is used to read and write the Student data to storage. It is called from UpdateFile for its reading and writing functionality.

***Testing:***
The StudentFile class was tested by ensuring that output to the `storage/students.txt` was correctly formatted when running test cases. The `readFile()` and `reset()` methods inherited from AbstractFile were also tested.

---
**Teacher**

The Teacher class is the class that holds all of the things that the teacher can do. It holds the functionality for creating, deleting, and editing courses. It also holds the teacher dashboard function. The functionality for creating, deleting, and editing forums are in the Course class but they use the teacher id to validate that the teacher can do anything with the forums. The Teacher class also uses the TeacherFile to for the persistance of its data.

***Testing:***
The Teacher class was tested by comparing the output in `storage/teachers.txt` to expected output in several cases. This included creating, deleting, and editing student accounts. This also included allowing teachers to create, edit, and delete courses, as well as allowing them to comment on posts.

---
**TeacherFile**

TeacherFile, which is a subclass of AbstractFile, serves as the utility class that is used to read and write the Teacher data to storage. It is called from UpdateFile for its reading and writing functionality.

***Testing:***
The TeacherFile class was tested by ensuring that output to the `storage/teachers.txt` was correctly formatted when running test cases. The `readFile()` and `reset()` methods inherited from AbstractFile were also tested.

---
**Testing**

***Testing:***
This class was used to write and run test cases.

---
**UpdateFiles**

UpdateFile uses the functionality provided by StudentFile, TeacherFile, CourseFile, ForumFile, PostFile, CommentFile, to consisely fit all of the reading and writing of data into one class when it should be done all at the same time.

***Testing:***
The UpdateFiles class was tested by ensuring the files were properly read and written to the code. This was done by modifying the state of the proram and writing to the files to update them, then comparing the read output of the files to the expected output with several test cases.

---
**UploadFile**

The UploadFile class allows for the functionality of adding a student post or a teacher forum simply by providing a file with the corresponding data. The Driver class takes care of converting the read data to actual posts/forums. These methods simply read them so they are usable by the Driver class. 

***Testing:***
The UploadFile class was tested by ensuring that the methods `uploadStudentPosts()` and `uploadTeacherForum()` properly parsed a file through several test cases and formatted the post / forum properly in `storage/posts.txt` or `storage/forums.txt` respectively.

---
**User**

The User class provides some core data and redefines the toString() and equals() methods that can be used by all Users. It is the super class for the Teacher and Student classes. 

***Testing:***
The User class was tested through the Student and Teacher class, since both of them inherit the User class. The username and password storage logic was tested for both students and teachers, which was implemented in the User class.

---
**Vote**

The Vote class extends the Emoji class since it holds all the functionality that an Emoji does, but the emoji string is set to “Vote” to symbolize that the reaction is specifically a vote. The Vote is implemented separately in order to allow teachers to sort posts by votes.

***Testing:***
The Vote class was tested by comparing the output in `stoarge/posts.txt` to expected output in several cases. This included voting, preventing duplicate votes, removing votes, and preventing removing votes from users that never voted in the first place.
