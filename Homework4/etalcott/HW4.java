import java.util.Random;

public class HW4 {

   private static void check(ComparableArray[ ] comp, int i, String status1, String status2) { 
      if (comp[i].hashCode( ) == comp[i+1].hashCode( )) {
         System.out.println("   ("+i+", "+(i+1)+") "+status1);
      } else {
         System.out.println("   ("+i+", "+(i+1)+") "+status2); 
      }
   }

   private static void print(String title, ComparableArray[ ] comp) {
      System.out.println(title+": ");
      for (int i = 0; i < comp.length; i++) {
         System.out.println("   "+comp[i].toString( ));
      }

      System.out.println("checking hash and equals:");
      for (int i = 0; i < comp.length-1; i++) {
         if (comp[i].equals(comp[i+1])) {
            check(comp, i, "is ok", "is not ok");
         } else {
            check(comp, i, "is ok", "is ok");
         }
      }
   }

   private static void swap(ComparableArray[ ] items, int i, int j) {
      ComparableArray tmp = items[i];
      items[i] = items[j];
      items[j] = tmp;
   }

   // it's a bubble sort, I'm lazy.  Sue me. :-)
   private static void sort(ComparableArray[ ] items) {
      for (int i = 0; i < items.length -1; i++) {

         for (int j = i+1; j < items.length; j++) {
            if (items[i].compareTo(items[j]) > 0) {
               swap(items, i, j);
            }
         } 
      } 
   }

   public static void main(String[ ] args) {
      Random rand = new Random(1234567890987L);
      ComparableArray[ ] intArray = new ComparableArray[5];
      for (int i = 0; i < intArray.length-2; i++) {
         int[ ] tmp = {rand.nextInt(10), rand.nextInt(10), rand.nextInt(10), rand.nextInt(10)};
         intArray[i] = new IntArray(tmp);
      }
      
      intArray[intArray.length-1] = new IntArray(intArray[intArray.length-3]);
      intArray[intArray.length-2] = new IntArray(intArray[intArray.length-3]);
      intArray[intArray.length-1].makeNumber(0);
      sort(intArray);

      ComparableArray[ ] points = new Point[5];
      for (int i = 0; i < points.length-2; i++) {
         points[i] = new Point(rand.nextInt(1000), rand.nextInt(1000));
      }
      
      points[points.length-1] = new Point(points[points.length-3]);

      ComparableArray tmp = new Point(intArray[points.length-3]);
      points[points.length-2] = tmp;
      points[points.length - 1].makeNumber(0);

      print("Printing intArray", intArray);
      print("Printing points", points);

      ComparableArray[ ] compArray = new ComparableArray[4];
      compArray[0] = intArray[0];
      compArray[1] = points[1];
      compArray[2] = intArray[2];      
      compArray[3] = points[3];
      sort(compArray);
      print("Printing compArray", compArray);
   }
}
