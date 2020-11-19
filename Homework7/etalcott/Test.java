public class Test {

   public static final int[ ]  values = {5, 1, 7, 15};

   public static void main(String[ ] args) {
      Node rootInt = new Node(Integer.valueOf(10));
      for (int i = 0; i < values.length; i++) {
         rootInt.insertNode(Integer.valueOf(values[i])); // facotry produces Integer w/value of ...
      }
      System.out.println(rootInt); // Q
      Node rootDouble = new Node(Double.valueOf((double) 10));
      for (int i = 0; i < values.length; i++) {
         rootDouble.insertNode(Double.valueOf((double) values[i])); // facotry produces Integer w/value of ...
      }
      System.out.println(rootDouble); // Q
   }
}
