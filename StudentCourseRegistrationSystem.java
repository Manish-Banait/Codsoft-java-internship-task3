import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Course class to store course information
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    // Getters and setters
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public void enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
        }
    }

    public void dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + "\nTitle: " + title + "\nDescription: " + description +
               "\nCapacity: " + capacity + "\nSchedule: " + schedule + "\nAvailable Slots: " + getAvailableSlots() + "\n";
    }
}

// Student class to store student information
class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // Getters and setters
    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.getAvailableSlots() > 0) {
            registeredCourses.add(course);
            course.enrollStudent();
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(studentID).append("\nName: ").append(name).append("\nRegistered Courses:\n");
        for (Course course : registeredCourses) {
            sb.append(course.getTitle()).append(" (").append(course.getCourseCode()).append(")\n");
        }
        return sb.toString();
    }
}

// CourseManager class to handle course operations
class CourseManager {
    private HashMap<String, Course> courses;

    public CourseManager() {
        courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }
}

// StudentManager class to handle student operations
class StudentManager {
    private HashMap<String, Student> students;

    public StudentManager() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getStudentID(), student);
    }

    public Student getStudent(String studentID) {
        return students.get(studentID);
    }

    public void registerStudentForCourse(String studentID, Course course) {
        Student student = getStudent(studentID);
        if (student != null) {
            student.registerCourse(course);
            System.out.println("Student " + student.getName() + " has been registered for the course " + course.getTitle() + ".");
        } else {
            System.out.println("Student with ID " + studentID + " not found.");
        }
    }

    public void dropStudentFromCourse(String studentID, Course course) {
        Student student = getStudent(studentID);
        if (student != null) {
            student.dropCourse(course);
            System.out.println("Student " + student.getName() + " has been dropped from the course " + course.getTitle() + ".");
        } else {
            System.out.println("Student with ID " + studentID + " not found.");
        }
    }

    public void displayStudentInfo(String studentID) {
        Student student = getStudent(studentID);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student with ID " + studentID + " not found.");
        }
    }
}

// Main class to run the program
public class StudentCourseRegistrationSystem {
    public static void main(String[] args) {
        CourseManager courseManager = new CourseManager();
        StudentManager studentManager = new StudentManager();
        Scanner scanner = new Scanner(System.in);

        // Sample courses
        courseManager.addCourse(new Course("CS101", "Introduction to Computer Science", "Basics of CS", 30, "Mon-Wed-Fri 10:00-11:00 AM"));
        courseManager.addCourse(new Course("MATH101", "Calculus I", "Introduction to Calculus", 25, "Tue-Thu 9:00-10:30 AM"));
        courseManager.addCourse(new Course("ENG101", "English Literature", "Study of English Literature", 20, "Mon-Wed 1:00-2:30 PM"));

        // Sample students
        studentManager.addStudent(new Student("S1001", "John Doe"));
        studentManager.addStudent(new Student("S1002", "Jane Smith"));
        studentManager.addStudent(new Student("S1003", "Alice Johnson"));

        // Main program loop
        boolean running = true;
        while (running) {
            System.out.println("\nSTUDENT COURSE REGISTRATION SYSTEM");
            System.out.println("-----------------------------------");
            System.out.println("1. Display Available Courses");
            System.out.println("2. Register Student for a Course");
            System.out.println("3. Drop Student from a Course");
            System.out.println("4. Display Student Information");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (option) {
                case 1:
                    courseManager.displayCourses();
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentID = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.nextLine();
                    Course course = courseManager.getCourse(courseCode);
                    if (course != null) {
                        studentManager.registerStudentForCourse(studentID, course);
                    } else {
                        System.out.println("Course with code " + courseCode + " not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    courseCode = scanner.nextLine();
                    course = courseManager.getCourse(courseCode);
                    if (course != null) {
                        studentManager.dropStudentFromCourse(studentID, course);
                    } else {
                        System.out.println("Course with code " + courseCode + " not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextLine();
                    studentManager.displayStudentInfo(studentID);
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}