package pl.edu.pb.swd.dataOperations.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static pl.edu.pb.swd.dataOperations.Service.ReusableOperationsService.findColumnIndex;

@Service
public class TextToNumericService {

    final
    ReadWriteService readWriteService;


    public TextToNumericService(ReadWriteService readWriteService) {
        this.readWriteService = readWriteService;
    }

    public LinkedHashSet<String> findUniqueValuesInGivenColumnInOrderOfAppearance (String columnName){
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        return getAllValueFromGivenColumn(data,findColumnIndex(data.get(0), columnName));
    }

    public TreeSet<String> findUniqueValuesInGivenColumnInAlphabeticalOrder(String columnName){
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        LinkedHashSet<String> valuesFromGivenColumnInOrderOfAppearance = getAllValueFromGivenColumn(data, findColumnIndex(data.get(0), columnName));
        TreeSet<String> valuesFromGivenColumnInAlphabeticalOrder
                = new TreeSet<>(valuesFromGivenColumnInOrderOfAppearance);
        return valuesFromGivenColumnInAlphabeticalOrder;
    }

    public static LinkedHashSet<String> getAllValueFromGivenColumn(LinkedList<LinkedList<String>> data, int columnIndex){
        int numberOfRows = data.size(); //ilość wierszy
        LinkedHashSet<String> valuesFromGivenColumn = new LinkedHashSet();
        for(int i=1; i<numberOfRows; i++){
                valuesFromGivenColumn.add(data.get(i).get(columnIndex));
            }
        return valuesFromGivenColumn;
    }

    public HashMap<String, Integer> createMapWithUniqueValue(Set<String> uniqueValue){
        HashMap<String, Integer> mapWithUniqueValue = new HashMap<>();
        Iterator<String> itr = uniqueValue.iterator();
        int i=1;
        while(itr.hasNext()){
            mapWithUniqueValue.put(itr.next(), i);
            i++;
        }
        return mapWithUniqueValue;
    }

    public LinkedList<LinkedList<String>> convertTextValuesToNumeric(HashMap<String, Integer> mapWithUniqueValue, String newColumnName, int columnIndex){
        LinkedList<LinkedList<String>> listOfList = readWriteService.readDataFromWorkingFile();
        LinkedList<String> nameColumns = listOfList.get(0);
        nameColumns.add(newColumnName);
        listOfList.set(0,nameColumns);
        int numberOfRows = listOfList.size(); //ilość wierszy
        for(int i=1; i<numberOfRows; i++){
            int value = mapWithUniqueValue.get(listOfList.get(i).get(columnIndex));
            LinkedList<String> row = listOfList.get(i);
            row.add(String.valueOf(value));
            listOfList.set(i,row);
        }
        return listOfList;
    }

    public LinkedList<LinkedList<String>> chooseMethodConvertTextValuesToNumeric(String columnName, String newColumnName, boolean alphabetical) throws IOException {
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        int first = 0, indexColumn;
        LinkedList<String> headers= linkedLists.get(first);
        indexColumn = findColumnIndex(headers, columnName);
        if(!alphabetical){
            Set<String> uniqueValues = findUniqueValuesInGivenColumnInOrderOfAppearance(columnName);
            HashMap<String, Integer> uniqueValuesMap = createMapWithUniqueValue(uniqueValues);
            readWriteService.saveToWorkingFile(convertTextValuesToNumeric(uniqueValuesMap, newColumnName, indexColumn));
            return readWriteService.readDataFromWorkingFile();
        }
        else{
            Set<String> uniqueValues = findUniqueValuesInGivenColumnInAlphabeticalOrder(columnName);
            HashMap<String, Integer> uniqueValuesMap = createMapWithUniqueValue(uniqueValues);
            readWriteService.saveToWorkingFile(convertTextValuesToNumeric(uniqueValuesMap, newColumnName, indexColumn));
            return readWriteService.readDataFromWorkingFile();
        }
    }


}
