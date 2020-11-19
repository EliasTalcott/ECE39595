public class Test {

   public static void main(String args[ ]) {

      PizzaIngredients ingredients = new PizzaIngredients( );
      PizzaFactory factory = new PizzaFactory( );

      Pizza pizza = factory.buildPizza("nycheese", ingredients);
      System.out.println("pizza 1: "+pizza);

      pizza = factory.buildPizza("nypepperoni", ingredients);
      System.out.println("pizza 2: "+pizza);

      pizza = factory.buildPizza("nyclam", ingredients);
      System.out.println("pizza 3: "+pizza);

      pizza = factory.buildPizza("chicagocheese", ingredients);
      System.out.println("pizza 4: "+pizza);

      pizza = factory.buildPizza("chicagopepperoni", ingredients);
      System.out.println("pizza 5: "+pizza);

      pizza = factory.buildPizza("chicagoclam", ingredients);
      System.out.println("pizza 6: "+pizza);

   }
}
