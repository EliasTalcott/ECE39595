public class Node<T extends Comparable<T>> {

   private static final int LESS_THAN = -1;
   private static final int EQUAL = 0;
   private static final int GREATER_THAN = 1;

   private final T data;
   Node<T> left = null;
   Node<T> right = null;

   public Node(T val) { data = val; }

   public Node<T> insertNode(T n) {

      if (n.compareTo(data) == EQUAL) return this;
      if (n.compareTo(data) == LESS_THAN) {
         if (left != null) return left.insertNode(n);
         else {
            left = new Node<>(n);
            return left;
         }
      }
      if (n.compareTo(data) == GREATER_THAN) {
         if (right != null) return right.insertNode(n);
         else {
            right = new Node<>(n);
            return left;
         }
      }
      assert false : "node "+n+" not inserted";
      return null;
   }

   public String toString( ) {
      String str = "";
      if (left != null)
         str = left.toString( ) + ", ";
      str += data;
      if (right != null)
         str += ", "+right.toString( );
      return str;
   }
}
