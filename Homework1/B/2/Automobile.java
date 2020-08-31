public class Automobile {

    private int vin;
    private float miles;
    private int maintenanceMonth;
    private int maintenanceDay;
    private int maintenanceYear;

    public Automobile(int _vin, float _miles, int _maintenanceMonth, int _maintenanceDay, int _maintenanceYear) {
        vin = _vin;
        miles = _miles;
        maintenanceMonth = _maintenanceMonth;
        maintenanceDay = _maintenanceDay;
        maintenanceYear = _maintenanceYear;
    }

    public int getVin() { return vin; }

    public void setVin(int _vin) { vin = _vin; }

    public float getMiles() { return miles; }

    public void setMiles(float _miles) { miles = _miles; }

    public String getMaintenanceDate() {
        return String.format("%d/%d/%d", maintenanceMonth, maintenanceDay, maintenanceYear);
    }

    public void setMaintenanceDate(int _maintenanceMonth, int _maintenanceDay, int _maintenanceYear) {
        maintenanceMonth = _maintenanceMonth;
        maintenanceDay = _maintenanceDay;
        maintenanceYear = _maintenanceYear;
    }

    public String toString() {
        return String.format("VIN number: %d\nMiles: %.1f\nLast maintenance date: %d/%d/%d\n",
                             vin, miles, maintenanceMonth, maintenanceDay, maintenanceYear);
    }
}