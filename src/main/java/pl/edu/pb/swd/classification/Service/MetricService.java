package pl.edu.pb.swd.classification.Service;
import org.springframework.stereotype.Service;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

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

    public LinkedList<Double> getVectorOfDistance(Map<Integer, Double> indexesAndValuesNewObject, Integer method) {
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        int numberOfRows = linkedLists.size();
        LinkedList<Double> distance = new LinkedList<>();
        double sum = 0;
        double currentValue, currentValueNewObject;
        for (int i = 1; i < numberOfRows; i++) {
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
}
