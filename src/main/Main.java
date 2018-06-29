package main;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {


        System.out.println("Application Controller Pattern");
        AppController controller = new AppController();
        //controller.ControllerHash("main.Student");

        Populace collegeStudents = controller.PopulaceControllerHash.get("main.Student");
        collegeStudents.listDB();
        Populace faculty = controller.PopulaceControllerHash.get("main.Faculty");
        faculty.listDB();


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
            //main.ManageDB DBConnection = new main.ManageDB();

        // Add the student to the database and get the student id
            System.out.println(student.addToDB());
            student.createFactory().close();

    }

    // List all Students
    System.out.println("List all the students in the directory? (y/n): ");
        String listStudents = scanner.nextLine();
        if(listStudents.equals("y") || listStudents.equals("Y")) {

            //List all the students in the database
            //main.StudentServlet studentservlet = new main.StudentServlet();
            //main.StudentServlet studentservlet1 = studentservlet.ServletHash.get("/ListStudents");
            //studentservlet.HandleServlet("/ListStudents");
            //main.ManageDB DBConnection = new main.ManageDB();

            Student student = new Student();
            student.listDB();
            student.setStudents(student.listDB());
            student.mapStudents(student.listDB());
            student.treeSetStudents(student.listDB());
            student.treeMapStudents(student.listDB());
            System.out.println(student.createJson(student.listDB()));
            try {
                System.out.println("Parse 1: "+student.parseJson("https://raw.githubusercontent.com/brooks-charlie/StudentManager/master/web/students.txt",true));
                //System.out.println("Parse xml: "+student.parseJson("https://raw.githubusercontent.com/brooks-charlie/StudentManager/master/web.xml",true));
                //System.out.println("Parse oracle: "+student.parseJson("https://www.oracle.com/index.html",true));


            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Parse last: "+student.parseJson("students.txt",false));
                //System.out.println("Parse web.xml: "+student.parseJson("web.xml",false));
            } catch (Exception e) {
                e.printStackTrace();
            }
            student.createFactory().close();
            //DBConnection.createFactory().close();
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
            //main.ManageDB DBConnection = new main.ManageDB();
            Student student = new Student();
            student.searchDB(searchName);
            //student.searchDB(searchName);
            // We're done, close the factory
            //this.createFactory().close();
            student.createFactory().close();
        }

        // We're done, close the factory
        //student.createFactory.close();

    }
}





