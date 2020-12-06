package pl.edu.pb.swd.grouping.Service;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.springframework.stereotype.Service;
import pl.edu.pb.swd.classification.Service.MetricService;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;


import java.io.IOException;
import java.util.*;

@Service
public class GroupingService {

    final ReadWriteService readWriteService;

    final MetricService metricService;

    public GroupingService(ReadWriteService readWriteService, MetricService metricService) {
        this.readWriteService = readWriteService;
        this.metricService = metricService;
    }

    public LinkedList<LinkedList<String>> addClassToObject(LinkedList<LinkedList<String>> linkedLists, Integer method, LinkedList<LinkedList<String>> newObject) {
        for (int i = 1; i < linkedLists.size(); i++) {
            LinkedList<String> currentObject = linkedLists.get(i);
            int sizeNewObject = newObject.getFirst().size();
            LinkedList<String> currentObjectTemp = new LinkedList<>();
            for(int j=0; j<sizeNewObject; j++){
                currentObjectTemp.add(linkedLists.get(i).get(j));
            }
            Map<Integer, Double> newOb = metricService.createMapWithIndexAndValueNewObject(metricService.getIndexesNumericColumn(metricService.getNumericHeaders()), currentObjectTemp);
            LinkedList<Double> distanceVector = getVectorOfDistance(newOb, method, newObject);
            LinkedHashMap<Integer, Double> mapOfDistance = listOfDistanceToMapWithIndex(distanceVector);
            LinkedList<Integer> indexOfTheClosesNeighbors = metricService.getIndexesOfTheClosestNeighbors(metricService.getGivenElementsByNeighborsValue(mapOfDistance, 1));
            String decision = String.valueOf(indexOfTheClosesNeighbors.getFirst() + 1);
            linkedLists.get(i).add(decision);
        }
        return linkedLists;
    }

