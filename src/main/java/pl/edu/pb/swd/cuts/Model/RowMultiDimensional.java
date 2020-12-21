package pl.edu.pb.swd.cuts.Model;

import java.util.LinkedList;
import java.util.Objects;

public class RowMultiDimensional {
    LinkedList<Double> row;
    String classifier;

    public LinkedList<Double> getRow() {
        return row;
    }

    public void setRow(LinkedList<Double> row) {
        this.row = row;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public RowMultiDimensional(LinkedList<Double> row, String classifier) {
        this.row = row;
        this.classifier = classifier;
    }

    public RowMultiDimensional() {
    }

    @Override
    public String toString() {
        return "RowMultiDimensional{" +
                "row=" + row +
                ", classifier='" + classifier + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowMultiDimensional that = (RowMultiDimensional) o;
        return Objects.equals(row, that.row) &&
                Objects.equals(classifier, that.classifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, classifier);
    }
}
