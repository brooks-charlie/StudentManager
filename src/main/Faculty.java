package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sun.jvm.hotspot.runtime.Threads;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="faculty")
public class Faculty implements Populace{
    @Id
    @Column(name="id")
    private int id;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="dept")
    private String dept;

    public Faculty(){

    }
    public Faculty(String firstname, String lastName, String email, String dept){
        this.firstName=firstname;
        this.lastName=lastName;
        this.email=email;
        this.dept=dept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public SessionFactory createFactory(){
        SessionFactory factory;
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Student.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return factory;
    }

    //main.ManageDB DBConnection = new main.ManageDB();
    public int getNewId() {

        //main.ManageDB DBConnection = new main.ManageDB();
        //Session session = main.ManageDB.createFactory().openSession();
        //Session session = this.createFactory().openSession();
        Session session = this.createFactory().openSession();
        Transaction tx = null;
        int studentId = 0;
        try {
            tx = session.beginTransaction();

            // Create a query to get the highest student id
            String studentIdHql = "select max(id) from Student";
            Query studentIdQuery = session.createQuery(studentIdHql);
            List maxId = studentIdQuery.list();
            studentId = ((Integer) maxId.get(0)).intValue();
            tx.commit();
        }catch(Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return studentId;
    }


    public Integer addToDB(){
        //save the student to the database
        Session session = this.createFactory().getCurrentSession();
        try {
            // begin the session transaction
            session.beginTransaction();
            // insert the student info to the database
            //System.out.println("inserting and committing transaction...");
            session.save(this);
            // commit the database transaction
            session.getTransaction().commit();
            //System.out.println("committed");

        } catch (Exception e) {
            if (session.beginTransaction() != null) session.beginTransaction().rollback();
            e.printStackTrace();
        }finally {
            // Clean things up
            //System.out.println("Closing transaction");
            session.close();
        }
        // get and return the student id of the new student
        return this.getNewId();
    }

    public List listDB(){

        Transaction tx = null;
        Session session = this.createFactory().openSession();
        List students = null;

        try {
            tx = session.beginTransaction();

            String hql = "from Student";
            Query query = session.createQuery(hql);
            students = query.list();

            Student student1 = (Student) students.get(0);
            System.out.println("First1 main.Student ID from List: " + student1.getId());

            for (Iterator iterator = students.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                System.out.print("main.Student ID: " + student.getId());
                System.out.print(" First Name: " + student.getFirstName());
                System.out.print("  Last Name: " + student.getLastName());
                System.out.println("  email: " + student.getEmail());
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();

        }
        return students;
    }

    public List searchDB(String name){
        Transaction tx = null;
        Session session = this.createFactory().openSession();
        List students = null;
        try {
            tx = session.beginTransaction();

            String hql = "from Student where firstName like '"+ name +"%'";
            Query query = session.createQuery(hql);
            students = query.list();
            for (Iterator iterator = students.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                System.out.print("main.Student ID: " + student.getId());
                System.out.print(" First Name: " + student.getFirstName());
                System.out.print("  Last Name: " + student.getLastName());
                System.out.println("  email: " + student.getEmail());
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();

        }

        // Do another search, but for last name this time.
        tx = null;
        session = this.createFactory().openSession();
        try {
            tx = session.beginTransaction();

            String hql = "from Student where lastName like '"+ name +"%'";
            Query query = session.createQuery(hql);
            students = query.list();
            for (Iterator iterator = students.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                System.out.print("main.Student ID: " + student.getId());
                System.out.print(" First Name: " + student.getFirstName());
                System.out.print("  Last Name: " + student.getLastName());
                System.out.println("  email: " + student.getEmail());
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();

        }

        return students;
    }
}
