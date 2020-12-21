package pl.edu.pb.swd.cuts.Model;

import java.util.LinkedList;
import java.util.List;

public class HyperPlaneMoreThan2D {
    List<RowMultiDimensional> rows;
    List<Integer> vector;
    Cut cut;

    public List<RowMultiDimensional> getRows() {
        return rows;
    }

    public void setRows(List<RowMultiDimensional> rows) {
        this.rows = rows;
    }

    public List<Integer> getVector() {
        return vector;
    }

    public void setVector(List<Integer> vector) {
        this.vector = vector;
    }

    public HyperPlaneMoreThan2D(List<RowMultiDimensional> rows, List<Integer> vector) {
        this.rows = rows;
        this.vector = vector;
    }

    public HyperPlaneMoreThan2D() {
    }

    public HyperPlaneMoreThan2D(List<RowMultiDimensional> rows) {
        this.rows = rows;
    }

    public Cut getCut() {
        return cut;
    }

    public void setCut(Cut cut) {
        this.cut = cut;
    }

    @Override
    public String toString() {
        return "HyperPlaneMoreThan2D{" +
                "rows=" + rows +
                ", vector=" + vector +
                ", cut=" + cut +
                '}';
    }
}
