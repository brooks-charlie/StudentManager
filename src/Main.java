import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;


public class Main {
    private static SessionFactory factory;
    public static void main(String[] args) {

        // Create the session factory
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Student.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        // Add a student
        // Prompt to add student
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add a new student? (y/n): ");
        String addStudent = scanner.nextLine();
        String firstname = null;
        String lastname = null;
        String email = null;

        // if yes, then gather the details to add the student
        if (addStudent.equals("y") || addStudent.equals("Y")) {
            System.out.println("Enter your first name: ");
            firstname = scanner.nextLine();

            System.out.println("Enter your last name: ");
            lastname = scanner.nextLine();

            System.out.println("Enter your email address: ");
            email = scanner.nextLine();

        //create a student object using the variables from input
        Student student = new Student(firstname, lastname, email);

        // Add the student to the database and get the student id
            System.out.println(student.addStudent(factory));

    }

    // List all Students
    System.out.println("List all the students in the directory? (y/n): ");
        String listStudents = scanner.nextLine();
        if(listStudents.equals("y") || listStudents.equals("Y")) {

            //List all the students in the database
            Student student = new Student();
            //student.listStudents(factory);
            student.setStudents(student.listStudents(factory));
            student.mapStudents(student.listStudents(factory));
            student.treeSetStudents(student.listStudents(factory));
            student.treeMapStudents(student.listStudents(factory));
            System.out.println(student.createJson(student.listStudents(factory)));
            try {
                System.out.println("Parse: "+student.parseJson("https://raw.githubusercontent.com/brooks-charlie/StudentManager/master/web/students.txt",true));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Parse: "+student.parseJson("students.txt",false));
                System.out.println("Parse web.xml: "+student.parseJson("web.xml",false));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Next add javax.json file to database
        }

        // Search for students
        System.out.println("Search for a student in the directory? (y/n): ");
        String searchStudents = scanner.nextLine();
        String searchName = null;

        if(searchStudents.equals("y") || searchStudents.equals("Y")) {
            System.out.println("Enter a first name or last name to search: ");
            searchName = scanner.nextLine();

            // search for the student
            Student student = new Student();
            student.searchStudents(factory,searchName);
        }

        // We're done, close the factory
        factory.close();

    }
}





