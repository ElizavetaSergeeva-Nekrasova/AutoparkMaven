package Autopark.Vehicle.Engine;

abstract class AbstractEngine implements Startable{
    private String typeName;
    private double taxByEngineType;

    public AbstractEngine(String typeName, double taxByEngineType) {
        this.typeName = typeName;
        this.taxByEngineType = taxByEngineType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getTaxByEngineType() {
        return taxByEngineType;
    }

    public void setTaxByEngineType(double taxByEngineType) {
        this.taxByEngineType = taxByEngineType;
    }

    @Override
    public String toString() {
        return typeName + ", " + taxByEngineType;
    }
}