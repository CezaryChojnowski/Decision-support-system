package pl.edu.pb.swd.cuts.Model;

public class ResultInfoMultiDimensionalPlane {
    int numberOfObjectsRemoved;
    int numberOfCuts;

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

    public ResultInfoMultiDimensionalPlane(int numberOfObjectsRemoved, int numberOfCuts) {
        this.numberOfObjectsRemoved = numberOfObjectsRemoved;
        this.numberOfCuts = numberOfCuts;
    }

    public ResultInfoMultiDimensionalPlane() {
    }

    @Override
    public String toString() {
        return "ResultInfoMultiDimensionalPlane{" +
                "numberOfObjectsRemoved=" + numberOfObjectsRemoved +
                ", numberOfCuts=" + numberOfCuts +
                '}';
    }
}
