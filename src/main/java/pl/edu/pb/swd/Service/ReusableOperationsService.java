package pl.edu.pb.swd.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReusableOperationsService {

    final
    ReadWriteService readWriteService;

    public ReusableOperationsService(ReadWriteService readWriteService) {
        this.readWriteService = readWriteService;
    }

    public static LinkedList<Double> convertStringValueToDouble(LinkedList<String> column){
        LinkedList<Double> convertedListFromStringToDouble = new LinkedList<>();
        for(String value : column){
            convertedListFromStringToDouble.add(Double.valueOf(value));
        }
        return convertedListFromStringToDouble;
    }

    public static int findColumnIndex(List<String> columnsList, String columnName){
        return columnsList.indexOf(columnName);
    }

    public static Double calculateAverageColumnValue(LinkedList<Double> column){
        Double sum = 0.0;
        for(Double value: column){
            sum+=value;
        }
        return sum / column.size();
    }

    public static Double calculateStdColumnValue(LinkedList<Double> column, Double average){
        Double sum = 0.0;
        for(Double value: column){
            sum = sum + Math.pow(value-average, 2);
        }
        return Math.pow((sum/column.size()), 0.5);
    }

    public LinkedList<String> getWholeColumnByIndex(String columnName){
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        Integer firstIndex = 0;
        Integer columnIndex = findColumnIndex(data.get(firstIndex), columnName);
        data.remove();
        LinkedList<String> column = new LinkedList<>();
        for(LinkedList<String> row: data){
            column.add(row.get(columnIndex));
        }
        return column;
    }

    public static double findMinValueInGivenColumn(LinkedList<Double> column){
        return Collections.min(column);
    }

    public static double findMaxValueInGivenColumn(LinkedList<Double> column){
        return Collections.max(column);
    }

    public LinkedList<LinkedList<String>> mergingExistingColumnsWithNewColumn(String nameNewColumn, LinkedList<String> newColumn) throws IOException {
        LinkedList<LinkedList<String>> listLinkedList = readWriteService.readDataFromWorkingFile();
        LinkedList<String> nameColumns = listLinkedList.get(0);
        nameColumns.add(nameNewColumn);
        listLinkedList.set(0,nameColumns);
        int numberOfRows = listLinkedList.size(); //ilość wierszy
        for(int i=1; i<numberOfRows; i++){
            String value = newColumn.get(i-1).toString();
            LinkedList<String> row = listLinkedList.get(i);
            row.add(value);
            listLinkedList.set(i,row);
        }
        readWriteService.saveToWorkingFile(listLinkedList);
        return listLinkedList;
    }
}
