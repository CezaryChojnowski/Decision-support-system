package pl.edu.pb.swd.Service;

import org.springframework.stereotype.Service;
import pl.edu.pb.swd.Model.Range;

import java.util.LinkedList;

import static pl.edu.pb.swd.Service.ReusableOperationsService.*;

@Service
public class DiscretizationService {

    final
    ReusableOperationsService reusableOperationsService;

    public DiscretizationService(ReusableOperationsService reusableOperationsService) {
        this.reusableOperationsService = reusableOperationsService;
    }

    public LinkedList<String> discretizationRow(LinkedList<Double> column, LinkedList<Range> rangeLinkedList){
        LinkedList<String> newColumn = new LinkedList<>();
        String newValue;
        int temp = rangeLinkedList.size();
        String tempString = String.valueOf(temp);
        for(Double value : column){
            for(Range range: rangeLinkedList){
                if(!range.getName().equals(tempString)){
                    if(value>= range.getLowerValue() && value<range.getUpperValue()){
                        newValue = range.getName();
                        newColumn.add(newValue);
                    }
                }
                else{
                    if(value>= range.getLowerValue() && value<=range.getUpperValue()){
                        newValue = range.getName();
                        newColumn.add(newValue);
                    }
                }
            }
        }
        return newColumn;
    }

    public LinkedList<Range> createListRange(String columnName, Integer numberOfRanges){
        LinkedList<String> column = reusableOperationsService.getWholeColumnByIndex(columnName);
        LinkedList<Double> columnDouble = convertStringValueToDouble(column);
        Double min = findMinValueInGivenColumn(columnDouble),
                max = findMaxValueInGivenColumn(columnDouble);
        Double subtractionMaxMin = max-min;
        Double rangeValue = subtractionMaxMin/numberOfRanges, tempRangeValue = min+rangeValue;
        LinkedList<Range> rangeLinkedList = new LinkedList<>();
        int customNumberOfRangesException = 2;
        Integer value;
        for(int i=0; i<numberOfRanges; i++){
            value = i + 1;
            if(numberOfRanges!=customNumberOfRangesException) {
                if (i == 0) {
                    rangeLinkedList.add(new Range(min, min + rangeValue, value.toString()));
                } else if (i == numberOfRanges - 1) {
                    rangeLinkedList.add(new Range(max - rangeValue, max, value.toString()));
                } else {
                    rangeLinkedList.add(new Range(tempRangeValue, tempRangeValue + rangeValue, value.toString()));
                    tempRangeValue += rangeValue;
                }
            }
            else{
                rangeLinkedList.add(new Range(min, min+rangeValue, value.toString()));
                value++;
                rangeLinkedList.add(new Range(min+rangeValue, max, value.toString()));
                break;
            }
        }
        return rangeLinkedList;
    }
}
