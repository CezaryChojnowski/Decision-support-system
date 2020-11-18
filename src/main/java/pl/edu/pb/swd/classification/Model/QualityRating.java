package pl.edu.pb.swd.classification.Model;


public class QualityRating {
    Integer numberOfNeighbors;
    Double quality;

    public Integer getNumberOfNeighbors() {
        return numberOfNeighbors;
    }

    public void setNumberOfNeighbors(Integer numberOfNeighbors) {
        this.numberOfNeighbors = numberOfNeighbors;
    }

    public Double getQuality() {
        return quality;
    }

    public void setQuality(Double quality) {
        this.quality = quality;
    }

    public QualityRating(Integer numberOfNeighbors, Double quality) {
        this.numberOfNeighbors = numberOfNeighbors;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "QualityRating{" +
                "numberOfNeighbors=" + numberOfNeighbors +
                ", quality=" + quality +
                '}';
    }
}
