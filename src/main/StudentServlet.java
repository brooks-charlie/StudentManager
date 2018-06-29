package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

// Extend HttpServlet class
public class StudentServlet extends HttpServlet {



    // Method to handle GET method request.
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //Get form data from fields.
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");

        //Setup html page for response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "main.Student List";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#fff\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n"
        );

        out.println("getRequestURI is: " +request.getRequestURI());
        out.println(
                "<table width = \"80%\" border = \"1\" align = \"center\">\n" +
                        "<tr bgcolor = \"#949494\">\n" +
                        "<th>main.Student ID</th> <th>First Name</th> <th>Last Name</th> <th>Email Address</th>\n" +
                        "</tr>\n");

        String filePath = "https://raw.githubusercontent.com/brooks-charlie/StudentManager/master/web.xml";
        out.println("file path is1: " + filePath);

        try {
            Student student = new Student();
            out.println("Parse servlet: "+student.parseJson(filePath,true));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Application Conroller Pattern
        // Get servlets from web.xml file to build hashmap
        JSONArray jsonArray = null;
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        //return gson.toJson(studentList);


        //filePath = "web/WEB-INF/web.xml";
        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        try {
            jsonArray = (JSONArray) jsonParser.parse(reader);
        } catch (ParseException e) {
            e.printStackTrace();
        }





        //out.println("getServletPath is: " +request.getServletPath());
        //out.println("getPathInfo is: " +request.getPathInfo());
        //out.println("getmethod is: " +request.getMethod());
        //out.println("getServletContext is: " +request.getServletContext());


        //Check to see if the Add main.Student button is pressed or the List main.Student

        if (request.getServletPath().equals("/AddStudent")) {
            //Add the student
            Student student = new Student(first_name, last_name, email);
            //student.addToDB();
            out.println("The new main.Student has been added. The new student id is: " + student.addToDB());

        } else if (request.getServletPath().equals("/ListStudents")){
            // Set response content type


            //List all the students in the database
            Student students = new Student();
            List allStudents = students.listDB();
            for (Iterator iterator = allStudents.iterator(); iterator.hasNext(); ) {
                Student student = (Student) iterator.next();
                out.print("<tr><td>" + student.getId() + "</td>");
                out.print("<td>" + student.getFirstName() + "</td>");
                out.print("<td>" + student.getLastName() + "</td>");
                out.println("<td>" + student.getEmail() + "</td></tr>");

                //out.println("main.Student ID: " + student.getId());
            }
            out.println("\n</table>\n");
        }




        //URL WebPage = new URL(filePath);
        //URLConnection urlc = WebPage.openConnection();
        //BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        //JSONParser jsonParser = new JSONParser();
        //try {
        //    jsonArray = (JSONArray) jsonParser.parse(in);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}

        //String inputLine;
        //while ((inputLine = in.readLine()) != null)
        //    System.out.println(inputLine);
        //in.close();

        out.println("</body></html>");
            //main.Student student = new main.Student(firstname, lastname, email);

    }

    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
