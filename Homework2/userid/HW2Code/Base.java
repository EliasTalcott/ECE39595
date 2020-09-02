class Base {
   public void f2( ) {
      System.out.println("Base::f2");
   }

   public void f3( ) {
      System.out.println("Base::f3");
   }
   
   public void f4( ) {
      System.out.println("Base::f4");
   }
   
   public void f5( ) {
      System.out.println("Base::f5");
   }
   
   public void foo0( ) {
      System.out.println("Base::foo0");
      f0( );
   }
   
   public void foo1( ) {
      System.out.println("Base::foo1");
      f1( );
   }
   
   public void foo2( ) {
      System.out.println("Base::foo2");
      f2( );
   }
   
   private void f0( ) {
      System.out.println("Base::f0");
   }
   
   private void f1( ) {
      System.out.println("Base::f1");
   }
}
