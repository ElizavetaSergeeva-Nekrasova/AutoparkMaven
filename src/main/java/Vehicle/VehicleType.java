package Vehicle;

import java.util.Objects;

public class VehicleType {
    private int id;
    private String typeName;
    private double taxCoefficient;

    public VehicleType(int id, String typeName, double taxCoefficient) {
        this.id = id;
        this.typeName = typeName;
        this.taxCoefficient = taxCoefficient;
    }

    public VehicleType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    public void display() {
        System.out.println("typeName = " + this.typeName + "; taxCoefficient = " + this.taxCoefficient);
    }

    public String getString() {
        String result = this.typeName + ", " + this.taxCoefficient;

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleType that = (VehicleType) o;
        return Double.compare(that.taxCoefficient, taxCoefficient) == 0 && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, taxCoefficient);
    }
}