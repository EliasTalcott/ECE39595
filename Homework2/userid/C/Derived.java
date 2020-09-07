public class Derived extends Base {

    public void f2( ) { System.out.println("Derived::f2"); }

    public void f3( ) {
        System.out.println("Derived::f3");
    }

    public void f4( ) { System.out.println("Derived::f4"); }

    public void f5( ) {
        System.out.println("Derived::f5");
    }

    public void f6( ) {
        System.out.println("Derived::f6");
    }

    public void foo2( ) {
        System.out.println("Derived::foo2");
        f2( );
    }

    private void f1( ) {
        System.out.println("Derived::f1");
    }
}
