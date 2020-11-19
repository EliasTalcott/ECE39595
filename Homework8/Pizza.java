public class Pizza {

   public Pizza(String _name, PizzaIngredients _ingredients) {
      name = _name;
      ingredients = _ingredients;
   }

   @Override
   public String toString( ) {
      return name + ", " + ingredients;
   }

   private String name;
   private PizzaIngredients ingredients;
}