    public LinkedHashMap<Integer, Double> getDistinctClass(LinkedList<LinkedList<String>> linkedLists) {
        LinkedHashMap<Integer, Double> distance = new LinkedHashMap<>();
        for (int i = 1; i < linkedLists.size(); i++) {
            try {
                distance.put(Integer.parseInt(linkedLists.get(i).getLast()), Double.valueOf(linkedLists.get(i).getLast()));
            } catch (NumberFormatException numberFormatException) {
                continue;
            }
        }

        LinkedHashMap<Integer, Double> sortByDistance = new LinkedHashMap<>();
        distance.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortByDistance.put(x.getKey(), x.getValue()));
        return sortByDistance;
    }

    public LinkedList<LinkedList<String>> createNewObjectsByAverage(Map<Integer, Double> tempObject, LinkedHashMap<Integer, Double> distance, LinkedList<LinkedList<String>> resultAfterAddClasses) {
        LinkedList<LinkedList<String>> tempObj = new LinkedList<>();
        for (Map.Entry<Integer, Double> entry : distance.entrySet()) {
            LinkedList<String> newObjectByNewClass = new LinkedList<>();
            for (int j = 0; j < tempObject.size(); j++) {
                LinkedList<Double> valueInColumn = new LinkedList<>();
                for (int i = 1; i < resultAfterAddClasses.size(); i++) {
                    try {
                        if (Double.valueOf(resultAfterAddClasses.get(i).getLast()).equals(entry.getValue())) {
                            valueInColumn.add(Double.valueOf(resultAfterAddClasses.get(i).get(j)));
                        }
                    } catch (NumberFormatException numberFormatException) {
                        continue;
                    }
                }
                double sum = valueInColumn.stream().mapToDouble(Double::doubleValue).sum();
                double average = sum / valueInColumn.size();
                newObjectByNewClass.add(String.valueOf(average));
            }
            newObjectByNewClass.add(String.valueOf(entry.getValue()));
            tempObj.add(newObjectByNewClass);
        }
        return tempObj;
    }


    public LinkedList<LinkedList<String>> grouping(Integer method, LinkedList<LinkedList<String>> newObject, String columnName) throws IOException {
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        LinkedList<LinkedList<String>> resultAfterAddClasses = addClassToObject(linkedLists, method, newObject);
        int countChange;
        Map<Integer, Double> tempObject = metricService.createMapWithIndexAndValueNewObject(metricService.getIndexesNumericColumn(metricService.getNumericHeaders()), linkedLists.getLast());
        int nextColumn = resultAfterAddClasses.getFirst().size();
        do {
            countChange = 0;
            LinkedHashMap<Integer, Double> distance = getDistinctClass(resultAfterAddClasses);

            LinkedList<LinkedList<String>> tempObj = createNewObjectsByAverage(tempObject, distance, resultAfterAddClasses);

            for (int i = 1; i < resultAfterAddClasses.size(); i++) {
                LinkedList<String> currentObject = linkedLists.get(i);
                int sizeNewObject = newObject.getFirst().size();
                LinkedList<String> currentObjectTemp = new LinkedList<>();
                for(int j=0; j<sizeNewObject; j++){
                    currentObjectTemp.add(currentObject.get(j));
                }
                Map<Integer, Double> newOb = metricService.createMapWithIndexAndValueNewObject(metricService.getIndexesNumericColumn(metricService.getNumericHeaders()), currentObjectTemp);
                LinkedList<Double> distanceVector = getVectorOfDistance(newOb, method, tempObj);
                LinkedHashMap<Integer, Double> mapOfDistance = listOfDistanceToMapWithIndex(distanceVector);
                LinkedList<Integer> indexOfTheClosesNeighbors = metricService.getIndexesOfTheClosestNeighbors(metricService.getGivenElementsByNeighborsValue(mapOfDistance, 1));
                String decision = String.valueOf(indexOfTheClosesNeighbors.getFirst() + 1);
                if (!resultAfterAddClasses.get(i).getLast().equals(decision)) {
                    countChange++;
                }
                resultAfterAddClasses.get(i).set(nextColumn, decision);
            }
        } while (countChange > 0);
        resultAfterAddClasses.getFirst().add(columnName);
        readWriteService.saveToWorkingFile(resultAfterAddClasses);
        return resultAfterAddClasses;
    }

    public LinkedHashMap<Integer, Double> listOfDistanceToMapWithIndex(LinkedList<Double> distance) {
        Map<Integer, Double> integerDoubleMap = new HashMap<>();
        for (int k = 0, i = 0; i < distance.size(); k++, i++) {
            integerDoubleMap.put(i, Double.parseDouble(String.valueOf(distance.get(k))));
        }
        LinkedHashMap<Integer, Double> sortByDistance = new LinkedHashMap<>();

        integerDoubleMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortByDistance.put(x.getKey(), x.getValue()));
        return sortByDistance;
    }

    public LinkedList<Double> getVectorOfDistance(Map<Integer, Double> indexesAndValuesNewObject, Integer method, LinkedList<LinkedList<String>> newObject) {
        int numberOfRows = newObject.size();
        LinkedList<Double> distance = new LinkedList<>();
        double sum = 0;
        double currentValue, currentValueNewObject;
        for (int i = 0; i < numberOfRows; i++) {
            sum = 0;
            List<String> row = newObject.get(i);
            LinkedList<Double> doubleLinkedList = new LinkedList<>();
            LinkedList<Double> currentValueList = new LinkedList<>();
            LinkedList<Double> currentValueNewObjectList = new LinkedList<>();
            LinkedList<Double> vector = new LinkedList<>();
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
                    case 4:
                        currentValueList.add(currentValue);
                        currentValueNewObjectList.add(currentValueNewObject);
                        vector.add(currentValue - currentValueNewObject);
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
                case 4:
                    double[][] currentAndNewValue = new double[2][indexesAndValuesNewObject.size()];
                    double[][] vectorTemp = new double[1][indexesAndValuesNewObject.size()];
                    for (int k = 0; k < 2; k++) {
                        for (int j = 0; j < indexesAndValuesNewObject.size(); j++) {
                            if (k == 0) {
                                currentAndNewValue[k][j] = currentValueList.get(j);
                                vectorTemp[k][j] = vector.get(j);
                            } else {
                                currentAndNewValue[k][j] = currentValueNewObjectList.get(j);
                            }
                        }
                    }
                    RealMatrix C = new Covariance(currentAndNewValue).getCovarianceMatrix();
                    RealMatrix subtract = MatrixUtils.createRealMatrix(vectorTemp);
                    distance.add(Math.sqrt((subtract.multiply(C.multiply(subtract.transpose()))).getEntry(0, 0)));
                    break;
                default:
            }
        }
        return distance;
    }
}
