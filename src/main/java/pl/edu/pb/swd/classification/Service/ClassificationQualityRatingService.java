package pl.edu.pb.swd.classification.Service;

import org.springframework.stereotype.Service;
import pl.edu.pb.swd.classification.Model.QualityRating;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class ClassificationQualityRatingService {

    final MetricService metricService;

    final ReadWriteService readWriteService;

    public ClassificationQualityRatingService(MetricService metricService, ReadWriteService readWriteService) {
        this.metricService = metricService;
        this.readWriteService = readWriteService;
    }

    public LinkedList<QualityRating> getClassificationQualityRating(Integer method) {
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        LinkedList<Integer> indexesNumericColumn = metricService.getIndexesNumericColumn(metricService.getNumericHeaders());
        LinkedList<Double> quality = new LinkedList<>();
        LinkedList<QualityRating> temp = new LinkedList<>();
        int count;
        double correctResult, all = (linkedLists.size());
        for(int i=1; i<all; i++) {
            count=-1;
            correctResult = 0.0;
            for (LinkedList<String> row : linkedLists) {
                count++;
                LinkedList<String> newObject = new LinkedList<>();
                if (row.equals(linkedLists.getFirst())) {
                    continue;
                }
                for (Integer index : indexesNumericColumn) {
                    newObject.add(row.get(index));
                }
                Map<Integer, Double> newOb = metricService.
                        createMapWithIndexAndValueNewObject(
                                indexesNumericColumn,
                                newObject);
                LinkedList<Double> temp2 = metricService.getVectorOfDistance(newOb, method, count);
                LinkedHashMap<Integer, Double> mapOfDistance = metricService.listOfDistanceToMapWithIndex(temp2, count);
                LinkedList<Integer> indexOfTheClosesNeighbors = metricService.getIndexesOfTheClosestNeighbors(metricService.getGivenElementsByNeighborsValue(mapOfDistance, i));
                LinkedHashMap<String, Long> result = metricService.getFrequencyOfResult(metricService.getAllDecisionValueFromDistanceVector(indexOfTheClosesNeighbors));
                String decision = metricService.getDecision(result);
                if (decision.equals(row.getLast())) {
                    correctResult++;
                }
            }
            quality.add((correctResult / (all-1)));
            temp.add(new QualityRating(i, quality.get(i-1)));
        }
        return temp;

    }
}
