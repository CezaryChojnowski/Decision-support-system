package pl.edu.pb.swd.dataOperations.Model;

public class Range {

    public Double lowerValue;

    public Double upperValue;

    public String name;

    public Range(Double lowerValue, Double upperValue) {
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
    }

    public Range(Double lowerValue, Double upperValue, String name) {
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.name = name;
    }

    public Double getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(Double lowerValue) {
        this.lowerValue = lowerValue;
    }


    public Double getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(Double upperValue) {
        this.upperValue = upperValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Range{" +
                "lowerValue=" + lowerValue +
                ", upperValue=" + upperValue +
                ", name='" + name + '\'' +
                '}';
    }
}
