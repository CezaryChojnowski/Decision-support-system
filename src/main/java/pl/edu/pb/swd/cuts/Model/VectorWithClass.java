package pl.edu.pb.swd.cuts.Model;

import java.util.List;

public class VectorWithClass {
    List<Integer> vector;
    String classifier;

    public List<Integer> getVector() {
        return vector;
    }

    public void setVector(List<Integer> vector) {
        this.vector = vector;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public VectorWithClass(List<Integer> vector, String classifier) {
        this.vector = vector;
        this.classifier = classifier;
    }

    public VectorWithClass() {
    }

    @Override
    public String toString() {
        return "VectorWithClass{" +
                "vector=" + vector +
                ", classifier='" + classifier + '\'' +
                '}';
    }
}
