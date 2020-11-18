package pl.edu.pb.swd.classification.Service;

import org.springframework.stereotype.Service;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

import java.io.IOException;
import java.util.*;
import java.lang.Math;


@Service
public class MetricService {

    final ReadWriteService readWriteService;

    public MetricService(ReadWriteService readWriteService) {
        this.readWriteService = readWriteService;
    }

    public LinkedList<String> getNumericHeaders() {
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        int firstNoHeaderRowIndex = 1, headersRowIndex = 0, sizeRow;
        LinkedList<String> headers = data.get(headersRowIndex);
        LinkedList<String> numericColumnName = new LinkedList<>();
        LinkedList<String> firstNoHeaderRow = data.get(firstNoHeaderRowIndex);
        sizeRow = headers.size();
        for (int i = 0; i < sizeRow; i++) {
            if (i == sizeRow - 1) {
                break;
            } else if (isNumeric(firstNoHeaderRow.get(i))) {
                numericColumnName.add(headers.get(i));
            }
        }
        return numericColumnName;
    }

    public LinkedList<Integer> getIndexesNumericColumn(LinkedList<String> linkedList) {
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        int headersRowIndex = 0, sizeRow;
        LinkedList<String> headers = data.get(headersRowIndex);
        sizeRow = linkedList.size();
        LinkedList<Integer> indexes = new LinkedList<>();
        for (int i = 0; i < sizeRow; i++) {
            indexes.add(headers.indexOf(headers.get(i)));
        }
        return indexes;
    }

    public boolean isNumeric(String stringNum) {
        if (stringNum == null) {
            return false;
        }
        try {
            Double.parseDouble(stringNum);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public Map<Integer, Double> createMapWithIndexAndValueNewObject(LinkedList<Integer> indexes, LinkedList<String> values) {
        Map<Integer, Double> indexesAndValuesNewObject = new LinkedHashMap<>();
        int size = indexes.size();
        for (int i = 0; i < size; i++) {
            indexesAndValuesNewObject.put(indexes.get(i), Double.parseDouble(values.get(i)));
        }
        return indexesAndValuesNewObject;
    }

    public LinkedList<Double> getVectorOfDistance(Map<Integer, Double> indexesAndValuesNewObject, Integer method, Integer count) {
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        int numberOfRows = linkedLists.size();
        LinkedList<Double> distance = new LinkedList<>();
        double sum = 0;
        double currentValue, currentValueNewObject;
        for (int i = 1; i < numberOfRows; i++) {
            if (count != -1 && count == i) {
                continue;
            }
            sum = 0;
            List<String> row = linkedLists.get(i);
            LinkedList<Double> doubleLinkedList = new LinkedList<>();
            for (Map.Entry<Integer, Double> entry : indexesAndValuesNewObject.entrySet()) {
                currentValue = Double.parseDouble(row.get(entry.getKey()));
                currentValueNewObject = Double.parseDouble(String.valueOf(entry.getValue()));
                switch (method) {
                    case 1:
                        sum = sum + Math.pow(currentValue - currentValueNewObject, 2);
                        break;
                    case 2:
                        sum = sum + Math.abs(currentValue - currentValueNewObject);
                        break;
                    case 3:
                        doubleLinkedList.add(Math.abs(currentValue - currentValueNewObject));
                        break;
                    default:
                }
            }
            switch (method) {
                case 1:
                case 2:
                    distance.add(Math.round(sum * 100.0) / 100.0);
                    break;
                case 3:
                    distance.add(Collections.max(doubleLinkedList));
                    break;
                default:
            }
        }
        return distance;
    }

    public LinkedHashMap<Integer, Double> listOfDistanceToMapWithIndex(LinkedList<Double> distance, Integer count) {
        Map<Integer, Double> integerDoubleMap = new HashMap<>();
        for (int k = 0, i = 1; i < distance.size(); k++, i++) {
            if (count != -1 && count == i) {
                continue;
            }
            integerDoubleMap.put(i, Double.parseDouble(String.valueOf(distance.get(k))));
        }
        LinkedHashMap<Integer, Double> sortByDistance = new LinkedHashMap<>();

        integerDoubleMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortByDistance.put(x.getKey(), x.getValue()));
        return sortByDistance;
    }

    public LinkedHashMap<Integer, Double> getGivenElementsByNeighborsValue(LinkedHashMap<Integer, Double> mapOfDistance, Integer neighbors) {
        LinkedHashMap<Integer, Double> result = new LinkedHashMap<>();
        int i = 0;
        for (Map.Entry<Integer, Double> entry : mapOfDistance.entrySet()) {
            if (i == neighbors) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
            i++;
        }
        return result;
    }

    public LinkedList<Integer> getIndexesOfTheClosestNeighbors(LinkedHashMap<Integer, Double> mapOfIndexAndDistanceClosestNeighbors) {
        LinkedList<Integer> result = new LinkedList<>();
        for (Map.Entry<Integer, Double> entry : mapOfIndexAndDistanceClosestNeighbors.entrySet()) {
            result.add(entry.getKey());
        }
        return result;
    }

    public LinkedList<String> getAllDecisionValueFromDistanceVector(LinkedList<Integer> indexOfTheClosestNeighbors) {
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        LinkedList<String> result = new LinkedList<>();
        int lastDecisionColumn = linkedLists.getFirst().size() - 1;
        for (Integer index : indexOfTheClosestNeighbors) {
            result.add(linkedLists.get(index).get(lastDecisionColumn));
        }
        return result;
    }

    public LinkedHashMap<String, Long> getFrequencyOfResult(LinkedList<String> wordsList) {
        HashMap<String, Long> frequencyMap = new HashMap<>();
        for (String s : wordsList) {
            Long count = frequencyMap.get(s);
            if (count == null)
                count = 0L;

            frequencyMap.put(s, count + 1);
        }
        LinkedHashMap<String, Long> sortByDistance = new LinkedHashMap<>();

        frequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortByDistance.put(x.getKey(), x.getValue()));

        return sortByDistance;
    }

