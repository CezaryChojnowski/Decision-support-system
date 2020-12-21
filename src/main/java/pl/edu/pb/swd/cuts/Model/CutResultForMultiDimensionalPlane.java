package pl.edu.pb.swd.cuts.Model;

import java.util.List;
import java.util.Objects;

public class CutResultForMultiDimensionalPlane {
    List<Cut> cuts;
    List<HyperPlaneMoreThan2D> hyperPlanes;
    int numberOfObjectsRemoved;
    int numberOfCuts;

    public CutResultForMultiDimensionalPlane(List<Cut> cuts, List<HyperPlaneMoreThan2D> hyperPlanes, int numberOfObjectsRemoved, int numberOfCuts) {
        this.cuts = cuts;
        this.hyperPlanes = hyperPlanes;
        this.numberOfObjectsRemoved = numberOfObjectsRemoved;
        this.numberOfCuts = numberOfCuts;
    }

    public List<Cut> getCuts() {
        return cuts;
    }

    public void setCuts(List<Cut> cuts) {
        this.cuts = cuts;
    }

    public List<HyperPlaneMoreThan2D> getHyperPlanes() {
        return hyperPlanes;
    }

    public void setHyperPlanes(List<HyperPlaneMoreThan2D> hyperPlanes) {
        this.hyperPlanes = hyperPlanes;
    }

    public int getNumberOfObjectsRemoved() {
        return numberOfObjectsRemoved;
    }

    public void setNumberOfObjectsRemoved(int numberOfObjectsRemoved) {
        this.numberOfObjectsRemoved = numberOfObjectsRemoved;
    }

    public int getNumberOfCuts() {
        return numberOfCuts;
    }

    public void setNumberOfCuts(int numberOfCuts) {
        this.numberOfCuts = numberOfCuts;
    }

    public CutResultForMultiDimensionalPlane() {
    }

    @Override
    public String toString() {
        return "CutResultForMultiDimensionalPlane{" +
                "cuts=" + cuts +
                ", hyperPlanes=" + hyperPlanes +
                ", numberOfObjectsRemoved=" + numberOfObjectsRemoved +
                ", numberOfCuts=" + numberOfCuts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CutResultForMultiDimensionalPlane that = (CutResultForMultiDimensionalPlane) o;
        return numberOfObjectsRemoved == that.numberOfObjectsRemoved &&
                numberOfCuts == that.numberOfCuts &&
                Objects.equals(cuts, that.cuts) &&
                Objects.equals(hyperPlanes, that.hyperPlanes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuts, hyperPlanes, numberOfObjectsRemoved, numberOfCuts);
    }
}
