package pl.edu.pb.swd.classification.Service;

import org.springframework.stereotype.Service;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.Math;


@Service
public class MetricService {

    final ReadWriteService readWriteService;

    public MetricService(ReadWriteService readWriteService) {
        this.readWriteService = readWriteService;
    }

    public LinkedList<String> getNumericHeaders(){
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        int firstNoHeaderRowIndex = 1, headersRowIndex = 0, sizeRow;
        LinkedList<String> headers = data.get(headersRowIndex);
        LinkedList<String> numericColumnName = new LinkedList<>();
        LinkedList<String> firstNoHeaderRow = data.get(firstNoHeaderRowIndex);
        sizeRow = headers.size();
        for(int i=0; i<sizeRow; i++){
            if(i==sizeRow-1){
                break;
            }
            else if(isNumeric(firstNoHeaderRow.get(i))){
                numericColumnName.add(headers.get(i));
            }
        }
        return numericColumnName;
    }

    public LinkedList<Integer> getIndexesNumericColumn(LinkedList<String> linkedList){
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        int headersRowIndex = 0, sizeRow;
        LinkedList<String> headers = data.get(headersRowIndex);
        sizeRow = linkedList.size();
        LinkedList<Integer> indexes = new LinkedList<>();
        for(int i=0; i<sizeRow; i++){
            indexes.add(headers.indexOf(headers.get(i)));
        }
        return indexes;
    }

    public boolean isNumeric(String stringNum){
        if(stringNum==null){
            return false;
        }
        try {
            Double.parseDouble(stringNum);
            return true;
        }catch (NumberFormatException numberFormatException){
            return false;
        }
    }

    public Map<Integer, Double> createMapWithIndexAndValueNewObject(LinkedList<Integer> indexes, LinkedList<String> values){
        Map<Integer, Double> indexesAndValuesNewObject = new LinkedHashMap<>();
        int size = indexes.size();
        for(int i=0; i<size; i++){
            indexesAndValuesNewObject.put(indexes.get(i), Double.parseDouble(values.get(i)));
        }
        return indexesAndValuesNewObject;
    }

    public LinkedList<Double> getVectorOfDistance(Map<Integer, Double> indexesAndValuesNewObject){
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        int numberOfRows = linkedLists.size();
        LinkedList<Double> distance = new LinkedList<>();
        double sum =0;
        for(int i=1; i<numberOfRows; i++){
            sum=0;
            List<String> row = linkedLists.get(i);
            for (Map.Entry<Integer,Double> entry : indexesAndValuesNewObject.entrySet()){
                double currentValue = Double.parseDouble(row.get(entry.getKey()));
                double currentValueNewObject = entry.getValue();
                sum=sum + Math.pow(currentValue-currentValueNewObject, 2);
            }
            distance.add(sum);
        }
        return distance;
    }
}
