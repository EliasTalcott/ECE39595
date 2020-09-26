import java.util.Arrays;

public class ComparableArray {

   protected int[ ] ary;

   public ComparableArray(int[ ] data) {
      ary = data;
   }

   // Do a deep copy of source
   public ComparableArray(ComparableArray source) {
      ary = new int[source.getLength( )];
      for (int i = 0; i < ary.length; i++) {
         ary[i] = source.getElement(i);
      }
   }

   /* 
    * return -1 if this < a, 0 if this equals a, 1 if this > a
    * based on the values of the ary members of this and a.
    *
    * Given an array x, x[0] = v0, x[0] = v2, ..., x[n] = vN
    * A prefix of length n of x is leading elements x[0] .. x[n-1]
    *
    * Consider the case where ary for this and a are the same length L
    * if some for some prefix of this.ary and a.ary of length k, k+1 < L,  
    * (1) if this.ary[k+1] > a.ary[k+1], then this > a
    * (2) if this.ary[k+1] < a.ary[k+1], then this < a
    * (3) if this.ary[k+1] == a.ary[k+1] for all such prefix, this == a.
    *
    * (4) If one of this.ary and a.ary is shorter than the other, and the shorter
    * ary has length L, then (1) and (2) apply.  
    * (5) If (3) applies for this L, then if the longer array has non-zero 
    * elements in positions L or greater, the longer array is greatest, 
    * (6) otherwise they are equal.
    * 
    * Examples:
    * this.ary = [0, 1, 2, 4] and a.ary = [0, 1, 2, 3], this > a by (1) (k+1 is 3)
    * this.ary = [0, 1, 1, 3] and a.ary = [0, 1, 2, 3], this < a by (2) (k+1 is 2)
    * this.ary = [0, 1, 1, 2] and a.ary = [0, 1, 1, 2], this == a by (3) 
    * this.ary = [0, 1]  and a.ary = [0, 1, 1], this < a by (3) 
    * this.ary = [0, 1, 2, 0, 0]  and a.ary = [0, 1, 2], this == a by (3) 
    * this.ary = [0, 1, 2, 0, 0]  and a.ary = [0, 1, 2], this == a by (6) 
    * this.ary = [0, 4, 1]  and a.ary = [0, 1, 1, 1, 1], this > a by (4), (k+1 = 1) 
    */

   public int compareTo(ComparableArray a) {
      // Find the length of the shorter array
      int len_this = getLength();
      int len_a = a.ary.length;
      int len = min(len_this, len_a);

      // Compare the arrays up to len
      for(int i = 0; i < len; i++) {
         if (this.ary[i] > a.ary[i]) return 1;
         if (this.ary[i] < a.ary[i]) return -1;
      }

      // If arrays are equal up to len, check if longer array has trailing nonzero values
      if (len_this > len_a && trailingNonZero(this, len) != 0) return 1;
      if (len_this < len_a && trailingNonZero(a, len) != 0) return -1;
      return 0;
   }

   // Get an element in ary at index i
   public int getElement(int i) { return ary[i]; }

   // Return the length of ary
   public int getLength( ) { return ary.length; }

   // Set all elements of ary to n
   public void makeNumber(int n) { Arrays.fill(ary, n); }

   // Print out the elements of ary
   public String toString( ) { return Arrays.toString(this.ary); }

   // Find the minimum of integers i and j
   private int min(int i, int j) { return Math.min(i, j); }

   // I used this for compareTo, for cases 5 and 6
   private int trailingNonZero(ComparableArray a, int i) {
      int comp = 0;
      for (; (comp == 0) && (i < a.getLength( )); i++) {
         if (a.getElement(i) > 0) comp = 1;
         if (a.getElement(i) < 0) comp = -1;
      }
      return comp;
   }
}
