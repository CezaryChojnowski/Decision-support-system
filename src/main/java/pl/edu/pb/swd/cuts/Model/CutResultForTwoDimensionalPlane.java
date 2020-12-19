package pl.edu.pb.swd.cuts.Model;

import java.util.List;

public class CutResultForTwoDimensionalPlane {
    List<Line> lines;
    List<HyperPlane> hyperPlanes;
    int numberOfObjectsRemoved;
    int numberOfCuts;

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<HyperPlane> getHyperPlanes() {
        return hyperPlanes;
    }

    public void setHyperPlanes(List<HyperPlane> hyperPlanes) {
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

    public CutResultForTwoDimensionalPlane(List<Line> lines, List<HyperPlane> hyperPlanes, int numberOfObjectsRemoved, int numberOfCuts) {
        this.lines = lines;
        this.hyperPlanes = hyperPlanes;
        this.numberOfObjectsRemoved = numberOfObjectsRemoved;
        this.numberOfCuts = numberOfCuts;
    }

    public CutResultForTwoDimensionalPlane() {
    }

    @Override
    public String toString() {
        return "CutResultForTwoDimensionalPlane{" +
                "lines=" + lines +
                ", hyperPlanes=" + hyperPlanes +
                ", numberOfObjectsRemoved=" + numberOfObjectsRemoved +
                ", numberOfCuts=" + numberOfCuts +
                '}';
    }
}
