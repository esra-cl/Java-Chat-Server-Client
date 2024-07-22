import com.sun.source.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.util.*;
class ornek {
    int a;
    int b;

    // Constructor
    public ornek(int a, int b)
    {
        this.a = a;
        this.b = b;
    }
}
class odev3 implements Comparator<ornek> {

    public int compare(ornek ob1, ornek ob2)
    { // return negative - zero - pozitiv
        //we choose dif bcz the dif declare the difference between
        //these two objects
        return ob1.a - ob2.a;
    }
}
class testing  {
    public static void main(String[] args) {
  // to show how its works lets create a ar list wich is
        //list of objects
        ArrayList<ornek> ar = new ArrayList<>();
// adding elements
        ar.add(new ornek(11, 123));
        ar.add(new ornek(23, 234));
        ar.add(new ornek(12, 11));
        ar.add(new ornek(10, 13));
        //figure out how compare method worked
        // should give me the dif of (ob1.a - ob2.a)
        odev3 test = new odev3();
        System.out.println("comparing test resultes:");
        for (int i = 0; i < ar.size()-1; i++) {
            System.out.println(test.compare(ar.get(i), ar.get(i+1 )));
        }
        System.out.println("unsorted List:");
// before sorting our list
        for (int i = 0; i < ar.size(); i++) {
            System.out.println( ar.get(i));
        }
        // Sorting ornek entries by roll number
        Collections.sort(ar, new odev3());

        System.out.println("\nSorted by odev3");
        // Again iterating over entries to print them
        for (int i = 0; i < ar.size(); i++)
            System.out.println(ar.get(i));
    }
}
class TreeSetOrnek{
    public static void main(String [] args ){
        TreeSet<Integer> ornek= new TreeSet<>();
        ornek.add(10);
        ornek.add(1);
        ornek.add(11);
        ornek.add(0);
        ornek.add(2);
        ornek.add(3);
        System.out.println("my tree  :"+ornek);
        System.out.println("the number of elements in my tree  :"+ornek.size());
        // remove an elements from the table
        ornek.remove(3);
        //after deleting 3
        System.out.println("after deleting the number 3 of the tree "+ornek);
        // clearing the set
        ornek.clear();
        System.out.println("after clearing the tree"+ornek);
    }
}
