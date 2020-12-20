package pl.edu.pb.swd.cuts.Model;

import java.util.LinkedList;

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
}
