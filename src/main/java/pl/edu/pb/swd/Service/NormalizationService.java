package pl.edu.pb.swd.Service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

import static pl.edu.pb.swd.Service.ReusableOperationsService.calculateAverageColumnValue;
import static pl.edu.pb.swd.Service.ReusableOperationsService.calculateStdColumnValue;

@Service
public class NormalizationService {

    public LinkedList<String> normalizationRow(LinkedList<Double> column){
        LinkedList<String> newColumn = new LinkedList<>();
        Double newValue,
                average = calculateAverageColumnValue(column),
                std = calculateStdColumnValue(column, average);
        for(Double value : column){
            newValue = (value-average)/std;
            newColumn.add(newValue.toString());
        }
        return newColumn;
    }
}
