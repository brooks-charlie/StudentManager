package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONUtilities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
//import org.javax.json.simple.JSONObject;

@Entity
@Table(name="student")
public class Student implements Populace{

    @Id
    @Column(name="id")
    private int id;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="email")
    private String email;

    public Student(){

    }
    public Student(String firstname, String lastName, String email){
        this.firstName=firstname;
        this.lastName=lastName;
        this.email=email;



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

    //
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

    //JSON Build
    // Take the list of students from the database and write to a file
    public String createJson(List studentList){

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        //try {
        //    //System.out.println(gson.toJson(studentList));
        //} catch (Exception e) { }

        try{
            FileWriter file = new FileWriter("students.txt");
            file.write(gson.toJson(studentList));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.toJson(studentList);
    }




    //JSON Parse
    public JSONArray parseJson(String filePath,boolean isUrl) throws Exception {
        //String filePath = "students.txt";
        JSONArray jsonArray = null;

        if(isUrl){
            URL WebPage = new URL(filePath);
            URLConnection urlc = WebPage.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            JSONParser jsonParser = new JSONParser();
            jsonArray = (JSONArray) jsonParser.parse(in);

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        }
        else {
            try {

                FileReader reader = new FileReader(filePath);
                JSONParser jsonParser = new JSONParser();
                jsonArray = (JSONArray) jsonParser.parse(reader);


            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return jsonArray;
    }


    //QCJSON stringify
    public String stringifyJson(List studentList){

        //Object anObject = this.mapStudents(studentList);
        ArrayList aJSONArrayList = new ArrayList();
        aJSONArrayList.add("1");
        aJSONArrayList.add("hello");
        HashMap aJSONArrayListHash = new HashMap();
        aJSONArrayListHash.put("name","fred");
        aJSONArrayListHash.put("age","23");
        aJSONArrayList.add(aJSONArrayListHash);

        String aJSONString = "[“1”, “hello”, {“name”:”fred”,”age”:”23″}]";
        //"{“state”:”Idaho”, “city”:”Rexburg”, “people”:[“bob”,”sue”]}";
        //Serializable anObject = null;
        Object anObject = null;
        String jsonString = null;
        try {
            anObject = JSONUtilities.parse(aJSONString);
            jsonString = JSONUtilities.stringify(aJSONArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (org.quickconnectfamily.json.ParseException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
    
    //QCJSON parse
    public Object parseStudentList(String filePath){
        Object studentObjects = null;
        try {
            studentObjects = JSONUtilities.parse(filePath);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (org.quickconnectfamily.json.ParseException e) {
            e.printStackTrace();
        }
        return studentObjects;
    }


    //Java Collection List
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
            System.out.println("First1 Student ID from List: " + student1.getId());

            for (Iterator iterator = students.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                System.out.print("Student ID: " + student.getId());
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
        session.close();
        return students;
    }

    //Java Collections


    //Java Collection Set
    public Set<Integer> setStudents(List studentList){
        Set<Integer> studentIds = null;

        try {
            studentIds = new HashSet<Integer>();
            for (Iterator iterator = studentList.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                //Set add method
                studentIds.add(student.getId());
            }
        } catch (Exception e) { }

        //Set size method
        System.out.println("The last Student ID is: " + studentIds.size());
        return studentIds;
    }

    //Java Collection Map
    public Map mapStudents(List studentList){
        Map students = null;

        try {
            students = new HashMap();
            for (Iterator iterator = studentList.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                //Map add method
                students.put(student.id,student.firstName);
            }
        } catch (Exception e) { }

        //Print Map
        System.out.println("Print of student HashMap: " + students);
        return students;
    }

    //Java Collection TreeMap
    public TreeMap treeMapStudents(List studentList){
        TreeMap students = null;

        try {
            students = new TreeMap();
            for (Iterator iterator = studentList.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                //TreeMap add method
                students.put(student.id,student.firstName);
            }
        } catch (Exception e) { }

        //print TreeMap
        System.out.println("Print of student TreeMap: " + students);
        return students;
    }

    //Java Collection TreeSet
    public TreeSet treeSetStudents(List studentList){
        TreeSet students = null;

        try {
            students = new TreeSet();
            for (Iterator iterator = studentList.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                //TreeSet add method
                students.add(student.getId());
            }
        } catch (Exception e) { }

        //Set size method
        System.out.println("Print of student TreeSet: " + students);
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
                System.out.print("Student ID: " + student.getId());
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
                System.out.print("Student ID: " + student.getId());
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
