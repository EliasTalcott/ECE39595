public class Automobile {

    private int vin;
    private float miles;
    private String maintenanceDate;

    public Automobile(int _vin, float _miles, int _maintenanceMonth, int _maintenanceDay, int _maintenanceYear) {
        vin = _vin;
        miles = _miles;
        maintenanceDate = String.format("%d/%d/%d", _maintenanceMonth, _maintenanceDay, _maintenanceYear);
    }

    public int getVin() { return vin; }

    public void setVin(int _vin) { vin = _vin; }

    public float getMiles() { return miles; }

    public void setMiles(float _miles) { miles = _miles; }

    public String getMaintenanceDate() { return maintenanceDate; }

    public void setMaintenanceDate(int _maintenanceMonth, int _maintenanceDay, int _maintenanceYear) {
        maintenanceDate = String.format("%d/%d/%d", _maintenanceMonth, _maintenanceDay, _maintenanceYear);
    }

    public String toString() {
        return String.format("VIN number: %d\nMiles: %.1f\nLast maintenance date: %s\n", vin, miles, maintenanceDate);
    }
}