    public String getDecision(LinkedHashMap<String, Long> frequencyOfResult) {
        Long max = frequencyOfResult.entrySet().iterator().next().getValue();
        int frequency = 0;
        LinkedList<String> result = new LinkedList<>();
        for (Map.Entry<String, Long> entry : frequencyOfResult.entrySet()) {
            if (entry.getValue().equals(max)) {
                frequency++;
            }
        }
        if (frequency == 1) {
            return frequencyOfResult.entrySet().iterator().next().getKey();
        } else {
            int i = 0;
            for (Map.Entry<String, Long> entry : frequencyOfResult.entrySet()) {
                if (i == frequency) {
                    break;
                }
                result.add(entry.getKey());
                i++;
            }
            return result.get(new Random().nextInt(frequency));
        }
    }


    public String getDecisionClass(Integer neighbors, Integer method, LinkedList<String> newObject) throws IOException {
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        Map<Integer, Double> newOb = createMapWithIndexAndValueNewObject(getIndexesNumericColumn(getNumericHeaders()), newObject);
        LinkedHashMap<Integer, Double> mapOfDistance = listOfDistanceToMapWithIndex(getVectorOfDistance(newOb, method, -1), -1);
        LinkedList<Integer> indexOfTheClosesNeighbors = getIndexesOfTheClosestNeighbors(getGivenElementsByNeighborsValue(mapOfDistance, neighbors));
        LinkedHashMap<String, Long> result = getFrequencyOfResult(getAllDecisionValueFromDistanceVector(indexOfTheClosesNeighbors));
        String decision = getDecision(result);
        newObject.add(decision);
        linkedLists.add(newObject);
        readWriteService.saveToWorkingFile(linkedLists);
        return getDecision(result);
    }
}
