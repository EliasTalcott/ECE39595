public class Address {

   private String street;
   private String city;
   private String state;
   private String postalCode;
   private String country = "USA";

   public Address(String _street, String _city, String _state, String _postalCode) {
      street = _street;
      city = _city;
      state = _state;
      postalCode = _postalCode;
   }

   public Address(String _street, String _city, String _state, String _postalCode, String _country) {
      street = _street;
      city = _city;
      state = _state;
      postalCode = _postalCode;
      country = _country;
   }

   public String getStreet( ) {
      return street;
   }

   public void setStreet(String _street) {
      street = _street;
   }

   public String getCity( ) {
      return city;
   }

   public void setCity(String _city) {
      city = _city;
   }

   public String getState( ) {
      return state;
   }

   public void setState(String _state) {
      state = _state;
   }

   public String getPostalCode( ) {
      return postalCode;
   }

   public void setPostalCode(String _postalCode) {
      postalCode = _postalCode;
   }

   public String getCountry( ) {
      return country;
   }

   public void setCountry(String _country) {
      country = _country;
   }

   public String toString( ) {
      String address = street+"\n";
      address += city+"\n";
      address += state+"\n";
      address += country+"\n";
      address += postalCode+"\n";
      return address;
   }

   public static void main(String[ ] args) {

      Address address1 = new Address("465 Northwestern Ave, EE 310, Purdue University", "West Lafayette", "IN", "47907");
      System.out.println(address1.toString( ));
      System.out.println(address1);

      Address address = new Address("490 Northwestern Ave, MSEE 300, Purdue University", "West Lafayette", "IN", "47907", 
                                    "United States of America");
      System.out.println(address.toString( ));
      System.out.println(address);
   }
}
