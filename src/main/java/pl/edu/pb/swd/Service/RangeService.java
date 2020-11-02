package pl.edu.pb.swd.Service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

import static pl.edu.pb.swd.Service.ReusableOperationsService.*;

@Service
public class RangeService {

    final
    ReadWriteService readWriteService;

    public RangeService(ReadWriteService readWriteService) {
        this.readWriteService = readWriteService;
    }

    public LinkedList<String> createNewColumnWithChangedRangeOfValuesByMinMaxNormalizationMethod(LinkedList<Double> column, Double newMin, Double newMax){
        LinkedList<String> newColumn = new LinkedList<>();
        Double newValue, oldMin = findMinValueInGivenColumn(column), oldMax = findMaxValueInGivenColumn(column);
        for(Double value: column){
            newValue = ((value - oldMin)/(oldMax - oldMin))*(newMax-newMin)+newMin;
            newColumn.add(newValue.toString());
        }
        return newColumn;
    }

    public LinkedList<String> createNewColumnWithChangedRangeOfValuesByZScoreMethod(LinkedList<Double> column){
        LinkedList<String> newColumn = new LinkedList<>();
        Double newValue,
                average = calculateAverageColumnValue(column),
                std = calculateStdColumnValue(column, average);
        for(Double value: column){
            newValue = (value - average)/ std;
            newColumn.add(newValue.toString());
        }
        return newColumn;
    }
}
