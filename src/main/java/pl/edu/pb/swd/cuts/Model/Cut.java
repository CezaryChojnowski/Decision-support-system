package pl.edu.pb.swd.cuts.Model;

public class Cut {
    double value;
    int index;
    String direction;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Cut(double value, int index, String direction) {
        this.value = value;
        this.index = index;
        this.direction = direction;
    }

    public Cut() {
    }

    @Override
    public String toString() {
        return "Cut{" +
                "value=" + value +
                ", index=" + index +
                ", direction='" + direction + '\'' +
                '}';
    }
}
