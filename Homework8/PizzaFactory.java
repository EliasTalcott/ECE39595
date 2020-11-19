import java.util.*;
import java.lang.reflect.*;

public class PizzaFactory {

   // used to translate pizza names to the corresponding pizza class
   private static String[ ] pizzaClasses = {"NYStyleCheese", "NYStylePepperoni", "NYStyleClam", 
                                            "ChicagoStyleCheese", "ChicagoStylePepperoni", 
                                            "ChicagoStyleClam"};
   private static String[ ] pizzaStrings = {"nycheese", "nypepperoni", "nyclam", 
                                            "chicagocheese", "chicagopepperoni", "chicagoclam"};

   private static Map<String,String> pizzaMap = new HashMap<String,String>( );

   public PizzaFactory( ) {
      // used to translate pizza names to the corresponding pizza class
      String[ ] pizzaClassNames = {"NYStyleCheese", "NYStylePepperoni", "NYStyleClam", 
                                   "ChicagoStyleCheese", "ChicagoStylePepperoni",
                                   "ChicagoStyleClam"};
      String[ ] pizzaStrings = {"nycheese", "nypepperoni", "nyclam", 
                                "chicagocheese", "chicagopepperoni", "chicagoclam"};

      for (int i = 0; i < pizzaClassNames.length; i++) {
         pizzaMap.put(pizzaStrings[i], pizzaClassNames[i]);
      }
   
   }

   public static Pizza buildPizza(String pizzaName, PizzaIngredients ingredients) {
      Pizza pizza = null;
      String pizzaClassName = pizzaMap.get(pizzaName);
      if (pizzaClassName == null) {
         System.out.println("Pizza "+pizzaName+" appears to be mispelled");
      } else {
         try {
            /*
             Put reflection code in here to get the Class for a particular pizza
             (e.g., a NYCheesePizza), get the constructor for that pizza, where
             the constructor takes a PizzaIngredients reference as an argument,
             and then create an instance of that pizza using the constructor and
             ingredients, which was passed in.  

             The pizzaMap allows you to go from the pizza name passed in, i.e.,
             "nycheese", to the actual class name, i.e., "NYStyleCheese".  You
             will use it to get the name to pass to Class.forName(String).
            */
         } catch (Exception e) {
            System.out.println("Exception in PizzaFactory: "+pizzaName);
            e.printStackTrace( );
         }
      }
      return pizza;
   }
}
