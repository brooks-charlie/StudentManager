package test;

import main.AppController;
import main.Faculty;
import main.Populace;
import main.Student;
import org.junit.Assert;
import org.junit.Test;



public class JUnitTests {
    Student student = new Student("charlie","brooks","me@email.com");
    AppController controller = new AppController();
    @Test
    public void testStudent(){
        // Student object should be not null
        Assert.assertNotNull(student);
        // ID should be 0 for student not added to database
        int studentID = student.getId();
        //Assert.assertEquals(0,studentID);
        Assert.assertTrue(student.getId() == 0);
        // ID in DB should be some number greater than 0
        int studentNewID = student.getNewId();
        // Add student object to DB should increment by 1 and return new student id
        Assert.assertNotNull(student.addToDB());
        //Assert.assertEquals(studentNewID+1, student.getNewId());
        Assert.assertFalse(studentNewID == student.getNewId());


    }

    @Test
    public void testController(){

        Populace collegeStudents = controller.PopulaceControllerHash.get("Student");

        //test hashmap, count, put, get, etc.

        // Check the size of the hashmap
        Assert.assertEquals(2, controller.PopulaceControllerHash.size());

        // Add a new entry to the hashmap
        Assert.assertNull(controller.PopulaceControllerHash.put("new1",new Faculty()));

        // check the size after adding new entry
        Assert.assertEquals(3, controller.PopulaceControllerHash.size());

        // Search and compare
        Assert.assertNotNull(controller.PopulaceControllerHash.get("Student").searchDB("Charlie"));
        Assert.assertNotEquals(controller.PopulaceControllerHash.get("Student"), controller.PopulaceControllerHash.get("Faculty"));
        //Assert.assertArrayEquals();
        //Assert.assertNotSame();
        //Assert.assertSame();
        //Assert.assertThat();


    }


}
