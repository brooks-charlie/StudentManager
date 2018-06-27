import java.util.HashMap;

public class AppController {

    HashMap<String,Populace> PopulaceControllerHash = new HashMap();

    //public void ControllerHash(String populace){
    public AppController(){
        PopulaceControllerHash.put("Student", new Student());
        PopulaceControllerHash.put("Faculty", new Faculty());

        //Populace popManager = PopulaceControllerHash.get(populace);

        //popManager.listDB();
    }



    //public void HandleServlet(String uri) {
        //ServletHash = servletHash;

        //uri = "/ListStudents";
        //System.out.println("before liststudent hash");
        //ServletHash.put("Student", new Student());

        //System.out.println("after liststudent hash");
        //ServletHash.put("/AddStudent", new Student().addStudent());
        //System.out.println("after addstudent hash");
        //ServletHash.put(uri, new Student().listStudents());
        //System.out.println("Servlet info is: "+ServletHash.get(uri));
        //Student student = new Student();
        //student."ServletHash.get(uri)";
        //System.out.println("servlet hash is: "+ ServletHash.get("/ListStudents"));
//new Student().listStudents();
        //Integer handler = ServletHash.get(uri);

        //student.handler;
    //}
}
