import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CollectionsTest {


    //ArrayList
    //Array list will maintain order(insertion order)-- To retireve element in the array they put insertion oreder required
    //Contains Duplicates
    @Test
    public static void arrayListTest() {
      List<String> topCompanies = new ArrayList<String>();
      //ArrayList<String> topComp = new ArrayList<String>();


      System.out.println(topCompanies.isEmpty());
      topCompanies.add("Thought");
      topCompanies.add("Amazon");
      topCompanies.add("Google");
      topCompanies.add("Oracle");
      System.out.println(topCompanies.isEmpty());
      //topCompanies.forEach((name) --> {
        //  System.out.println(name);
      //});Lambda code

      for(int i=0; i<topCompanies.size(); i++)  {
          System.out.println(topCompanies.get(i));
        }

      Iterator<String> companiesIterator = topCompanies.iterator();
      while (companiesIterator.hasNext()) {
          System.out.println(companiesIterator.next());
        }

      topCompanies.clear();
    }
        //Linked List
    @Test
    public static void LinkedlistTest(){
        List<String> humanSpecies= new LinkedList<String>();


    }

}
