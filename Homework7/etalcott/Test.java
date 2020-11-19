public class Test {

   public static final int[ ]  values = {5, 1, 7, 15};

   public static void main(String[ ] args) {
      Node<Integer> rootInt = new Node<>(Integer.valueOf(10));
      for (int value : values) {
         rootInt.insertNode(Integer.valueOf(value));
      }
      System.out.println(rootInt);
      Node<Double> rootDouble = new Node<>(Double.valueOf((double) 10));
      for (int value : values) {
         rootDouble.insertNode(Double.valueOf((double) value));
      }
      System.out.println(rootDouble);
   }
}
