package pl.edu.pb.swd.dataOperations.Service;

import org.springframework.stereotype.Service;
import pl.edu.pb.swd.dataOperations.Model.Range;

import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeSet;

import static pl.edu.pb.swd.dataOperations.Service.ReusableOperationsService.convertStringValueToDouble;

@Service
public class DataForChartsService {

    final ReusableOperationsService reusableOperationsService;

    final TextToNumericService textToNumericService;

    final DiscretizationService discretizationService;

    public DataForChartsService(ReusableOperationsService reusableOperationsService, TextToNumericService textToNumericService, DiscretizationService discretizationService) {
        this.reusableOperationsService = reusableOperationsService;
        this.textToNumericService = textToNumericService;
        this.discretizationService = discretizationService;
    }

    public LinkedList<LinkedList<String>> getDataTo2dChart(String firstColumnName,
                                               String secondColumnName,
                                               String thirdColumnName){
        LinkedList<String> firstColumn = reusableOperationsService.getWholeColumnByIndex(firstColumnName);
        LinkedList<String> secondColumn = reusableOperationsService.getWholeColumnByIndex(secondColumnName);
        LinkedList<LinkedList<String>> linkedLists = new LinkedList<>();
        linkedLists.addAll(Collections.singleton(firstColumn));
        linkedLists.addAll(Collections.singleton(secondColumn));
        if( thirdColumnName!=null && !thirdColumnName.isEmpty()){
            LinkedList<String> thirdColumn = reusableOperationsService.getWholeColumnByIndex(thirdColumnName);
            linkedLists.addAll(Collections.singleton(thirdColumn));
        }
        return linkedLists;
    }

    public LinkedList<LinkedList<String>> getDataToHistogram(String columnName,
                                                             boolean discreteVariable,
                                                             Integer numberOfRanges){
        LinkedList<String> column = reusableOperationsService.getWholeColumnByIndex(columnName);
        LinkedList<LinkedList<String>> result = new LinkedList<>();
        if(discreteVariable){
            TreeSet<String> uniqueValue = textToNumericService.findUniqueValuesInGivenColumnInAlphabeticalOrder(columnName);
            LinkedList<String> uniqueValueLinkedList = new LinkedList<>(uniqueValue);
            LinkedList<String> countUniqueValues = new LinkedList<>();
            for(String uniqueValue1: uniqueValueLinkedList){
                int count = 0 ;
                for(String value: column){
                    if(value.equals(uniqueValue1)){
                        count++;
                    }
                }
                countUniqueValues.add(Integer.toString(count));
            }
            result.addAll(Collections.singleton(uniqueValueLinkedList));
            result.addAll(Collections.singleton(countUniqueValues));
        }
        else{
            LinkedList<Double> columnDouble = convertStringValueToDouble(column);
            LinkedList<Range> rangeLinkedList = discretizationService.createListRange(columnName, numberOfRanges);
            LinkedList<String> resultRange = new LinkedList<>();
            LinkedList<String> countValues = new LinkedList<>();
            int i=0;
            char beginningOfaClosedSet = '<',
                    endOfClosedSet = '>',
                    endOfOpenSet = ')',
                    betweenExtremesOfSet = ';';
            for(Range range: rangeLinkedList){
                String rangeString = "";
                if(i!=rangeLinkedList.size()-1) {
                    rangeString = String.valueOf(beginningOfaClosedSet) + range.getLowerValue() + (betweenExtremesOfSet) + range.getUpperValue() + (endOfOpenSet);
                }
                else{
                    rangeString = String.valueOf(beginningOfaClosedSet) + range.getLowerValue() + (betweenExtremesOfSet) + range.getUpperValue() + (endOfClosedSet);
                }
                resultRange.add(rangeString);
                int count = 0 ;
                for(Double value: columnDouble){
                    if(i!=rangeLinkedList.size()-1){
                        if(value >= range.getLowerValue() && value <range.getUpperValue()){
                            count++;
                        }
                    }
                    else {
                        if(value >= range.getLowerValue() && value <=range.getUpperValue()){
                            count++;
                        }
                    }
                }
                countValues.add(Integer.toString(count));
                i++;
            }
            result.addAll(Collections.singleton(resultRange));
            result.addAll(Collections.singleton(countValues));
        }
        return result;
    }

}
