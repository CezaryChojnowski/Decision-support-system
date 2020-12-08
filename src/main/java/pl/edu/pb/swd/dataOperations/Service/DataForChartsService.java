package pl.edu.pb.swd.dataOperations.Service;

import org.springframework.stereotype.Service;
import pl.edu.pb.swd.dataOperations.Model.Range;

import java.util.*;

import static pl.edu.pb.swd.dataOperations.Service.ReusableOperationsService.convertStringValueToDouble;
import static pl.edu.pb.swd.dataOperations.Service.ReusableOperationsService.findColumnIndex;

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
                                                           String thirdColumnName) {
        if (thirdColumnName != null && !thirdColumnName.isEmpty()) {
            LinkedList<LinkedList<String>> linkedLists = new LinkedList<>();
            LinkedList<LinkedList<String>> data = reusableOperationsService.readWriteService.readDataFromWorkingFile();
            int firstColumnIndex = findColumnIndex(data.getFirst(), firstColumnName);
            int secondColumnIndex = findColumnIndex(data.getFirst(), secondColumnName);
            Integer columnIndex = findColumnIndex(data.getFirst(), thirdColumnName);
            data.remove();
            HashSet<String> decisionClasses = new HashSet<>();
            for (LinkedList<String> row : data) {
                decisionClasses.add(row.get(columnIndex));
            }
            TreeSet decisionClassesSet = new TreeSet();
            decisionClassesSet.addAll(decisionClasses);
            Iterator<String> itr = decisionClassesSet.iterator();
            while (itr.hasNext()) {
                LinkedList<String> firstColumn = new LinkedList<>();
                LinkedList<String> secondColumn = new LinkedList<>();
                String temp = itr.next();
                for (int i = 0; i < data.size(); i++) {
                    if (temp.equals(data.get(i).get(columnIndex))) {
                        firstColumn.add(data.get(i).get(firstColumnIndex));
                        secondColumn.add(data.get(i).get(secondColumnIndex));
                    }
                }
                linkedLists.addAll(Collections.singleton(firstColumn));
                linkedLists.addAll(Collections.singleton(secondColumn));
            }
            return linkedLists;
        }
        LinkedList<String> firstColumn = reusableOperationsService.getWholeColumnByIndex(firstColumnName);
        LinkedList<String> secondColumn = reusableOperationsService.getWholeColumnByIndex(secondColumnName);
        LinkedList<LinkedList<String>> linkedLists = new LinkedList<>();
        linkedLists.addAll(Collections.singleton(firstColumn));
        linkedLists.addAll(Collections.singleton(secondColumn));
        return linkedLists;
    }

    public LinkedList<LinkedList<String>> getDataToHistogram(String columnName,
                                                             boolean discreteVariable,
                                                             Integer numberOfRanges) {
        LinkedList<String> column = reusableOperationsService.getWholeColumnByIndex(columnName);
        LinkedList<LinkedList<String>> result = new LinkedList<>();
        if (discreteVariable) {
            TreeSet<String> uniqueValue = textToNumericService.findUniqueValuesInGivenColumnInAlphabeticalOrder(columnName);
            LinkedList<String> uniqueValueLinkedList = new LinkedList<>(uniqueValue);
            LinkedList<String> countUniqueValues = new LinkedList<>();
            for (String uniqueValue1 : uniqueValueLinkedList) {
                int count = 0;
                for (String value : column) {
                    if (value.equals(uniqueValue1)) {
                        count++;
                    }
                }
                countUniqueValues.add(Integer.toString(count));
            }
            result.addAll(Collections.singleton(uniqueValueLinkedList));
            result.addAll(Collections.singleton(countUniqueValues));
        } else {
            LinkedList<Double> columnDouble = convertStringValueToDouble(column);
            LinkedList<Range> rangeLinkedList = discretizationService.createListRange(columnName, numberOfRanges);
            LinkedList<String> resultRange = new LinkedList<>();
            LinkedList<String> countValues = new LinkedList<>();
            int i = 0;
            char beginningOfaClosedSet = '<',
                    endOfClosedSet = '>',
                    endOfOpenSet = ')',
                    betweenExtremesOfSet = ';';
            for (Range range : rangeLinkedList) {
                String rangeString = "";
                if (i != rangeLinkedList.size() - 1) {
                    rangeString = String.valueOf(beginningOfaClosedSet) + range.getLowerValue() + (betweenExtremesOfSet) + range.getUpperValue() + (endOfOpenSet);
                } else {
                    rangeString = String.valueOf(beginningOfaClosedSet) + range.getLowerValue() + (betweenExtremesOfSet) + range.getUpperValue() + (endOfClosedSet);
                }
                resultRange.add(rangeString);
                int count = 0;
                for (Double value : columnDouble) {
                    if (i != rangeLinkedList.size() - 1) {
                        if (value >= range.getLowerValue() && value < range.getUpperValue()) {
                            count++;
                        }
                    } else {
                        if (value >= range.getLowerValue() && value <= range.getUpperValue()) {
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
