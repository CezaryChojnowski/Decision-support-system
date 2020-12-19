package pl.edu.pb.swd.cuts.Model;


import java.util.Objects;

public class Row{
    double x;
    double y;
    String classifier;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public Row(double x, double y, String classifier) {
        this.x = x;
        this.y = y;
        this.classifier = classifier;
    }

    @Override
    public String toString() {
        return "Row{" +
                "x=" + x +
                ", y=" + y +
                ", classifier='" + classifier + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return Double.compare(row.x, x) == 0 &&
                Double.compare(row.y, y) == 0 &&
                Objects.equals(classifier, row.classifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, classifier);
    }
}
