package main;

import java.util.HashMap;

public class AppController {

    public HashMap<String,Populace> PopulaceControllerHash = new HashMap();

    //public void ControllerHash(String populace){
    public AppController(){
        PopulaceControllerHash.put("Student", new Student());
        PopulaceControllerHash.put("Faculty", new Faculty());

    }

}
