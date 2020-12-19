package pl.edu.pb.swd.cuts.Model;

import java.util.List;

public class HyperPlane {
    List<Row> rows;
    List<Integer> vector;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Integer> getVector() {
        return vector;
    }

    public void setVector(List<Integer> vector) {
        this.vector = vector;
    }

    public HyperPlane(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "HyperPlane{" +
                "rows=" + rows +
                ", vector=" + vector +
                '}';
    }
}
