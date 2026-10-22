import java.util.ArrayList;
import java.util.List;

class Teacher {
    private String name;

    Teacher(String name) { this.name = name; }
    public String getName() { return name; }
}

class Department {
    private String name;
    private List<Teacher> teachers = new ArrayList<>();

    Department(String name) { this.name = name; }
    public String getName() { return name; }

    // AGGREGATION: teacher is created outside and passed in
    public void addTeacher(Teacher t) { teachers.add(t); }

    public void show() {
        System.out.println("Department: " + name);
        for (Teacher t : teachers)
            System.out.println("  Teacher: " + t.getName());
    }
}

class College {
    private String name;
    private List<Department> departments = new ArrayList<>();

    College(String name) { this.name = name; }

    // COMPOSITION: department is created INSIDE the college
    public void addDepartment(String deptName) {
        departments.add(new Department(deptName));
    }

    public Department getDepartment(String deptName) {
        for (Department d : departments)
            if (d.getName().equals(deptName)) return d;
        return null;
    }

    public void show() {
        System.out.println("College: " + name);
        for (Department d : departments)
            d.show();
    }
}

class Course {
    private String title;

    Course(String title) { this.title = title; }
    public String getTitle() { return title; }
}

class Student {
    private String name;
    private List<Course> courses = new ArrayList<>();

    Student(String name) { this.name = name; }

    // ASSOCIATION: student and course only "know" each other
    public void enroll(Course c) { courses.add(c); }

    public void show() {
        System.out.print("Student " + name + " enrolled in:");
        for (Course c : courses)
            System.out.print(" " + c.getTitle());
        System.out.println();
    }
}

public class CollegeSystem {
    public static void main(String[] args) {

        // Teachers and courses exist on their own
        Teacher t1 = new Teacher("Dr. Sharma");
        Teacher t2 = new Teacher("Dr. Mehta");
        Teacher t3 = new Teacher("Dr. Rao");

        Course java = new Course("Java");
        Course dbms = new Course("DBMS");

        // Composition: college creates its departments
        College college = new College("ABC College");
        college.addDepartment("CSE");
        college.addDepartment("ECE");

        // Aggregation: existing teachers are added to departments
        college.getDepartment("CSE").addTeacher(t1);
        college.getDepartment("CSE").addTeacher(t2);
        college.getDepartment("ECE").addTeacher(t3);

        // Association: students use courses
        Student s1 = new Student("Riya");
        Student s2 = new Student("Aman");
        s1.enroll(java);
        s1.enroll(dbms);
        s2.enroll(java);       // same course shared by two students

        college.show();
        s1.show();
        s2.show();

        // Lifecycle test
        college = null;        // college (and its departments) become garbage
        System.out.println("---");
        System.out.println("Teacher still exists: " + t1.getName());
        System.out.println("Course still exists: " + java.getTitle());
    }
}
