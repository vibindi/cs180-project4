/**
 * LoginChecker Class
 * class to create logins and verify them
 *
 * @author Ashvin
 * @version 1
 */
public class LoginChecker {
    public static User verifyLogin(String username, String password) {
        for (Teacher teacher : Driver.teachers.values()) {
            if (teacher.getUsername().equals(username) && teacher.getPassword().equals(password)) {
                return teacher;
            }
        }
        for (Student student : Driver.students.values()) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                return student;
            }
        }
        return null;
    }

    public static User createLogin(String isTeacher, String username, String password) {
        for (Teacher teacher : Driver.teachers.values()) {
            if (teacher.getUsername().equalsIgnoreCase(username)) {
                return null;
            }
        }
        for (Student student : Driver.students.values()) {
            if (student.getUsername().equalsIgnoreCase(username)) {
                return null;
            }
        }
        if (isTeacher.equalsIgnoreCase("Teacher")) {
            Teacher teacher = new Teacher(username, password);
            Driver.teachers.put(teacher.getTeacherId(), teacher);
            UpdateFiles.writeToFiles();
            return teacher;
        } else if (isTeacher.equalsIgnoreCase("Student")) {
            Student student = new Student(username, password);
            Driver.students.put(student.getStudentId(), student);
            UpdateFiles.writeToFiles();
            return student;
        }
        return null;
    }

    public static boolean deleteLogin(String username) {
        for (int teacherId : Driver.teachers.keySet()) {
            if (Driver.teachers.get(teacherId).getUsername().equals(username)) {
                Driver.teachers.remove(teacherId);
                UpdateFiles.writeToFiles();
                return true;
            }
        }
        for (int teacherId : Driver.students.keySet()) {
            if (Driver.students.get(teacherId).getUsername().equals(username)) {
                Driver.students.remove(teacherId);
                UpdateFiles.writeToFiles();
                return true;
            }
        }
        return false;
    }

    public static boolean changeUsername(String isTeacher, int id, String newUsername) {
        if (isTeacher.equalsIgnoreCase("Teacher")) {
            for (Teacher teacher : Driver.teachers.values()) {
                if (teacher.getTeacherId() == id) {
                    teacher.setUsername(newUsername);
                    UpdateFiles.writeToFiles();
                    return true;
                }
            }
        } else {
            for (Student student : Driver.students.values()) {
                if (student.getStudentId() == id) {
                    student.setUsername(newUsername);
                    UpdateFiles.writeToFiles();
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean changePassword(String username, String newPassword) {
        for (Teacher teacher : Driver.teachers.values()) {
            if (teacher.getUsername().equals(username)) {
                teacher.setPassword(newPassword);
                UpdateFiles.writeToFiles();
                return true;
            }
        }
        for (Student student : Driver.students.values()) {
            if (student.getUsername().equals(username)) {
                student.setPassword(newPassword);
                UpdateFiles.writeToFiles();
                return true;
            }
        }
        return false;
    }
}